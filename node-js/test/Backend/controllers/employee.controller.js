const express = require("express");
const bcrypt = require("bcrypt");
const Employee = require("../models/employee");
const jwt = require("jsonwebtoken");

const pool = new Pool({
    user: "postgres",
    database: "restful_2023",
    password: "postgres",
    port : "3306"
});

const createEmployee = async(req, res)=>{
    try{
        const {name, email, phoneNumber, role} = req.body;
        const value = await createEmployee.validateAsync(req.body);
        
        const insertQuery = `INSERT INTO employees(name, email, phoneNumber, role) VALUES (?, ?, ?, ?)`;
        const newEmployeeValues = [name, email, phoneNumber, role];
        const result = await pool.query(insertQuery, newEmployeeValues);
        res.status(200).json({
            message: "Employee Added Successfully", Employee: newEmployeeValues
        });
    } catch(error) {
        console.error(error);
        return res.status(500).json({message: "Internal Server error"});
    }
};

const getEmployee = async(req, res) =>{
    try{
        const data = await Employee.findAll();
        console.log(data);
        return res.json(data);
    } catch(error){
        console.error(error);
        return res.status(500).json({message: "Internal Server Error"});
    }
};

const updateEmployee = async(req,res) =>{
    try{
        const {id} = req.params;
        const {name, email, phoneNumber, role} = req.body;
        const employee = await Employee.findByPk(id);
        console.log(employee);
        if(employee){
            await employee.update({
            name, email, phoneNumber, role
            });
            res.status(200).json({message: "Successfully updated Employee", Employee: Employee});
        } else{
            res.status(401).json({message: "Employee not found"});
        }
    } catch(error){
        console.error(error);
        res.status(500).json({message: "Server error"});
    }
};

const deleteEmployee = async(req, res) =>{
    try{
        const {id} = req.params;
        const employee = await Employee.findByPk(id);
        console.log(employee);
        if(book){
            await employee.destroy();
            res.status(200).json({message: "Successfully deleted Employee"});
        } else{
            res.status(401).json({message: "Employee not found"});
        }
    } catch(error){
        console.error(error);
        res.status(500).json({message: "Server error"});
    }
};

module.exports = {
    createEmployee,
    getEmployee,
    updateEmployee,
    deleteEmployee
}