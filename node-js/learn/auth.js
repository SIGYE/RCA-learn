const express = require("express")
const sequelize = require("./database");
const jwt = require("jsonwebtoken")
const User = require("./userModel");
const { where } = require("sequelize");

const app = express();

app.use(express.json());

app.post("/login", async(req, res, next) =>{
        let { email, password } = req.body
        console.log("Received Payload:", req.body)
        let existingUser;
        try{
            existingUser = await User.findOne({ where: { email } });
        } catch(err){
            return res.status(500).json({message: "Something went wrong"});
        }
        if(!existingUser || existingUser.password != password){
            return res.status(401).json({message: "Incorrect email or password"});
        }
        let token;
        try{
            token = jwt.sign(
                {userId: existingUser.id, email: existingUser.email},
                "secretkeyappearshere",
                {expiresIn: "1h"}
            );
            } catch(err){
               return res.status(500).json({ meassage: "Error generating token!"});
            }
            res.status(200).json({
                    success: true,
                    data: {
                        userId: existingUser.id,
                        email: existingUser.email,
                        token: token,
                    },
                   }) ;
        });

 app.post("/signUp", async(req, res, next) =>{
        const {name,email,password} = req.body;
        console.log("Received payload:", req.body)
        const newUser = User.build({email, password, name});
        try{
            await newUser.save();
        } catch(err){
            console.error("Error occured:", err)
            return res.status(500).json({message: "Something went wrong!"});
        }
        let token;
        try{
            token = jwt.sign(
                {userId: newUser.id, email: newUser.email},
                "secretkeyappershere",
                {expiresIn: "1h"}
            );
        } catch(err){
           return res.status(500).json({message:"Error generating token"}); 
        }
        res.status(201).json({
                success: true,
                data: {
                    userId: newUser.id,
                    email: newUser.email,
                    token: token
                },
             });
    });

    sequelize
    .sync({force:true}) 
    .then(() => {
      app.listen(5000, () => {
        console.log("App is listening on 5000");
      });
    })
    .catch((err) => {
      console.error("Error occurred:", err);
    });

                 

                  