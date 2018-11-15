var path = require('path');

const root = path.normalize(__dirname + '/..');

const env = process.env.ENV || "local";

var main_config = {
  env: env,
	host: '0.0.0.0',
	port: 3000,
	secret: '6FdAqPSYz6BB9xJSyNh2wF6SnM@%ZR6yMa9GZ6CS7YnXQ#bKNBM^^ujjT22pq8cM7UDezuyskjykj!^J',
	root_dir: root,
	public_dir: root + '/public',
	models_dir: root + '/models',
	controllers_dir: root + '/controllers',
	library_dir: root + '/library',
	console_log: true
};

module.exports = Object.assign(main_config, require('./env/'+env) || {});
