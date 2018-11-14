var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var OrderSchema = new mongoose.Schema({
    owner: { type: Schema.Types.ObjectId, ref: 'User' },
    product: { type: Schema.Types.ObjectId, ref: 'Product' },
    name: {
        type: String,
        trim: true,
    },
    address: {
        type: String,
        trim: true,
    },
    phonenumber: {
        type: String,
        trim: true,
    },
    date_created: {
        type: Date,
        default: Date.now
    }
});

OrderSchema.pre('save', next=>{
    if (this.isNew || this.isModified) {
       
    }
    return next();
});

module.exports = mongoose.model('Order', OrderSchema);