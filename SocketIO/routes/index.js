
/*
 * GET home page.
 */

exports.index = function(req, res){
  res.render('index', { title: 'Chat Sample', layout: 'chatlayout' })
};

exports.chat = function(req, res){
    res.render('chat', { title: 'Chat Sample', layout: 'chatlayout' })
};

exports.webcommand = function(req, res){
    res.render('webcommand', { title: 'WebSocket / Node.js DEMO', layout: 'webcommandlayout' })
};