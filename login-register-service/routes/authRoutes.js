const express = require('express');
const { register, login,refreshToken,logout ,updateUserProfile,validateToken,getUserInfo} = require('../controllers/authController');
const router = express.Router();

router.post("/register", register);
router.post("/login", login);
router.post("/refresh-token", refreshToken);
router.post("/logout", logout);
router.put("/update-profile", updateUserProfile)
router.post('/validate', validateToken);
router.get('/userinfo', getUserInfo);
module.exports = router;
