var config = require('../../../config');
var express = require('express');

var apiRoutes = express.Router();

apiRoutes.use("/users", require('./user'))
apiRoutes.use("/products", require('./product'))

module.exports = apiRoutes;
