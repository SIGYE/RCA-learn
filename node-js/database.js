const { Sequelize } = require(sequelize);

const sequelize = new Sequelize("testdb", "root", "postgres",{
    host: "localhost",
    dialect: "postgresql"
});

sequalize. authenticate().then(() =>console.log("connected to the database"))
                                        .catch((err)=>console.error("Unable to connect to the database:", err));

module.exports = sequelize;                                        