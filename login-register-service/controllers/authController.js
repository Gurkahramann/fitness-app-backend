const User = require('../models/User');
const { hashPassword, comparePasswords } = require('../utils/hash');
const { generateAccessToken, generateRefreshToken, verifyToken } = require("../utils/token");
const bcrypt = require('bcrypt');
const jwt = require("jsonwebtoken");

const register = async (req, res) => {
    try {
        const { email, password, name,gender,height,weight,age,birthDate,activityLevel,goal } = req.body;
        if (!email || !password || !name || !gender || !height || !weight || !age || !activityLevel || !birthDate) {
            return res.status(400).json({ message: "All fields are required." });
        }
        if(!['Male','Female'].includes(gender))
        {
            return res.status(400).json(
                {
                    message: "Gender must be 'Male' or 'Female' "
                }
            );
        }
        // Şifre uzunluğu ve en az 1 rakam içerdiğini kontrol et
        const passwordRegex = /^(?=.*\d).{6,}$/;
        if (!passwordRegex.test(password)) {
            return res.status(400).json({ message: "Password must be at least 6 characters long and contain at least one number." });
        }

        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).json({ message: "User already exists" });
        }

        const hashedPassword = await hashPassword(password);
        const user = new User(
            {
                email,
                password:hashedPassword,
                name,
                gender,
                height,
                weight,
                age,
                activityLevel,
                goal,
                birthDate: new Date(birthDate), // Doğum tarihini Date formatına çevir
            }
        );
        await user.save();

        res.status(201).json({ message: "User created successfully" });
    } catch (error) {
        res.status(500).json({ message: "Something went wrong", error: error.message });
    }
};
const login = async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email });
    if (!user) return res.status(401).json({ message: "Incorrect email or password" });

    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) return res.status(401).json({ message: "Incorrect email or password" });

    // Access & Refresh Token oluştur
    const accessToken = generateAccessToken(user);
    const refreshToken = generateRefreshToken(user);

    // Refresh Token'ı veritabanına kaydet
    user.refreshToken = refreshToken;
    await user.save();

    res.json({ accessToken, refreshToken });
  } catch (error) {
    res.status(500).json({ message: "Login error", error: error.message });
  }
};
const refreshToken = async (req, res) => {
    try {
      const refreshToken = req.body.refreshToken || req.headers['x-refresh-token'];
      
      if (!refreshToken) {
        return res.status(401).json({ message: "No refresh token provided" });
      }
  
      const user = await User.findOne({ refreshToken });
      if (!user) {
        return res.status(403).json({ message: "Invalid refresh token" });
      }
  
      jwt.verify(refreshToken, process.env.JWT_REFRESH_SECRET, (err, decoded) => {
        if (err) return res.status(403).json({ message: "Invalid refresh token" });
  
        const accessToken = jwt.sign(
          { id: user._id, role: user.role },
          process.env.JWT_SECRET,
          { expiresIn: "1h" }
        );
  
        res.json({ accessToken });
      });
    } catch (error) {
      res.status(500).json({ message: "Token refresh error", error: error.message });
    }
  };

const updateUserProfile = async (req, res) => {
  try {
    // 1. Token'dan kullanıcı ID'sini çıkar
    const authHeader = req.headers.authorization
    if (!authHeader || !authHeader.startsWith("Bearer ")) {
      return res.status(401).json({ message: "Yetkisiz erişim" })
    }

    const token = authHeader.split(" ")[1]
    const decoded = jwt.verify(token, process.env.JWT_SECRET) // token doğrulama
    const userId = decoded.id

    // 2. Body'den güncellenecek alanları al
    const {
      name,
      height,
      weight,
      birthDate,
      activityLevel,
      fitnessGoal,
    } = req.body

    // 3. Veritabanında kullanıcıyı güncelle
    const updatedUser = await User.findByIdAndUpdate(
      userId,
      {
        name,
        height,
        weight,
        birthDate, // "dd/MM/yyyy" formatında geliyor
        activityLevel,
        goal: fitnessGoal,
      },
      { new: true }
    )

    if (!updatedUser) {
      return res.status(404).json({ message: "Kullanıcı bulunamadı" })
    }

    return res.json({
      message: "Profil başarıyla güncellendi",
      user: updatedUser,
    })
  } catch (error) {
    console.error("Update error:", error)
    return res.status(500).json({ message: "Sunucu hatası", error: error.message })
  }
}

const logout = async (req, res) => {
  try {
    // Hem body'den hem header'dan hem de cookie'den almayı dene
    const refreshToken = req.body.refreshToken || req.headers['x-refresh-token']

console.log("BODY:", req.body);
console.log("HEADERS:", req.headers);
console.log("COOKIES:", req.cookies);

    if (!refreshToken) return res.status(400).json({ message: "No refresh token provided" });

    // Refresh token'ı veritabanından kaldır
    console.log("refreshToken", refreshToken)
    await User.findOneAndUpdate({ refreshToken }, { refreshToken: null });

    if (res.clearCookie) res.clearCookie("refreshToken");
    res.json({ message: "Logged out successfully" });
  } catch (error) {
    res.status(500).json({ message: "Logout error", error: error.message });
  }
};
const validateToken = (req, res) => {
  const { token } = req.body;
  if (!token) return res.status(400).json({ valid: false, error: 'Token gerekli' });
  try {
    verifyToken(token);
    return res.status(200).json({ valid: true });
  } catch (err) {
    return res.status(401).json({ valid: false, error: 'Geçersiz token' });
  }
};

// Kullanıcı bilgisi fonksiyonu
const getUserInfo = async (req, res) => {
  const authHeader = req.headers.authorization;
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({ error: 'Token eksik veya geçersiz' });
  }
  const token = authHeader.replace('Bearer ', '');
  try {
    console.log("Gelen token:", token);
    const decoded = verifyToken(token);
    console.log("Decoded:", decoded);
    const user = await User.findById(decoded.id).select('-password');
    if (!user) return res.status(404).json({ error: 'Kullanıcı bulunamadı' });
    // _id'yi id olarak mapleyip, _id ve __v alanlarını kaldır
    const userObj = user.toObject();
    userObj.id = userObj._id;
    delete userObj._id;
    delete userObj.__v;
    return res.json(userObj);
  } catch (err) {
    console.error("Token doğrulama hatası:", err);
    return res.status(401).json({ error: 'Geçersiz token' });
  }
};
module.exports = { register, login, refreshToken, logout, updateUserProfile,validateToken,getUserInfo};
