
var config = require('../../../../config');
var express = require('express');

var userRoutes = express.Router();


// apiRoutes.use('/', require('./v1'));
// apiRoutes.use('/v1', require('./v1'));

userRoutes.get("/", require('./findall'))
userRoutes.post("/", require('./create'))
userRoutes.delete("/:user_id", require('./delete'))
userRoutes.put("/:user_id", require('./update'))


module.exports = userRoutes;