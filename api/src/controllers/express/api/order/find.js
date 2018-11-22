var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Order = require(config.models_dir + '/mongo/order');

module.exports = (req, res)=>{
    let product_id = req.params.product_id
    Order.find({product: product_id})
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