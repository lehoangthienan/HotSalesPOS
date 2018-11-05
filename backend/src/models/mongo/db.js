const config = require('../../config');
var mongoose = require('mongoose');

const connect = ()=>{
	return new Promise((resolve, reject)=>{
		var url = "mongodb://" + config.mongo.host + ":" + config.mongo.port + "/" + config.mongo.database;
		if(config.mongo.user){
		  url = "mongodb://" + config.mongo.user + ":" +config.mongo.password + "@" + config.mongo.host + ":" + config.mongo.port + "/" + config.mongo.database;
		}

		mongoose.connect(url, {useNewUrlParser: true});
		mongoose.Promise = global.Promise;
		var connection = mongoose.connection;
		connection.on('error', err => reject(err));
		connection.on('connected', () => resolve());
	});
}

module.exports = {connect};
