var mongoose = require('mongoose');
var UserSchema = new mongoose.Schema({
    username: {
        type: String,
        trim: true,
        lowercase: true,
        index: {
            unique: true,
            partialFilterExpression: {username: {$type: 'string'}}
        }
    },
    full_name: {
        type: String,
        trim: true,
    },
    email: {
        type: String,
        trim: true,
        lowercase: true,
        index: {
            unique: true,
            partialFilterExpression: {email: {$type: 'string'}}
        }
    },
    password: {
        type: String,
        trim: true,
        required: true,
    },
    avatar: {
        type: String,
        trim: true,
    },
    date_created: {
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