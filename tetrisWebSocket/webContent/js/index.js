/// <reference path="../libs/lz-string.js" />
/// <reference path="../../typings/jquery/jquery.d.ts" />
/// <reference path="../../typings/underscore/underscore-1.7.0.d.ts" />
/// <reference path="../../typings/tetris/tetris.all.d.ts" />
/// <reference path="websocket.js" />
/// <reference path="tetris.js" />
/// <reference path="view.js" />


/**
 * 初期描画時に行うことを設定する.
 * $(document).ready と等価
 */ 
 $(function() {
	 var tetris =  new Tetris();
	 tetris.config.blockDecompress();
	 
	 
	
	// 初期描画として行うこと
	// 盤面を設定に応じて作る
	var padData = tetris.game.preparePad();
	$('#playerGame').html(padData);
	$('#rivalGame').html(padData);

	// keyboardで操作できるように突っ込んでおく
	tetris.game.keyboardEvent();

	viewAction.setCells($('#playerGame td'));
	viewAction.setRivalCells($('#rivalGame td'));


	// webSocket接続試行.
	tetris.websocket.open(viewAction.socketOpen,viewAction.retrieve, viewAction.close, viewAction.error);

	var o = {};	// event object
	$(o).on('updatePad', function (event,data) {
		viewAction.updatePad(event, data);
	});
	$(o).on('updateFills', function (event,data) {
		viewAction.updateFills(event, data);
	});	

	// ボタンをクリックしたら、tetrisを動かし始める
	$('#gameStart').click(function() {
		tetris.game.gameInitial($(o));
	});
});
 