const jwt = require("jsonwebtoken");

/**
 * Access Token oluşturma fonksiyonu (1 saat geçerli)
 */
const generateAccessToken = (user) => {
  return jwt.sign(
    { id: user._id, role: user.role },
    process.env.JWT_SECRET,
    { expiresIn: "1h" } // 1 saat
  );
};

/**
 * Refresh Token oluşturma fonksiyonu (1 yıl geçerli)
 */
const generateRefreshToken = (user) => {
  return jwt.sign(
    { id: user._id },
    process.env.JWT_REFRESH_SECRET,
    { expiresIn: "1y" } // 1 yıl
  );
};

/**
 * Token doğrulama fonksiyonu
 */
const verifyToken = (token) => {
  return jwt.verify(token, process.env.JWT_SECRET);
};

module.exports = { generateAccessToken, generateRefreshToken, verifyToken };
