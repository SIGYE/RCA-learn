const { type } = require("os");
const Sequelize = require("sequelize");
const sequelize = new Sequelize({
    dialect: "postgres",
    username: "postgres",
    password: "postgres",
    database: "restful_2023",
    host: "localhost",
    port: 3306,
});
const Employee = sequelize.define(
    "employees",
    {
        name: {
            type: Sequelize.STRING,
            allowNullable: false,
            validate: {
                len: [3, 255]
            },
        },
        email:{
            type: Sequelize.STRING,
            allowNullable: false,
            validate:{
                isEmail: true
            },
        },
        phoneNumber:{
            type: Sequelize.NUMBER,
            allowNullable: true,
            validate: {
                len: [10,20]
            },
        },
        role:{
            type: Sequelize.STRING,
            allowNullable: false,
            
        }
    }
)