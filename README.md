
## Kurulum

### 1. Repoyu klonlamak için

```sh
git clone https://github.com/Gurkahramann/fitness-app-backend.git
cd fitness-app
```

### 2. Alt Servislerin Kurulumu

#### Backend (Java Spring Boot)

```sh
cd fitness-app-backend/fitness-app-backend/demo
./mvnw spring-boot:run
```
> Java 17+ ve Maven gereklidir.

#### Food Calorie Calculator (Python)

```sh
cd food-calorie-calculator
python -m venv yolovenv
yolovenv\Scripts\activate  # Windows için
pip install -r requirements.txt
uvicorn main:app --host 0.0.0.0 --port 8000
```

#### Login/Register Service (Node.js)

```sh
cd login-register-service
npm install
node index.js
```

---

## Kullanılan Teknolojiler

- **Java Spring Boot** (Backend API)
- **Python** (Besin kalori hesaplama)
- **Node.js & Express** (Kullanıcı yönetimi)
- **MongoDB** (Veritabanı)
- **PostgreSQL** (Veritabanı)
- **React/React Native** (Varsa frontend için)

---

## İletişim

Herhangi bir sorunda veya katkı yapmak için [Gurkahramann](https://github.com/Gurkahramann) ile iletişime geçebilirsiniz.

---


