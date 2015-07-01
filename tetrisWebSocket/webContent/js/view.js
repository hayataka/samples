/// <reference path="../libs/lz-string.js" />
/// <reference path="../../typings/jquery/jquery.d.ts" />
/// <reference path="../../typings/underscore/underscore-1.7.0.d.ts" />
/// <reference path="config.js" />
/// <reference path="websocket.js" />
/// <reference path="tetris.js" />

/**
 * ブラウザへの描画を担当する作業は全てこの処理内で行うようにする
 */



var viewAction = function() {

	return {
		socketOpen : function() {
			console.log("openしたよー");
		},
		retrieve : function(event) {
			console.log("messageを受け取ったよ");
			var flgs = event.data.split(":");
			var commdand = flgs[0];
			var datas = flgs[1];
			var test = LZString.decompressFromEncodedURIComponent(datas);
			console.log(test);
		},
		close : function() {
			console.log("closeしたよ");
		},
		error : function() {
			tetris.websocket.cannotConnect();
		}
	};
}();

		
//	function onMessage(event) {
//		console.log("受信しました");

		
		



/**
 * 初期描画時に行うことを設定する
 */ 
 $(function() {
	 
	// 初期描画として行うこと
	// 盤面を設定に応じて作る
	var padData = tetris.game.preparePad();
	$('#view').html(padData);
	$('#rivalView').html(padData);

	tetris.websocket.open(viewAction.socketOpen,viewAction.retrieve, viewAction.close, viewAction.error);

});