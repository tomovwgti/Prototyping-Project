/**
 * Created with JetBrains WebStorm.
 * User: tomo
 * Date: 12/04/20
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */

/*
// ver.1
function start() {
    console.log("Request handler 'start' was called.");
    return "Hello Start";
}

function upload() {
    console.log("Request handler 'upload' was called.");
    return "Hello Upload";
}

exports.start = start;
exports.upload = upload;
*/
/*
// ver.2
var exec = require("child_process").exec;

function start() {
    console.log("Request handler 'start' was called.");
    var content = "empty";

    exec("find /", function(error, stdout, stderr) {
        content = stdout;
        console.log(content);
    });

    return content;
}

function upload() {
    console.log("Request handler 'upload' was called.");
    return "Hello Upload";
}

exports.start = start;
exports.upload = upload;
*/
/*
// ver.6
var exec = require("child_process").exec;

function start(response) {
    console.log("Request handler 'start' was called.");

    exec("find /", function(error, stdout, stderr) {
        response.writeHead(200, {"Content-Type": "text/plain"});
        response.write(stdout);
        response.end();
    });
}

function upload(response) {
    console.log("Request handler 'upload' was called.");
    response.writeHead(200, {"Content-Type": "text/plain"});
    response.write("Hello Upload");
    response.end();
}

exports.start = start;
exports.upload = upload;
*/
// ver.7
var querystring = require("querystring");

function start(response) {
    console.log("Request handler 'start' was called.");

    var body = '<html>'+
        '<head>'+
        '<meta http-equiv="Content-Type" content="text/html; '+
        'charset=UTF-8" />'+
        '</head>'+
        '<body>'+
        '<form action="/upload" method="post">'+
        '<textarea name="text" rows="20" cols="60"></textarea>'+
        '<input type="submit" value="Submit text" />'+
        '</form>'+
        '</body>'+
        '</html>';

        response.writeHead(200, {"Content-Type": "text/html"});
        response.write(body);
        response.end();
}

function upload(response, postData) {
    console.log("Request handler 'upload' was called.");
    response.writeHead(200, {"Content-Type": "text/plain"});
    response.write("You've sent the text:" + querystring.parse(postData).text);
    response.end();
}

exports.start = start;
exports.upload = upload;