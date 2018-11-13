
var config = require('../../../../config');
var express = require('express');
var productRoutes = express.Router();

productRoutes.get("/", require('./findall'))
productRoutes.post("/", require('./create'))
productRoutes.delete("/:product_id", require('./delete'))
productRoutes.put("/:product_id", require('./update'))
productRoutes.get("/:user_id", require('./find'))


module.exports = productRoutes;