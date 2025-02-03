const express = require("express");
const router = express.Router();

const employeeRoutes = require("./employee.routes");
const userRoutes = require("./user.routes");
const isAuthenticated = require("../middlewares/isAuthenticated");

router.use("/employees", isAuthenticated, employeeRoutes);
router.use("/users",userRoutes);

module.exports = router