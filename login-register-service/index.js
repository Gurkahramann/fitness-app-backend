const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const path = require('path');
const cookieParser = require("cookie-parser"); // ⬅️ Cookie'leri okumak için eklenmeli

const envPath = path.resolve(__dirname, '../.env');
dotenv.config({ path: envPath });

console.log('Loaded .env from:', envPath);


const authRoutes = require('./routes/authRoutes');

const app = express();

// Middleware
app.use(express.json());
app.use(cookieParser()); // ⬅️ Cookie'leri kullanmak için ekle

// Database connection
mongoose.connect(process.env.MONGO_URI, { useNewUrlParser: true, useUnifiedTopology: true })
  .then(() => console.log("Connected to MongoDB"))
  .catch((err) => console.error(err));

app.use((req, res, next) => {
  console.log('--- Yeni İstek ---');
  console.log('Yöntem:', req.method);
  console.log('URL:', req.originalUrl);
  console.log('Headers:', JSON.stringify(req.headers, null, 2));
  console.log('Query:', req.query);
  console.log('Body:', req.body);

  // Yanıtı da loglamak için:
  const oldSend = res.send;
  res.send = function (data) {
    console.log('--- Yanıt ---');
    console.log('Status:', res.statusCode);
    try {
      // JSON ise prettify et
      console.log('Body:', JSON.stringify(JSON.parse(data), null, 2));
    } catch {
      // Değilse düz yaz
      console.log('Body:', data);
    }
    return oldSend.apply(res, arguments);
  };

  next();
});
// Routes
app.use('/api/auth', authRoutes);

// Server
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
