var ObjectId = require('mongoose').Types.ObjectId;
var config = require('../config');
var lib_common = require(config.library_dir + '/common');
var Log = require(config.library_dir + '/log');

exports.sendSocketToSocketID = function(socket, namespace, event, socket_id, data) {
	return new Promise((resolve, reject)=>{
		if(namespace)
			socket_id=lib_common.buildLocalSocketID(socket_id, namespace);
		socket.to(socket_id).emit(event, data);
		resolve();
	})
};