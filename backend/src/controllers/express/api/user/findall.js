
var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var User = require(config.models_dir + '/mongo/user');

module.exports = (req, res)=>{
    User.find({})
    .then(users=>{
        response_express.success(res, users)
    })
    .catch(err=>response_express.exception(res, err.message))
}