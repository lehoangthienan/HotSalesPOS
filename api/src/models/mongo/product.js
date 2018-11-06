var mongoose = require('mongoose');
var ProductSchema = new mongoose.Schema({
    name: {
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
    content: {
        type: String,
        trim: true,
        required: false,
    },
    image: {
        type: String,
        trim: true,
    },
    isWebsite: {
        type: Boolean,
        trim: true,
    },
    owner: {
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

ProductSchema.pre('save', next=>{
    if (this.isNew || this.isModified) {
       
    }
    return next();
});

module.exports = mongoose.model('Product', ProductSchema);