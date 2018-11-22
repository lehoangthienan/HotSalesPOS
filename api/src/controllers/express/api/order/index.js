
var config = require('../../../../config');
var express = require('express');
var orderRoutes = express.Router();

orderRoutes.get("/user/:user_id", require('./findall'))
orderRoutes.post("/", require('./create'))
orderRoutes.delete("/:order_id", require('./delete'))
orderRoutes.put("/:order_id", require('./update'))
orderRoutes.get("/:order_id", require('./find'))
orderRoutes.get("/product/:product_id", require('./findbyproduct'))


module.exports = orderRoutes;