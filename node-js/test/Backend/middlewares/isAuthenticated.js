const jwt = require("jsonwebtoken");

const isAuthenticated = (req,res,next)=>{
    if(!req.headers.authorization){
        return res.status(401).json({message: "Unauthorized"});
    }

    const token = req.headers.authorization.split("")[1];
    if(!token){
        return res.status(401).json({message: "Token not Found, Unauthorized"})
    }
    try{
        const decoded = jwt.verify(token, process.env.JWT_SECRET_KEY);
        console.log(decoded);
        req.user = decoded;
        next();
    } catch(error){
        console.error(error);
        return res.status(401).json({message: "Unauthorized"});
    }
};

module.exports = isAuthenticated;