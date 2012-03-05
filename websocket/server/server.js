var sys = require('util');
var ws = require('websocket-server'); 
var sockets = [];
  
var server = ws.createServer();
   
server.addListener('listening', function(nonnection) {
    sys.puts('listening..');
});
server.addListener('connection', function(connection){
    sockets.push(connection);
    sys.puts('connect');

    connection.addListener('message', function(message) {
        sys.puts(message);
        for (var k in sockets) {
            if (connection != sockets[k]){
                sockets[k].send(message);
            }
        }
    });
});
    
// 接続が切断された際のリスナー
server.addListener('close', function(connection){
    sys.puts('close');
});
// listenするポート番号を指定
server.listen(8001);
