
/*
 * GET home page.
 */
/**
 * クライアント
 */
exports.index = function(req, res){
  res.render('index', { title: 'Sample Socket.IO' })
};