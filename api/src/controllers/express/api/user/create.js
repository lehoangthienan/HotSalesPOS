var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var lib_common = require(config.library_dir+'/common');
var lib_password = require(config.library_dir+'/password');
var User = require(config.models_dir + '/mongo/user');

module.exports = (req, res)=>{
    let miss=lib_common.checkMissParams(res, req.body, ["user"])
    if (miss) return

    User.findOne({email: req.body.user.email})
    .then((user)=>{
        if(user==null) 
        {   
            lib_password.cryptPassword(req.body.user.password)
            .then(passwordHash=>{
                req.body.user.password = passwordHash
                return User.create(req.body.user)
            })
            .then(user=>{
                response_express.success(res, user)
            })
            .catch(err=>response_express.exception(res, err.message))
        }
        else
        {
            response_express.success(res, user)
        }
    })
}