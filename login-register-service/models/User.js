const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  name: { type: String, required: true },
  role: { type: String, default: "user" },
  gender: {type: String,enum:['Male','Female'],required:true},
  height: {type: Number,required:true},
  weight: {type: Number,required:true},
  age: {type: Number,required:true},//Bu kısım frontend tarafında hesaplanacak
  activityLevel: {type: String, required:false},
  goal: {type: String, required:false},
  refreshToken: { type: String } 

});

module.exports = mongoose.model('User', userSchema);
