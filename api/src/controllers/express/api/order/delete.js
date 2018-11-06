var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Order = require(config.models_dir + '/mongo/order');

module.exports = (req, res)=>{
    let order_id = req.params.order_id
    Order.deleteOne({_id: order_id})
    .then(()=>{
        response_express.success(res)
    })
    .catch(err=>response_express.exception(res, err.message))
}