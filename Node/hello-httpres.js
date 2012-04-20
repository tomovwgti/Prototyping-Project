var http = require('http');

// http://localhost:8000/?a=hogehoge"
var s = http.createServer(function(req, res) {
    res.writeHead(200, {'content-type': 'text/plain'});
    res.write("hello\n");

    console.log("METHOD: " + req.method);
    console.log("URL: " + req.url);
    console.log(req.headers);

    var urlinfo = require('url').parse(req.url, true);

    console.log( urlinfo );

    res.end(urlinfo.query.a);
});
s.listen(8000);
