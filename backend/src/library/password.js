var bcrypt = require('bcrypt');

exports.cryptPassword = function(password) {
	return new Promise(function(resolve, reject) {
		bcrypt.genSalt(10, function(err, salt) {
			if (err)
				return reject(err);

			bcrypt.hash(password.toString(), salt, function(err, hash) {
				if(err)
					return reject(err);

				return resolve(hash);
			});
		});
	});
};

exports.comparePassword = function(plainPass, hashword) {
	return new Promise(function(resolve, reject) {
		bcrypt.compare(plainPass.toString(), hashword, function(err, isPasswordMatch) {
			return err == null ? resolve(isPasswordMatch) : reject(err);
		});
	});
};
