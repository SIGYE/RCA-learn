const { Pool } = require("pg");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const user = require("../models/user");
const { CreateUser } = require("../schemas/UserValidationSchema");


const pool = new Pool({
    user: "postgres",
    host: "localhost",
    database: "restful_2023",
    password: "postgres",
    port: 3306, 
});

const createUser = async (req, res) => {
    try {
        const { name, email, password, role } = req.body;
        const value = await createUser.validateAsync(req.body);

        const passHashed = await bcrypt.hash(password, 10);
        console.log(passHashed);

        const checkQuery = `SELECT * FROM users WHERE email = $1`;
        const checkValues = [email];
        const checkResult = await pool.query(checkQuery, checkValues);

        if (checkResult.rows.length > 0) {
            return res.status(401).json({ message: "Email already exists" });
        }

        const insertQuery = `INSERT INTO users (name, email, password, role) VALUES ($1, $2, $3, $4)`;
        const newUserValues = [name, email, passHashed, role];
        await pool.query(insertQuery, newUserValues);

        return res.status(200).json({ message: "Sign up Successful" });
    } catch (error) {
        console.error(error);
        return res.status(500).json({ message: "Internal Server Error" });
    }
};

const userLogin = async(req,res)=>{
    const {email, password} = req.body;
    console.log(req.body);
    const getQuery = `SELECT *FROM users WHERE email = $1`;
    const userValues = [email];
    await pool.query(getQuery, userValues, (err, result)=>{
        if(err){
            return res.status(500).json({message: "Error in finding user"});
        }
        if(result.length===0){
            return res.status(401).json({message: "Invalid email"});
        }
        const user= result[0];
        bcrypt.compare(password, user.password, (err, isMatch) =>{
            if(err){
                return res.status(500).json({message:"Error comparing passwords"});
            }
            if(!isMatch){
                return res.status(401).json({message:"Invalid email or password"});
            }
            const token = jwt.sign({
                id: user.id,
                name : user.name,
                email: user.email,
                password: user.password
            },
            process.env.JWT_SECRET_KEY,
            {expiresIn: "1h"}
        );
        res.status(200).json({token});
        });
    }

    )};

const getAllUsers= async(req,res)=>{
    try{
        const data = await User.findAll({
            attributes: ["id", "name", "email"]
        });
        console.log(data);
        res.json(data);
    } catch(error){
        console.error(error);
        res.status(500).json({message: "Server Error!"});
    }
};

const getUsersById = async(req,res)=>{
    try{
        const id = req.params;
        const user = await User.findOne(id);
        console.log(user);
        if(user){
            return res.json(user);
        } else{
            return res.status(401).json({message: "User not found"});
        }
    }  catch(error){
        console.log(error);
        res.status(500).json({message: "Server Error"});
    }
};

const updateUser = async(req,res)=>{
    try{
    const { id } = req.params;
    const {name, email} = req.body;
    const user = await User.findByPk(id);
    if(user){
        await user.update({
            name,
            email
        });
        return res.status(200).json({message : "Successfully Updated User"});
    } else{
        return res.status(401).json({message: "Error occurred"});
    }
} catch(error){
    console.log(error);
    return res.status(500).json({message : "Server Error"});
}
};

const deleteUser = async(req,res)=>{
    try{
        const { id } = req.params;
        const user = await User.findByPk(id);
        if(user){
            await user.destroy();
        return res.status(200).json({message : "Successfully Deleted User"});
        } else{
        return res.status(401).json({message : "Error occurred"});
        }
    } catch(error) {
        console.error(error);
        return res.status(500).json({message : "Server error"});
    }
};

module.exports = {
    createUser,
    getAllUsers,
    getUsersById,
    updateUser,
    deleteUser
};