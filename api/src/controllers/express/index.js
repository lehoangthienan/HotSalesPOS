var config = require('../../config');
var express = require('express');

var mainRoutes = express.Router();

mainRoutes.get('/', function(req, res) {
	res.send('Hello! The API is at http://' + config.host + ':' + config.port + '/api');
});

mainRoutes.use('/api', require('./api'));

module.exports = mainRoutes;
