
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , http = require('http')
  , path = require('path');

var app = express()
    , http = require('http')
    , server = http.createServer(app)
    , io = require('socket.io').listen(server);

app.configure(function(){
  app.set('port', process.env.PORT || 3000);
  app.set('views', __dirname + '/views');
  app.set('view engine', 'ejs');
  app.use(express.favicon());
  app.use(express.logger('dev'));
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(express.cookieParser('your secret here'));
  app.use(express.session());
  app.use(app.router);
  app.use(express.static(path.join(__dirname, 'public')));
});

app.configure('development', function(){
  app.use(express.errorHandler());
});

// Routes
app.get('/', routes.index);
// Chat Sample
app.get('/chat', routes.chat);
// Web Command
app.get('/webcommand', routes.webcommand);

// ソケットを作る
var socketIO = require('socket.io');
// クライアントの接続を待つ(IPアドレスとポート番号を結びつけます)
server.listen(3000);

// クライアントが接続してきたときの処理
io.sockets.on('connection', function(socket) {
    console.log("connection");
    // メッセージを受けたときの処理
    socket.on('message', function(data) {
        // つながっているクライアント全員に送信
        console.log("message");
        socket.broadcast.emit('message', { value: data.value });
//        io.sockets.emit('message', { value: data.value });
    });

    // クライアントが切断したときの処理
    socket.on('disconnect', function(){
        console.log("disconnect");
    });
});