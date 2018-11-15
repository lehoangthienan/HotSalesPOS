
var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var User = require(config.models_dir + '/mongo/user');

module.exports = (req, res)=>{
    let user_id = req.params.user_id
    User.findById(user_id)
    .then(user=>{
        if (!user) {
            return Promise.reject("user not exist")
        }

        Object.assign(user, req.body.user)

        return user.save()
    })
    .then(user=>{
        response_express.success(res, user)
    })
    .catch(err=>response_express.exception(res, err.message))
}