const config = require('./config');
const server = require('./server');
const db_mongo = require(config.models_dir + '/mongo/db');
var Log = require(config.library_dir + '/log');

db_mongo.connect()
.then(()=>{
	Log.d("Connected mongo database");
	return server.start();
})
.then((serv)=>{
	Log.d("Started server");
})
.catch(err=>console.error(err));