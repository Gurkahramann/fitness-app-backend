const User = require('../models/User');
const { hashPassword, comparePasswords } = require('../utils/hash');
const { generateAccessToken, generateRefreshToken } = require("../utils/token");
const bcrypt = require('bcrypt');
const jwt = require("jsonwebtoken");

const register = async (req, res) => {
    try {
        const { email, password, name,gender,height,weight,age,activityLevel,goal } = req.body;
        if (!email || !password || !name || !gender || !height || !weight || !age || !activityLevel) {
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
                goal
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

    if (!user) return res.status(404).json({ message: "User not found" });

    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) return res.status(401).json({ message: "Invalid credentials" });

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
      console.log("Cookies:", req.cookies);  // ⬅️ Gelen cookie'leri logla
      const { refreshToken } = req.cookies;
      
      if (!refreshToken) {
          console.log("No refresh token found in cookies!");
          return res.status(401).json({ message: "No refresh token provided" });
      }

      console.log("Refresh Token:", refreshToken); // ⬅️ Refresh token'ı logla

      // Refresh token'ı veritabanında ara
      const user = await User.findOne({ refreshToken });
      if (!user) {
          console.log("Refresh token is invalid!");
          return res.status(403).json({ message: "Invalid refresh token" });
      }

      // Token doğrula
      jwt.verify(refreshToken, process.env.JWT_REFRESH_SECRET, (err, decoded) => {
          if (err) return res.status(403).json({ message: "Invalid refresh token" });

          // Yeni Access Token oluştur
          const accessToken = jwt.sign(
              { id: user._id, role: user.role },
              process.env.JWT_SECRET,
              { expiresIn: "1h" }
          );

          res.json({ accessToken });
      });
  } catch (error) {
      console.error("Token refresh error:", error);
      res.status(500).json({ message: "Token refresh error", error: error.message });
  }
};

  
  const logout = async (req, res) => {
    try {
      const { refreshToken } = req.cookies;
  
      if (!refreshToken) return res.status(400).json({ message: "No refresh token provided" });
  
      // Refresh token'ı veritabanından kaldır
      await User.findOneAndUpdate({ refreshToken }, { refreshToken: null });
  
      res.clearCookie("refreshToken");
      res.json({ message: "Logged out successfully" });
    } catch (error) {
      res.status(500).json({ message: "Logout error", error: error.message });
    }
  };
  module.exports = { register, login, refreshToken, logout };
