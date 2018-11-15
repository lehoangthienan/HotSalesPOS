
var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Product = require(config.models_dir + '/mongo/product');

module.exports = (req, res)=>{
    Product.find({})
    .populate('owner')
    .then(products=>{
        response_express.success(res, products)
    })
    .catch(err=>response_express.exception(res, err.message))
}