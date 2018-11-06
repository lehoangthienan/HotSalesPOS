
var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Order = require(config.models_dir + '/mongo/order');

module.exports = (req, res)=>{
    let order_id = req.params.order_id
    Order.findById(order_id)
    .then(order=>{
        if (!order) {
            return Promise.reject("order not exist")
        }

        Object.assign(order, req.body.order)

        return order.save()
    })
    .then(order=>{
        response_express.success(res, order)
    })
    .catch(err=>response_express.exception(res, err.message))
}