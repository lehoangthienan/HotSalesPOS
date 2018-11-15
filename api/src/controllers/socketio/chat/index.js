var config = require('../../../config');
var Log = require(config.library_dir + '/log');

module.exports = (io) => {
    const nsChat = 'chat';
    const chat = io.of('/'+nsChat);

    //middleware
    chat.use(require(config.library_dir+'/middleware').socketioMiddleware);

    chat.on("connection", function(socket){
        var decoded_token=socket.handshake.decoded_token;

        /**
         * to_id: receiverID
         * message
         */
        socket.on("newMessage", (data)=>{
            Log.d('----------------EVENT new message----------------');
            Log.d("receive message", data);
        });
    });
}
