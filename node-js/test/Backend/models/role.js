const { timeStamp } = require("console");
const Sequalize = require("sequelize")
const sequalize = new Sequalize({
    dialect: "postgres",
    username: "postgres",
    password: "postgres",
    database: "restful_2023",
    host: "localhost",
    port: 3306,
});

const Role = Sequalize.define(
    "role",
    {
        name: {
            type: Sequalize.STRING,
            allowNullable: true,
        }
    },
    {
        timeStamps : false,
        indexes: [
            {
                name: "role_name",
                fields: ["name"]
            },
        ]
    }
);