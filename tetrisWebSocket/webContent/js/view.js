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

	var rivalCells = {};

	return {
		socketOpen : function() {

			console.log("openしたよー");
		},
		setRivalCells : function(cells) {
			rivalCells = cells;
		},
		
		retrieve : function(event) {
			//console.log("messageを受け取ったよ");
			var flgs = event.data.split(":");
			var command = flgs[0];
			var datas = flgs[1];
			var dataString = LZString.decompressFromEncodedURIComponent(datas);
			// console.log(test);
			if (command=="info") {
				//data 形式
				//"{"0":"silver","1":"","2":"","3":"",「中略」,"250":"silver","251":"silver"}"
				var obj = JSON.parse(dataString);
				for (var i = 0; i< rivalCells.length; i++) {
					rivalCells[i].style.backgroundColor = obj[i];
				}

			}


		},
		close : function(event) {
			//var miliSecond = tetris.config.reConnect() * 1000;
			console.log("closeしたよ,code:" + event.code);
			// なんか変な挙動した。１回失敗して２回目接続試行で、失敗するはずなのにonOpenが呼ばれてた
			// console.log(miliSecond + "後に再接続を試行します");
		    // setTimeout("viewAction.socketOpen()", miliSecond);
		},
		error : function() {
			tetris.websocket.cannotConnect();
		},
		/**
		 * fills配列に入っている状態を盤面に反映する
		 */
		memoryToBrowser(fills) {
			

			
			
			
		}
	};
}();


/**
 * 初期描画時に行うことを設定する
 */ 
 $(function() {

	// 初期描画として行うこと
	// 盤面を設定に応じて作る
	var padData = tetris.game.preparePad();
	$('#playerGame').html(padData);
	$('#rivalGame').html(padData);

	// keyboardで操作できるように突っ込んでおく
	tetris.game.keyboardEvent();

	$('#gameStart').click(function() {
		tetris.game.gameInitial();
	});


	viewAction.setRivalCells($('#rivalGame td'));

	// webSocket接続試行.
	tetris.websocket.open(viewAction.socketOpen,viewAction.retrieve, viewAction.close, viewAction.error);
	
	
	
	
	
});