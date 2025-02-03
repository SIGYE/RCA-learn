const express = require("express");
const {
    createUser,
    getAllUsers,
    getUsersById,
    updateUser,
    deleteUser,
    userLogin
} = require("../controllers/user.controller");

const isAuthenticated = require("../middlewares/isAuthenticated");
const router =  express.Router();

router.post("/signup", createUser);
router.post("/login", userLogin);

router.use(isAuthenticated);

router.get("/:id", getUsersById);
router.get("/", getAllUsers);
router.put("/:id", updateUser);
router.delete("/:id", deleteUser);

module.exports = router;
