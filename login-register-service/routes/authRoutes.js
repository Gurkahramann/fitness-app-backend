const express = require('express');
const { register, login } = require('../controllers/authController');
const router = express.Router();

router.post('/register', register); // Register endpoint
router.post('/login', login); // Login endpoint

module.exports = router;
