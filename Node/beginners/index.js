/**
 * Created with JetBrains WebStorm.
 * User: tomo
 * Date: 12/04/20
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */

var server = require("./server");
var router = require("./router");
var requestHandlers = require("./requestHandlers");

var handle = [];
handle["/"] = requestHandlers.start;
handle["/start"] = requestHandlers.start;
handle["/upload"] = requestHandlers.upload;

server.start(router.route, handle);