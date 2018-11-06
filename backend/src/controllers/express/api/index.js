var config = require('../../../config');
var express = require('express');

var apiRoutes = express.Router();

// apiRoutes.use('/', require('./v1'));
// apiRoutes.use('/v1', require('./v1'));

apiRoutes.use("/users", require('./user'))

module.exports = apiRoutes;
