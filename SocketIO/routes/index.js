
/*
 * GET home page.
 */

exports.index = function(req, res){
  res.render('index', { title: 'Chat Sample' })
};

exports.chat = function(req, res){
    res.render('chat', { title: 'Chat Sample' })
};