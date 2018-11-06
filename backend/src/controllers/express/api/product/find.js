var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Product = require(config.models_dir + '/mongo/product');

module.exports = (req, res)=>{
    let product_id = req.params.product_id
    Product.findOne({_id: product_id})
    .then(product=>{
        if (!product) {
            return Promise.reject("product not exist")
        }
        response_express.success(res, product)
    })
    .catch(err=>response_express.exception(res, err.message))
}