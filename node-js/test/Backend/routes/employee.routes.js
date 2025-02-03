const express = require("express")
const {
    createEmployee,
    getEmployee,
    updateEmployee,
    deleteEmployee
} = require("../controllers/employee.controller");

const router =  express.Router();
router.post("/new", createEmployee);
router.get("/", getEmployee);
router.put("/update/:id", updateEmployee);
router.delete("/delete/:id", deleteEmployee);

module.exports = router;