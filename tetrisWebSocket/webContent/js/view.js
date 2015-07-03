/// <reference path="../libs/lz-string.js" />
/// <reference path="../../typings/jquery/jquery.d.ts" />
/// <reference path="../../typings/underscore/underscore-1.7.0.d.ts" />
/// <reference path="config.js" />
/// <reference path="websocket.js" />
/// <reference path="tetris.js" />

/**
 * ブラウザへの描画を担当する作業は全てこの処理内で行うようにする（理想）
 * TODO （現実）→　cells変数を使って、tetris.jsで更新している
 */

var viewAction = function() {

	var rivalCells = {};
	var cells;

	return {
		socketOpen : function() {
			console.log("openしたよー");
		},
		
		setCells : function(myCells) {
			cells = myCells;
		}, 
		/**
		 * 画面盤のライバルのセルを、private変数に格納しておく
		 */
		setRivalCells : function(enemyCells) {
			rivalCells = enemyCells;
		},

		updatePad : function(event, data) {

			var partsBefore = data.parts;
			var base = data.base;
			var color = data.color;
			for (var i = -1; i < partsBefore.length; i++) {
				var offset = partsBefore[i] || 0;
				cells[base + offset].style.backgroundColor = color;
			}
		},
		updateFills : function(event, data) {
			var fills = data.fills;
			//確定物＝fills を使って、cellsの色を更新			
			// 今動かしている最中の物については、move関数内で、cells及びnowsへ反映済み
			for(var i = 0; i < fills.length; i++) {
				cells[i].style.backgroundColor=fills[i];
			}
		},		
		
		/**
		 * websocketから通信を受領したら動くイベントハンドラ
		 */
		retrieve : function(event) {
			//console.log("messageを受け取ったよ");
			var flgs = event.data.split(":");
			var command = flgs[0];
			var datas = flgs[1];
			var dataString = LZString.decompressFromEncodedURIComponent(datas);
			// console.log(test);
			if (command=="info") {
				//data 形式
				//"{"0":"silver","1":"","2":"","3":"",「中略」,"250":"silver","251":"silver"}" こんな感じ
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
		}
	};
}();

