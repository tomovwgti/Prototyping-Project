/**
 * Created by JetBrains WebStorm.
 * User: tomo
 * Date: 12/02/13
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */

(function (global) {
    // Socket.IO
    var socket = io.connect();

    var msg = {
        'sender':'browser',
        'command':'Message',
        'message':''
    }

    // サーバに送信
    $(this).keydown(function (key) {
        if (key.keyCode === 13) {
            msg.message = $('#send').val();
            console.log('send message --> ' + JSON.stringify(msg));
            // メッセージを送信する
            socket.emit('message', { value: msg });
            $('#send').val('');
        }
    });

    // メッセージを受けたとき
    socket.on('message', function(event) {

        var receive_message = event.value;
        console.log('receive message <-- ' + receive_message.message);

        // Command
        switch (receive_message.command) {
            case 'geo':
                var map = 'http://maps.google.co.jp/?ie=UTF8&ll=' + receive_message.geo.lat +',' + receive_message.geo.lon + '&z=13'
                document.location = map;
                return;
        }

        // Message
        switch (receive_message.message) {
            // （」・ω・）」うー！
            case 'uu':
                $('body').append('<div> （」・ω・）」うー！ </div>');
                break;
            // （／・ω・）／にゃー！
            case 'nyaa':
                $('body').append('<div> （／・ω・）／にゃー！ </div>');
                break;
            default:
                $('body').append('<div>' + receive_message.message + '</div>');
        }
    });
}(this));

