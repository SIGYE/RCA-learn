const { Sequelize } = require("sequelize");

const sequelize = new Sequelize("testdb", "postgres", "postgres",{
    host: "localhost",
    dialect: "postgres"
});

sequelize. authenticate().then(() =>console.log("connected to the database"))
                                        .catch((err)=>console.error("Unable to connect to the database:", err));

module.exports = sequelize;                                        