const express = require("express")
const mongoose = require("mongoose")

const jwt = require("jsonwebtoken")
const User = require("./userModel")

const app = express();

app.use(express.json());

app.post("/login",
    async(req, res, next) =>{
        let { email, password } = req.body
        let existingUser;
        try{
            existingUser = 
            await User.findOne({ email: email });
        } catch{
            const error = new Error("Something went wrong!");
            return next(error);
        }
        if(!existingUser || existingUser.password != password){
            const error = Error("Your Password or Email are incorrect");
            return next(error);
        }
        let token;
        try{
            token = jwt.sign(
                {
                    userId: existingUser.id,
                    email: existingUser.email
                },
                "secretkeyappearshere",
                {expiresIn: "1h"}
            );
            } catch(err){
                console.log(error);
                const error = new Error("Error! Something went wrong");
                return next(error);
            }
            res.status(200)
                   .json({
                    success: true,
                    data: {
                        userId: existingUser.id,
                        email: existingUser.email,
                        token: token,
                    },
                   }) ;
        });

 app.post("/signUp",
    async(req, res, next) =>{
        const {
            name,
            email,
            password, 
        } = req.body;

        const newUser = User ({
            name,
            email,
            password,
        });
        try{
            await newUser.save();
        } catch{
            const error = new Error("Something went wrong");
            return next(error);
        }
        let token;
        try{
            token = jwt.sign(
                {
                    userId: newUser.id,
                    email: newUser.email
                },
                "secretkeyappershere",
                {expiresIn: "1h"}
            );
        } catch(err){
            console.log(error);
            const error = new Error("Error! Something went wrong");
            return next(error); 
        }
        res.status(201)
             .json({
                success: true,
                data: {
                    userId: newUser.id,
                    email: newUser.email,
                    token: token
                },
             });
    });

mongoose.connect("mongodb://localhost:27017/testdb")
                 .then(() =>{
                  app.listen(3000, () =>{
                    console.log(`App is listening on 3000`);
                  });
                 })
                 .catch(
                    (err) =>{
                        console.log("Error occured");
                    })