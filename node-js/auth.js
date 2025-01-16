const express = require("express")
const sequelize = require("sequelize");

const jwt = require("jsonwebtoken")
const User = require("./userModel");
const { where } = require("sequelize");

const app = express();

app.use(express.json());

app.post("/login", async(req, res, next) =>{
        let { email, password } = req.body
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
        const newUser = User.build({email, password, name});
        try{
            await newUser.save();
        } catch(err){
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
    .sync() 
    .then(() => {
      app.listen(3000, () => {
        console.log("App is listening on 3000");
      });
    })
    .catch((err) => {
      console.error("Error occurred:", err);
    });

// mongoose.connect("mongodb://localhost:27017/testdb")
//                  .then(() =>{
//                   app.listen(3000, () =>{
//                     console.log(`App is listening on 3000`);
//                   });
//                  })
//                  .catch(
//                     (err) =>{
//                         console.log("Error occured");
//                     })

app.get("/accessResource", (req, res) =>{
    const token = req.headers.authorization.split(' ')[1];
    if(!token){
        res.status(200).json({
            success: false,
            message: "Error! Token was not provided"
        });
    }
    const decodedToken = jwt.verify(token, "secretkeyappearshere");
    res.status(200).json({
        success: true,
        data: {
            userId: decodedToken.userId,
            email: decodedToken.email
        },
    });
})                    

                  