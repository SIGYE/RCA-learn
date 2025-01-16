const { Sequelize,DataTypes } = require("sequelize")
const sequelize = require("./database");

const User = sequelize.define("User", {
    email: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
    },
    password: {
        type: DataTypes.STRING,
        allowNull: false,
    },
});
module.exports = User;

// Define the user schema
// const userSchema = new mongoose.Schema({
//     email: {
//         type: String,
//         required: true,
//         unique: true, 
//         trim: true,   
//     },
//     password: {
//         type: String,
//         required: true,
//         minlength: 6, 
//     },
// });

// Export the model
// module.exports = mongoose.model("User", userSchema);
