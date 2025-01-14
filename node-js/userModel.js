const mongoose = require("mongoose");

// Define the user schema
const userSchema = new mongoose.Schema({
    email: {
        type: String,
        required: true,
        unique: true, 
        trim: true,   
    },
    password: {
        type: String,
        required: true,
        minlength: 6, 
    },
});

// Export the model
module.exports = mongoose.model("User", userSchema);
