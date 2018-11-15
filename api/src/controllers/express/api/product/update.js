var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Product = require(config.models_dir + '/mongo/product');

module.exports = (req, res)=>{
    let product_id = req.params.product_id
    Product.findById(product_id)
    .then(product=>{
        if (!product) {
            return Promise.reject("product not exist")
        }

        Object.assign(product, req.body.product)

        return product.save()
    })
    .then(product=>{
        response_express.success(res, product)
    })
    .catch(err=>response_express.exception(res, err.message))
}