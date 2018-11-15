var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Order = require(config.models_dir + '/mongo/order');

module.exports = (req, res)=>{
    let order_id = req.params.order_id
    Order.find({_id: order_id})
    .populate('owner')
    .populate('product')
    .then(order=>{
        if (!order) {
            return Promise.reject("order not exist")
        }
        response_express.success(res, order)
    })
    .catch(err=>response_express.exception(res, err.message))
}