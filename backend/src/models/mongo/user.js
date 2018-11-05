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
  is_confirm_email: {
    type: Boolean,
    default: false,
  },
  password: {
    type: String,
    trim: true,
    required: true,
  },
  status_id: {
    type: Number,
    default: 1,//1=>active, 2=>banned
  },
  avatar: {
    type: String,
    trim: true,
  },
  cover: {
    type: String,
    trim: true,
  },
  birthday: {
    type: Date,
  },
  gender: {
    type: Number,
    default: 1,//1: Nam, 2: Nữ, 3: Les, 4: Gay
  },
  date_created: {
    type: Date,
    default: Date.now
  },
  date_updated: {
    type: Date,
    default: Date.now
  }
});

UserSchema.pre('save', next=>{
  if (this.isNew || this.isModified) {
    this.date_updated = Date.now();
  }
  return next();
});

module.exports = mongoose.model('User', UserSchema);
