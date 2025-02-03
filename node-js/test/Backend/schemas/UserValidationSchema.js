const joi = require("joi")
const CreateUser = joi.object({
    name: joi.string().required().min(5),
    email: joi.string().required().min(14),
    password: joi.string().required().min(8)
})

module.exports = CreateUser;