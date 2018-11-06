const config = require('../config');

const d = (...args) => {
	var isLog = process.env.IS_LOG==="true";
	isLog = process.env.IS_LOG == undefined ? config.console_log : isLog;
	if (isLog) {
		console.log(...args);
	}
}

module.exports = {d}