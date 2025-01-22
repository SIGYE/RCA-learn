const { Pool } = require("pg");
const bcrypt = require("bcrypt");


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
                return res.status(401).json({message:""})
            }
        } 

        )
    }

    );
    
    

}