/**
 * Created with JetBrains WebStorm.
 * User: tomo
 * Date: 12/04/20
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */

/*
function route(pathname) {
    console.log("About to route a request for " + pathname);
}

exports.route = route;
*/
/*
// ver.5
function route(handle, pathname) {
    console.log("About to route a request for " + pathname);
    if (typeof handle[pathname] === 'function') {
        return handle[pathname]();
    } else {
        console.log("No requerst handler found for" + pathname);
        return "404 Not Found";
    }
}

exports.route = route;
*/
/*
// ver.6
function route(handle, pathname, response) {
    console.log("About to route a request for " + pathname);
    if (typeof handle[pathname] === 'function') {
        return handle[pathname](response);
    } else {
        console.log("No requerst handler found for" + pathname);
        response.writeHead(404, {"Content-Type": "text/plain"});
        response.write("404 Not Found");
        response.end();
    }
}

exports.route = route;
*/
// ver.7
function route(handle, pathname, response, postData) {
    console.log("About to route a request for " + pathname);
    if (typeof handle[pathname] === 'function') {
        return handle[pathname](response, postData);
    } else {
        console.log("No requerst handler found for" + pathname);
        response.writeHead(404, {"Content-Type": "text/plain"});
        response.write("404 Not Found");
        response.end();
    }
}

exports.route = route;
