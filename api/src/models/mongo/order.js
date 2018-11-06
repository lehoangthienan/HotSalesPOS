var mongoose = require('mongoose');
var OrderSchema = new mongoose.Schema({
    user_id: {
        type: String,
        trim: true,
    },
    user_name: {
        type: String,
        trim: true,
    },
    email: {
        type: String,
        trim: true,
        lowercase: true
    },
    phone_number: {
        type: String,
        trim: true,
        required: false,
    },
    product_name: {
        type: String,
        trim: true,
    },
    price: {
        type: Number,
        trim: true,
    },
    discount: {
        type: String,
        trim: true,
        required: true,
    },
    image: {
        type: String,
        trim: true,
    },
    owner: {
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