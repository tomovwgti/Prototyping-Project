/**
 * Created with JetBrains WebStorm.
 * User: tomo
 * Date: 12/04/20
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */

/*
// ver.1
var http = require("http");

http.createServer(function(request, response) {
    response.writeHead(200, {"Content-Type": "text/html"});
    response.write("Hello World");
    response.end();
}).listen(8888);
*/
/*
// ver.2
var http = require("http");

function onRequest(request, response) {
    console.log("Request received.");
    response.writeHead(200, {"Content-Type": "text/html"});
    response.write("Hello World");
    response.end();
}

http.createServer(onRequest).listen(8888);
console.log("Start Server");
*/
/*
// ver.3
var http = require("http");

function start() {
    function onRequest(request, response) {
        console.log("Request received.");
        response.writeHead(200, {"Content-Type": "text/html"});
        response.write("Hello World");
        response.end();
    }

    http.createServer(onRequest).listen(8888);
    console.log("Start Server");
}

exports.start = start;
*/
/*
// ver.5
var http = require("http");
var url = require("url");

function start() {
    function onRequest(request, response) {
        var pathname = url.parse(request.url).pathname;
        console.log("Request for " + pathname + " received.");
        response.writeHead(200, {"Content-Type": "text/html"});
        response.write("Hello World");
        response.end();
    }

    http.createServer(onRequest).listen(8888);
    console.log("Start Server");
}

exports.start = start;
*/
/*
// ver.5
var http = require("http");
var url = require("url");

function start(route) {
    function onRequest(request, response) {
        var pathname = url.parse(request.url).pathname;
        console.log("Request for " + pathname + " received.");

        route(pathname);

        response.writeHead(200, {"Content-Type": "text/html"});
        response.write("Hello World");
        response.end();
    }

    http.createServer(onRequest).listen(8888);
    console.log("Start Server");
}

exports.start = start;
*/
/*
// ver.6
var http = require("http");
var url = require("url");

function start(route, handle) {
    function onRequest(request, response) {
        var pathname = url.parse(request.url).pathname;
        console.log("Request for " + pathname + " received.");

        response.writeHead(200, {"Content-Type": "text/html"});
        var content = route(handle, pathname);
        response.write(content);
        response.end();
    }

    http.createServer(onRequest).listen(8888);
    console.log("Start Server");
}

exports.start = start;
*/
/*
// ver.7
var http = require("http");
var url = require("url");

function start(route, handle) {
    function onRequest(request, response) {
        var pathname = url.parse(request.url).pathname;
        console.log("Request for " + pathname + " received.");

        route(handle, pathname, response);
    }

    http.createServer(onRequest).listen(8888);
    console.log("Start Server");
}

exports.start = start;
*/
// ver.8
var http = require("http");
var url = require("url");

function start(route, handle) {
    function onRequest(request, response) {
        var postData = "";
        var pathname = url.parse(request.url).pathname;
        console.log("Request for " + pathname + " received.");

        request.setEncoding("utf8");

        request.addListener("data", function(postDataChunk) {
            postData += postDataChunk;
            console.log("Received POST data chunk " + postDataChunk + "'.");
        })

        request.addListener("end", function() {
            route(handle, pathname, response, postData);
        })
    }

    http.createServer(onRequest).listen(8888);
    console.log("Start Server");
}

exports.start = start;
