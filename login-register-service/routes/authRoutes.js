const express = require('express');
const { register, login,refreshToken,logout ,updateUserProfile} = require('../controllers/authController');
const router = express.Router();

router.post("/register", register);
router.post("/login", login);
router.post("/refresh-token", refreshToken);
router.post("/logout", logout);
router.put("/auth/update-profile", updateUserProfile)

module.exports = router;
