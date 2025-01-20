const express = require("express");
const bcrypt = require("bcrypt");
const User = require("../models/user");
const jwt = require("jsonwebtoken");

const pool = new Pool({
    username: "postgres",
    password: "postgres",
    database: "restful_2023",
    host: "localhost",
    port: "3306"
});

const createUser = async(req,res) =>{
    try{
        const {name, email, password} = req.body;
        const value = await createUser.validateAsync(req.body);
        const passHashed = await bcrypt.hash(password, 10);
        console.log(passHashed);
        const checkQuery = `SELECT *FROM users WHERE email = ?`;
        const checkValues = [email];
        await pool.query(checkQuery, checkValues, (err, result)=>{
            if(err){
                return res.status(500).json({message: "Error Checking User"});
            }
            if(result.length > 0){
                return res.status(401).json({message: "Email already exists"})
            }
        })

        const insertQuery = `INSERT INTO users (name, email, role) VALUES (?, ?, ?)`;
        const newUserValues = [name, email,role];
        const result = await pool.query(insertQuery, newUserValues);
        res.status(200).json({message: ""})
    } catch{
        
    }
}