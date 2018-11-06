var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var User = require(config.models_dir + '/mongo/user');

module.exports = (req, res)=>{
    let user_id = req.params.user_id
    User.deleteOne({_id: user_id})
    .then(()=>{
        response_express.success(res)
    })
    .catch(err=>response_express.exception(res, err.message))
}