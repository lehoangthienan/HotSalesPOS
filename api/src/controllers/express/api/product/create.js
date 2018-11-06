var config = require('../../../../config');
var response_express = require(config.library_dir+'/response').response_express;
var lib_common = require(config.library_dir+'/common');
var Product = require(config.models_dir + '/mongo/product');

module.exports = (req, res)=>{
    let miss=lib_common.checkMissParams(res, req.body, ["product"])
    if (miss) return

    Product.create(req.body.product)
    .then(product=>{
        response_express.success(res, product)
    })
    .catch(err=>response_express.exception(res, err.message))
}