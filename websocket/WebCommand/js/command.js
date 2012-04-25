/**
 * Created by JetBrains WebStorm.
 * User: tomo
 * Date: 12/04/14
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */

var imgList = ["slider-disabled.png", "slider-disabled-1.png", "slider.png", "slider-1.png"];
var preloadImg = []
for(var i = 0, imgSrc; imgSrc = imgList[i]; i++) {
    preloadImg[i] = new Image();
    preloadImg[i].src = "image/" + imgSrc;
};

// WebSocket
var ws = new WebSocket('ws://192.168.110.195:8001/');

// Create the div used to show the dynamically generated color
function createColorBox() {
    var dl = document.getElementsByTagName('dl')[0];
    var box  = document.createElement('div');
    box.setAttribute('id','colorBox');
    var res  = document.createElement('div');
    res.setAttribute('id','hexValue');

    dl.parentNode.insertBefore(res, dl.nextSibling);
    dl.parentNode.insertBefore(box, res);
    updateColor();

    // Clean up for poor old IE
    res = box = null;
}

// Convert and rgb value to hex
function toHex(N) {
    if (N==null) return "00";
    N=parseInt(N);
    if (N==0 || isNaN(N)) return "00";
    N=Math.max(0,N); N=Math.min(N,255);
    N=Math.round(N);
    return "0123456789ABCDEF".charAt((N-N%16)/16) + "0123456789ABCDEF".charAt(N%16);
}

// Message from Server
ws.onmessage = function (event) {
    var receive_message = JSON.parse(event.data);
    console.log('receive message <-- ' + event.data);

    // Command
    switch (receive_message.command) {
        case 'geo':
            var map = 'http://maps.google.co.jp/?ie=UTF8&ll=' + receive_message.lat +',' + receive_message.lon + '&z=13'
            document.location = map;
            break;
        case 'light':
            updateColorfromWS(receive_message.red, receive_message.green, receive_message.blue);
            break;
    }

    // Message
    switch (receive_message.message) {
        // （」・ω・）」うー！
        case 'uu':
            $('#uu').vtoggle();
            if ($('#uu').css('visibility') === 'visible') {
                uu_sound.play();
            }
            break;
        // （／・ω・）／にゃー！
        case 'nyaa':
            $('#nyaa').vtoggle();
            if ($('#nyaa').css('visibility') === 'visible') {
                uu_sound.play();
            }
            break;
    }
}

function updateColor() {
    var r = parseInt($('#slider-r').val(), 10) || 0;
    var g = parseInt($('#slider-g').val(), 10) || 0;
    var b = parseInt($('#slider-b').val(), 10) || 0;

    $('#colorBox').css("background-color","rgb("+r+ ","+g+","+b+")");
    $('#hexValue').html('#' + toHex(r) + toHex(g) + toHex(b));

    var msg = {
        'sender': 'browser',
        'command': 'light',
        'red': 0,
        'green': 0,
        'blue': 0
    }
    msg.red = r;
    msg.green = g;
    msg.blue = b;
    console.log(JSON.stringify(msg));
    ws.send(JSON.stringify(msg));
}

function updateColorfromWS(red, green, blue) {
    $('#slider-r').attr('value', red);
    $('#slider-g').attr('value', green);
    $('#slider-b').attr('value', blue);
    $('#colorBox').css("background-color","rgb(" + red + "," + green + "," + blue + ")");
    $('#hexValue').html('#' + toHex(red) + toHex(green) + toHex(blue));
}

$(function(){
    var led_state = false;
    $('#chime').click(function() {
        var msg = {
            'sender': 'browser',
            'command': 'chime'
        }
        console.log(JSON.stringify(msg));
        ws.send(JSON.stringify(msg));
    });

    $('#led').click(function() {
        led_state = !led_state;
        var msg = {
            'sender': 'browser',
            'command': 'led',
            'status': false
        }
        msg.status = led_state;
        console.log(JSON.stringify(msg));
        ws.send(JSON.stringify(msg));
    });
});

// Demo specific onload events (uses the addEvent method bundled with the slider)
fdSliderController.addEvent(window, 'load', createColorBox);