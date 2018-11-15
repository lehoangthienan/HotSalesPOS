var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var lib_common = require(config.library_dir+'/common');
var Order = require(config.models_dir + '/mongo/order');

module.exports = (req, res)=>{
    let miss=lib_common.checkMissParams(res, req.body, ["order"])
    if (miss) return

    Order.create(req.body.order)
    .then(order=>{
        response_express.success(res, order)
    })
    .catch(err=>response_express.exception(res, err.message))
}