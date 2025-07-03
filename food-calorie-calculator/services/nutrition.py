# services/nutrition.py
import os
import requests
from dotenv import load_dotenv

load_dotenv()
API_KEY = os.getenv("USDA_API_KEY")
BASE_URL = "https://api.nal.usda.gov/fdc/v1/"

def fetch_calories(food_query: str) -> float | None:
    """
    USDA FoodData Central API'den verilen yemek sorgusu için
    100g başına kcal değerini döner. Bulamazsa None döner.
    """
    # 1) Foods/Search endpoint'ine sorgu at
    resp = requests.get(
        BASE_URL + "foods/search",
        params={
            "api_key": API_KEY,
            "query": food_query,
            "pageSize": 1
        },
        timeout=5
    )
    resp.raise_for_status()
    foods = resp.json().get("foods", [])
    if not foods:
        return None

    # 2) İlk sonucun nutrient listesinde "Energy (KCAL)" değerini bul
    for nut in foods[0].get("foodNutrients", []):
        if nut.get("nutrientName") == "Energy" and nut.get("unitName") == "KCAL":
            return nut.get("value")

    return None
