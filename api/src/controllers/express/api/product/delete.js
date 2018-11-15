var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Product = require(config.models_dir + '/mongo/product');

module.exports = (req, res)=>{
    let product_id = req.params.product_id
    Product.deleteOne({_id: product_id})
    .then(()=>{
        response_express.success(res)
    })
    .catch(err=>response_express.exception(res, err.message))
}