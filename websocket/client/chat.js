/**
 * Created by JetBrains WebStorm.
 * User: tomo
 * Date: 12/02/13
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 */

(function (global) {
    // WebSocket
    var ws = new WebSocket('ws://192.168.110.195:8001/');
    var msg = {
        'sender': 'browser',
        'command': 'Message',
        'message': ''
    }

    // サーバに送信
    $(this).keydown(function (key) {
        if (key.keyCode === 13) {
            msg.message = $('#send').val();
            console.log('send message --> ' + JSON.stringify(msg));
            ws.send(JSON.stringify(msg));
            $('#send').val('');
        }
    });

    // サーバから受信
    ws.onmessage = function (event) {
        var receive_message = JSON.parse(event.data);
        console.log('receive message <-- ' + event.data);

        // Command
        switch (receive_message.command) {
            case 'geo':
                var map = 'http://maps.google.co.jp/?ie=UTF8&ll=' + receive_message.geo.lat +',' + receive_message.geo.lon + '&z=13'
                document.location = map;
                break;
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
        }
    }
}(this));

