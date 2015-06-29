/// <reference path="../libs/lz-string.js" />
/// <reference path="../../typings/jquery/jquery.d.ts" />
/// <reference path="../libs/underscore-1.8.3.js" />
function preparePad(id, rivalId) {

	var width = 12; // 全体の横幅(セル数）) + あたり判定用+２(左右）)
	var height = 21; // 全体の高さ(セル数）)+ あたり判定用+１
	var moveSpeed = 300; // 画面再描画速度(ミリ秒）)
	var speed = 30;
	var fills = {}; // もう、埋まっている場所のことを指す

	var table = [ '<table class="gamePad">' ];
	for (var y = 0; y < height; y++) {

		table.push('<tr>');

		for (var x = 0; x < width; x++) {
			if (x == 0 || x == width - 1 || y == height - 1) {
				table.push('<td style="background-color:silver"></td>');
				fills[x + y * width] = 'silver';
			} else {
				table.push('<td></td>');
			}

		}
		table.push('</tr>');
	}
	table.push('</table>');

	var padData = table.join('')
	$(id).html(padData);
	$(rivalId).html(padData);
}

$(function() {

	console.debug("ready");	
	preparePad('#view', '#rivalView');
});



//window.onload = function() {
//	// 接続先URI
//	var uri = "ws://localhost:8120/tetris/game";
//
//	// WebSocketオブジェクト
//	var webSocket = null;
//
//	// 接続
//	function open() {
//		if (webSocket == null) {
//			// WebSocket の初期化
//			webSocket = new WebSocket(uri);
//			// イベントハンドラの設定
//			webSocket.onopen = onOpen;
//			webSocket.onmessage = onMessage;
//			webSocket.onclose = onClose;
//			webSocket.onerror = onError;
//		}
//	}
//
//	// 接続イベント
//	function onOpen(event) {
//		console.log("接続しました");
//	}
//
//	// メッセージ受信イベント
//	function onMessage(event) {
//		console.log("受信しました");
//		var flgs = event.data.split(":");
//		var commdand = flgs[0];
//		var datas = flgs[1];
//		var test = LZString.decompressFromEncodedURIComponent(datas);
//		//	        alert(test);
//
//	}
//
//	// エラーイベント
//	function onError(event) {
//		//chat("エラーが発生しました。");
//	}
//
//	// 切断イベント
//	function onClose(event) {
//		/*         chat("切断しました。3秒後に再接続します。(" + event.code + ")");
//		        webSocket = null;
//		        setTimeout("open()", 3000); */
//		console.log("切断した");
//
//	}
//	open();
//
//
//	var top = 2; //画面に最初に描画されたのが(２７０度変換された状態であっても）画面にテトリス要素がすべて表示されるようにするため)
//	var topBefore = top //「1回前の」状態を保存する系統の変数
//	var left = Math.floor(width / 2); //画面の真ん中に初期配置
//	var leftBefore = left;
//
//	var w = width; //省略記述用。
//	var angle = 0;
//	var angleBefore = angle;
//	var partsBefore = [];
//	var score = 0;
//	var scoreBefore = score;
//
//	var blocks = [
//			{
//				color : 'cyan',
//				angles : [ [ -1, 1, 2 ], [ -w, w, w + w ], [ -2, -1, 1 ],
//						[ -w - w, -w, w ] ]
//			},
//			{
//				color : 'yellow',
//				angles : [ [ -w - 1, -w, -1 ] ]
//			},
//			{
//				color : 'green',
//				angles : [ [ -w, 1 - w, -1 ], [ -w, 1, w + 1 ],
//						[ 1, w - 1, w ], [ -w - 1, -1, w ] ]
//			},
//			{
//				color : 'red',
//				angles : [ [ -w - 1, -w, 1 ], [ 1 - w, 1, w ],
//						[ -1, w, w + 1 ], [ -w, -1, w - 1 ] ]
//			},
//			{
//				color : 'blue',
//				angles : [ [ -w - 1, -1, 1 ], [ -w, 1 - w, w ],
//						[ -1, 1, w + 1 ], [ -w, w - 1, w ] ]
//			},
//			{
//				color : 'orange',
//				angles : [ [ 1 - w, -1, 1 ], [ -w, w, w + 1 ],
//						[ -1, 1, w - 1 ], [ -w - 1, -w, w ] ]
//			},
//			{
//				color : 'magenta',
//				angles : [ [ -w, -1, 1 ], [ -w, 1, w ], [ -1, 1, w ],
//						[ -w, -1, w ] ]
//			} ];
//	var block = blocks[Math.floor(Math.random() * blocks.length)]
//
//	var keys = {};
//	var cells = document.getElementsByTagName('td');
//
//	// キーボード入力を受け付ける
//	document.onkeydown = function(e) {
//		switch ((e || event).keyCode) {
//		case 37: // キーボードの左キー
//			keys.left = true;
//			break;
//		case 39: // キーボードの右キー
//			keys.right = true;
//			break;
//		case 38: //キーボードの上キー
//			keys.rotate = true;
//			break;
//		case 40:
//			keys.down = true;
//			break;
//		/**
//		default:
//			console.log(e.keyCode);
//			break;
//		 **/
//		}
//
//	}
//
//	var tick = 0;
//	var move = function() {
//
//		tick++;
//		leftBefore = left;
//		topBefore = top;
//		angleBefore = angle;
//
//		if (tick % speed == 0) {
//			top++;
//		} else {
//			if (keys.left) {
//				left--;
//			}
//			// 別イベント関数 onkeydownにて記録した情報を受け取る
//			if (keys.right) {
//				left++;
//			}
//
//			if (keys.down) {
//				top++;
//			}
//
//			if (tick % speed == 0) {
//				top++;
//			}
//
//			if (keys.rotate) {
//				angle++
//			}
//		}
//
//		keys = {}; // 初期化（次のイベントで拾えるようにする）
//
//		// 当たり判定処理
//		var parts = block.angles[angle % block.angles.length];
//		for (var i = -1; i < parts.length; i++) {
//			var offset = parts[i] || 0;
//			if (fills[top * width + left + offset]) {
//
//				if (tick % speed == 0) {
//					for (var j = -1; j < partsBefore.length; j++) {
//						var offset = partsBefore[j] || 0;
//						fills[topBefore * width + leftBefore + offset] = block.color;
//					}
//
//					if (scoreBefore == score) {
//						for ( var i in fills) {
//							if (fills[i]) {
//								cells[i].style.backgroundColor = 'black';
//							}
//						}
//						return;
//					}
//
//					// 削除系処理
//					var cleans = 0;
//					for (var y = height - 2; y >= 0; y--) {
//						var filled = true;
//						for (var x = 1; x < width - 1; x++) {
//							if (!fills[y * width + x]) {
//								filled = false;
//								break;
//							}
//						}
//						if (filled) {
//							for (var y2 = y; y2 >= 0; y2--) {
//								for (var x = 1; x < width - 1; x++) {
//									fills[y2 * width + x] = fills[(y2 - 1)
//											* width + x];
//								}
//
//							}
//							y++;
//							cleans++;
//
//						}
//
//					}
//					if (cleans > 0) {
//						score += Math.pow(10, cleans) * 10;
//						for (var y = height - 2; y >= 0; y--) {
//							for (var x = 1; x < width - 1; x++) {
//								var color = fills[y * width + x] || '';
//								cells[y * width + x].style.backgroundColor = color;
//							}
//
//						}
//
//					}
//
//					//　次のblockを出現させ、上から落とす
//					block = blocks[Math
//							.floor(Math.random() * blocks.length)];
//					leftBefore = left = Math.floor(width / 2);
//					topBefore = top = 2;
//					angleBefore = angle = 0;
//					partsBefore = parts = block.angles[angle
//							% block.angles.length];
//					scoreBefore = score;
//				} else {
//					//次のイベントタイミングまで保留
//					left = leftBefore;
//					top = topBefore;
//					angle = angleBefore;
//					parts = partsBefore;
//
//				}
//
//				break; //　どこか１か所でもfills内にある＝あたり判定対象
//			}
//		}
//
//		if (top != topBefore) {
//			score++;
//		}
//
//		for (var i = -1; i < partsBefore.length; i++) {
//			var offset = partsBefore[i] || 0;
//			cells[topBefore * width + leftBefore + offset].style.backgroundColor = '';
//		}
//		partsBefore = parts;
//
//		for (var i = -1; i < partsBefore.length; i++) {
//			var offset = partsBefore[i] || 0;
//			cells[top * width + left + offset].style.backgroundColor = block.color;
//		}
//
//		var info = 'プログラム内の経過：' + tick + '(' + left + ',' + top
//				+ ' score: ' + score + ')';
//		document.getElementById('info').innerHTML = info;
//
//		var sendTemp = [];
//		for (var i = 0; i < cells.length; i++) {
//			sendTemp[i] = cells[i].style.backgroundColor;
//		}
//
//		//			webSocket.send(JSON.stringify(sendTemp));
//		/* 			alert("befadata:" +                   JSON.stringify(sendTemp).length);
//		 alert("befadata:" + LZString.compressToEncodedURIComponent(JSON.stringify(sendTemp)).length); */
//		//   5分の１程度
//
//		webSocket.send("info:"
//				+ LZString.compressToEncodedURIComponent(JSON
//						.stringify(sendTemp)));
//
//		setTimeout(move, moveSpeed / speed);
//	}
//

//	move();
//}
