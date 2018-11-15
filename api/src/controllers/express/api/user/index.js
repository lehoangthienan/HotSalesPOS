
var config = require('../../../../config');
var express = require('express');
var userRoutes = express.Router();

userRoutes.get("/", require('./findall'))
userRoutes.post("/", require('./create'))
userRoutes.delete("/:user_id", require('./delete'))
userRoutes.put("/:user_id", require('./update'))
userRoutes.get("/:user_id", require('./find'))


module.exports = userRoutes;