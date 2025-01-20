const Sequelize = require("sequelize");
const sequelize = new Sequalize({
    dialect: "postgres",
    username: "postgres",
    password: "postgres",
    database: "restful_2023",
    host: "localhost",
    port: "3306",
});

const User = sequelize.define(
    "users",
    {
        name: {
            type : Sequelize.STRING,
            allowNullable: false,
            validate: {
                len: [3,255]
            },
        },
        email: {
            type: Sequelize.STRING,
            allowNullable: false,
            validate: {
                isEmail: true
            },
        },
        password:{
            type: Sequelize.STRING,
            allowNullable: false,
            validate:{
                len:[8-255]
            },
        },
    },

    {
        timeStamp: false,
        indexes: [
            {
                name: "ad_name",
                fields: ["name"]
            },
            {
                name: "ad_email",
                fields: ["email"]
            },
            {
                name: "ad_password",
                fields: ["password"]
            },
        ],
    }
);

User.sync()
        .then(() =>{
            console.log("Database Table created Successfully");
        }).catch((err)=>{
            console.log("Error in creating Database Table");
        });

module.exports = User;