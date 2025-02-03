const User = require('../models/User');
const { hashPassword, comparePasswords } = require('../utils/hash');
const { generateToken } = require('../utils/token');

const register = async (req, res) => {
    try {
        const { email, password, name } = req.body;

        // Şifre uzunluğu ve en az 1 rakam içerdiğini kontrol et
        const passwordRegex = /^(?=.*\d).{8,}$/;
        if (!passwordRegex.test(password)) {
            return res.status(400).json({ message: "Password must be at least 8 characters long and contain at least one number." });
        }

        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).json({ message: "User already exists" });
        }

        const hashedPassword = await hashPassword(password);
        const user = new User({ email, password: hashedPassword, name });
        await user.save();

        res.status(201).json({ message: "User created successfully" });
    } catch (error) {
        res.status(500).json({ message: "Something went wrong", error: error.message });
    }
};

const login = async (req, res) => {
    try {
        const { email, password } = req.body;
        const existingUser = await User.findOne({ email });
        if (!existingUser) {
            return res.status(404).json({ message: "User does not exist" });
        }
        const isPasswordCorrect = await comparePasswords(password, existingUser.password);
        if (!isPasswordCorrect) {
            return res.status(401).json({ message: "Invalid credentials" });
        }
        const token = generateToken(existingUser);
        res.status(200).json({ token, user: { email: existingUser.email, name: existingUser.name, role: existingUser.role } });
    } catch (error) {
        res.status(500).json({ message: "Something went wrong: ", error });
    }
};

module.exports = { register, login };