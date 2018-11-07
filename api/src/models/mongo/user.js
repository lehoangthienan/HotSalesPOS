var mongoose = require('mongoose');
var UserSchema = new mongoose.Schema({
    name: {
        type: String,
        trim: true,
    },
    email: {
        type: String,
        trim: true,
        lowercase: true,
        index: {
            unique : true,
            partialFilterExpression: {email: {$type: 'string'}}
        }
    },
    password: {
        type: String,
        trim: true,
        required: true,
    },
    phone_number: {
        type: String,
        trim: true,
        required: false,
    },
    avatar: {
        type: String,
        trim: true,
    },
    createAt: {
        type: Date,
        default: Date.now
    }
});

UserSchema.pre('save', next=>{
    if (this.isNew || this.isModified) {
       
    }
    return next();
});

module.exports = mongoose.model('User', UserSchema);