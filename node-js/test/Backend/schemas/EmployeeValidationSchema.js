const joi = require("joi")
const CreateEmployee = joi.object({
    name: joi.string().required().min(5),
    email: joi.string().required().min(14),
    phonenumber: joi.string().required().min(10),
    role: joi.string().required().min(3)
}).options({abortEarly:false, allowUnknown:true});

module.exports = CreateEmployee;
