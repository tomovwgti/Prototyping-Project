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

    // サーバに送信
    $(this).keydown(function (key) {
        if (key.keyCode === 13) {
            var send_message = $('#send').val();
            console.log('send message --> ' + send_message);
            ws.send(send_message);
            $('#send').val('');
        }
    });

    // サーバから受信
    ws.onmessage = function (event) {
        var receive_message = event.data;
        console.log('receive message <-- ' + receive_message);
        $('body').append('<li>' + receive_message + '</li>');
        // 受信文字列のスキーマを取得してgeoなら地図を開く
        var schema = $.url(receive_message).attr('protocol');
        if (-1 != schema.indexOf('geo')) {
            var map = 'http://maps.google.co.jp/?ie=UTF8&ll=' + $.url(receive_message).attr('host') + '&z=13';
            document.location = map;
        }
    }
}(this));

