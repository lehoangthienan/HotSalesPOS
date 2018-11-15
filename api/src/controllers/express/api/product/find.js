var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var Product = require(config.models_dir + '/mongo/product');

module.exports = (req, res)=>{
    let user_id = req.params.user_id
    Product.find({owner: user_id})
    .populate('owner')
    .then(product=>{
        if (!product) {
            return Promise.reject("product not exist")
        }
        response_express.success(res, product)
    })
    .catch(err=>response_express.exception(res, err.message))
}