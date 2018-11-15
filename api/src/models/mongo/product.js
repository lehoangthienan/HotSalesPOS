var mongoose = require('mongoose');
var Schema = mongoose.Schema;
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
    type: {
        type: Number,
        trim: true,
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
    owner: { type: Schema.Types.ObjectId, ref: 'User' },
    lat: {
        type: Number,
        trim: true,
    },
    lng: {
        type: Number,
        trim: true,
    },
    phone_number: {
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