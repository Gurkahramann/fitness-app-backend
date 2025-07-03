import os
import io 
import json

import google.generativeai as genai
from PIL import Image

from fastapi import FastAPI, File, UploadFile, HTTPException
from pydantic import BaseModel
from dotenv import load_dotenv

# ───────────────────────────────────────────────────────────────────────────────
# SADECE GEMINI MODELİ: Bu versiyon, resimdeki yemeklerin tahmini gramajını
# ve bu gramaja göre toplam kalori miktarını hesaplar.
# ───────────────────────────────────────────────────────────────────────────────

# 1) Ortam değişkenlerini (.env) yükleyelim
load_dotenv()
app = FastAPI()


# 3) Google Gemini API anahtarını yükle ve modeli yapılandır
GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
if not GEMINI_API_KEY:
    raise RuntimeError("Lütfen .env içinde GEMINI_API_KEY tanımlayın!")
genai.configure(api_key=GEMINI_API_KEY)
gemini_model = genai.GenerativeModel('gemini-1.5-flash')

# ───────────────────────────────────────────────────────────────────────────────
# DEĞİŞTİ: Pydantic şeması: Gramaj ve toplam kalori alanları eklendi
# ───────────────────────────────────────────────────────────────────────────────
class GeminiFoodInfoWithGrams(BaseModel):
    food_name: str
    estimated_grams: float
    total_calories: float
    calories_per_100g: float
    protein_per_100g: float
    carbs_per_100g: float
    fat_per_100g: float

# ───────────────────────────────────────────────────────────────────────────────
# /detect_with_grams/ endpoint'i
# ───────────────────────────────────────────────────────────────────────────────
@app.post("/detect_with_grams/", response_model=list[GeminiFoodInfoWithGrams])
async def detect_with_grams(file: UploadFile = File(...)):
    # 1) Gelen resmi oku
    img_bytes = await file.read()
    if not img_bytes:
        raise HTTPException(status_code=400, detail="Geçersiz resim dosyası")

    try:
        pil_image = Image.open(io.BytesIO(img_bytes))
    except Exception:
        raise HTTPException(status_code=400, detail="Resim dosyası açılamadı veya bozuk.")

    # 2) DEĞİŞTİ: Prompt'a "estimated_grams" alanı eklendi
    prompt = """
    Lütfen bu resimdeki tüm yiyecekleri analiz et.
    Sadece ve sadece aşağıdaki formatta bir JSON listesi döndür. Liste dışına hiçbir metin veya açıklama ekleme. Her yiyecek için listede bir JSON nesnesi olmalıdır:
    [
      {
        "food_name": "Tespit edilen ilk yiyeceğin Türkçe adı",
        "estimated_grams": "Resimdeki bu porsiyonun tahmini gramajı (sadece sayı)",
        "calories_per_100g": 100 gram için kalori değeri (sadece sayı),
        "protein_per_100g": 100 gram için protein değeri (sadece sayı),
        "carbs_per_100g": 100 gram için karbonhidrat değeri (sadece sayı),
        "fat_per_100g": 100 gram için yağ değeri (sadece sayı)
      }
    ]
    Eğer resimde hiç yiyecek yoksa veya yiyecekleri tanıyamazsan, boş bir liste "[]" döndür. Gramaj tahmininde makul ol.
    """

    # 3) Gemini API'ye resmi ve prompt'u gönder
    try:
        response = gemini_model.generate_content([prompt, pil_image])
        
        response_text = response.text.strip().replace("```json", "").replace("```", "")
        
        if not response_text:
            return []
            
        nutrition_list = json.loads(response_text)
        
        # YENİ: Dönen listedeki her yiyecek için toplam kaloriyi hesapla
        results_list = []
        for item in nutrition_list:
            calories_per_100g = float(item.get("calories_per_100g", 0))
            estimated_grams = float(item.get("estimated_grams", 0))

            # Hesaplama yap
            total_calories = calories_per_100g * (estimated_grams / 100.0)

            # Pydantic modeline uygun yeni bir nesne oluştur
            validated_item = GeminiFoodInfoWithGrams(
                food_name=item.get("food_name", "Bilinmeyen"),
                estimated_grams=round(estimated_grams, 1),
                total_calories=round(total_calories, 1),
                calories_per_100g=calories_per_100g,
                protein_per_100g=float(item.get("protein_per_100g", 0)),
                carbs_per_100g=float(item.get("carbs_per_100g", 0)),
                fat_per_100g=float(item.get("fat_per_100g", 0))
            )
            results_list.append(validated_item)
            
        return results_list

    except Exception as e:
        print(f"Gemini API ile iletişimde veya veri işlemede hata: {e}")
        # Geliştirme aşamasında daha detaylı hata görmek için aşağıdaki satırı kullanabilirsiniz
        # raise HTTPException(status_code=500, detail=f"Hata: {e}")
        return []