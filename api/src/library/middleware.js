var config = require('../config');
var jwt = require('jsonwebtoken');
var response = require('./response');
var Log = require(config.library_dir + '/log');
var response_express = response.response_express;

exports.expressMiddleware =(req, res, next) => {
	Log.d("---------------------middleware---------------------");
	var token = req.body.token || req.query.token || req.headers['x-access-token'];

	if(token){
		//decode token
		var decoded=jwt.verify(token, config.secret);
		if (decoded == undefined) {
			return response_express.exception(res, "Failed to authenticate token.")
		}

		if(decoded._id==user_token.user_id){
			Log.d("Success auth");
			req.token_info = decoded;
			next();
		}
		else
			return response_express.exception(res, "Failed to authenticate token.")
	}else{
		Log.d("Failed to auth");
		return response_express.exception(res, "No token provided.", 403);
	}
}

exports.socketioMiddleware = (socket, next) => {
	Log.d("---------------------middleware socketio---------------------");
	var token = socket.handshake.query.token;
	if (!token) return next(new Error('Failed to authenticate token.'));

	//decode token
	var decoded=jwt.verify(token, config.secret);
	if (decoded == undefined) {
		return next(new Error(exceptionObj("Failed to authenticate token.")))
	}

	if(decoded._id==user_token.user_id){
		socket.handshake.decoded_token = decoded;
		next();
	}
	else
		return next(new Error(exceptionObj("Failed to authenticate token.")))
};
