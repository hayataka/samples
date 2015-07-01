/// <reference path="../libs/lz-string.js" />
/// <reference path="../../typings/jquery/jquery.d.ts" />
/// <reference path="../../typings/underscore/underscore-1.7.0.d.ts" />
/// <reference path="config.js" />
/// <reference path="websocket.js" />
// typeScriptについては探すならここが良さそう
// https://github.com/borisyankov/DefinitelyTyped


// 今のところは、１次元配列で、 左上から右に行って、端まで行ったら次の行（下）の左端というデータ構造
// 後で二次元配列に変更するつもり

/**
 * configがメインで作成
 */
var tetris = tetris || {};
tetris.game = function() {
	var keys = {};

 	var top = 2; //画面に最初に描画されたのが(２７０度変換された状態であっても）画面にテトリス要素がすべて表示されるようにするため)
 	var topBefore = top; //「1回前の」状態を保存する系統の変数
	var width = tetris.config.w(); 
	var height = tetris.config.h();
	var left = Math.floor(width / 2); //画面の真ん中に初期配置
 	var leftBefore = left;

 	var w = width; //省略記述用。
 	var angle = 0;
 	var angleBefore = angle;
 	var partsBefore = [];
 	var score = 0;
 	var scoreBefore = score;
 	var block = {};
 	var cells = {};
	var tick = 0;
	var speed = tetris.config.s();
	var blocks = tetris.config.blocks();
	var fills = []; 		// 確定済みの、もう動か無いブロック類が保持されている変数
	var nows = [];          // まだ操作中の、まだ動かせるブロック類が保持されている変数
	

// fills の概念が、「もう確定していて動かない項目」と定義しているのに対して、今操作中
// オブジェクトを混ぜてしまうと、　自分自身とぶつかって動けなくなってしまうので
// fillsで全てを解決でき無い

		/**
		 * fills[]に当たり判定のための壁を作る
		 */
	var	prepareMemory = function() {
			var width = tetris.config.w();
			var height = tetris.config.h();

			// 定数から動的算出系統
			var lastRows = width * (height -1); 	
			var z = _.range(0, width * height);	// このデータ構造（１次元配列）の数だけ生成
			// 条件分岐付きテンプレート		
			var tpl ="<% if (wall) { %>" +
							"silver" +
		             "<% } else { %>" +
							"" +
		            "<% }%>";
			var compiled = _.template(tpl);
			// config定義分、テンプレートに食わせて文字列を作成
			var memory = _.map(z, function(num,index){
				// 先に計算をしておいて、template時には結果のみ参照
				var obj = {
					 wall: index % width == 0 || index % width == width -1 || index >= lastRows
				};
				return compiled(obj);
			});
			fills = memory;
			return memory;
		};

	return {
		/**
		 * ゲーム盤面となるtrタグ、tdタグの文字列情報を返却する.
		 * ゲーム開始ボタンを押す前までに、事前状況を作っておく
		 * @return tr や tdタグを、configで設定した分の文字列情報
		 */	   
		preparePad: function() {
			var data = prepareMemory();

			var width = tetris.config.w();
			var height = tetris.config.h();
			var lastRows = width * (height -1); 	

			// テンプレート		
			var rowStart = "<tr>";
			var wall     = "<td style='background-color:silver'></td>";
			var tdNormal = "<td></td>";
			var rowEnd   = "</tr>";

			// 条件分岐付きテンプレート		
			var tpl ="<% if (leftWall) { %>" +
						rowStart + wall + 
		             "<% } else if (rightWall) { %>" +
									wall + rowEnd +
		             "<% } else if (buttomWall) { %>" +
									wall +						   
		             "<% } else { %>" +
									tdNormal +
		            "<% }%>";
			var compiled = _.template(tpl);

			// config定義分、テンプレートに食わせて文字列を作成
			var trData = _.map(data, function(num,index){
				// 先に計算をしておいて、template時には結果のみ参照
				var obj = {
					pos: num
					,leftWall: index % width == 0
					,rightWall: index % width == width -1
					,buttomWall: index >= lastRows
				};
				return compiled(obj);
			});
			return trData.join('');
        },
		/**
		 * keyboardイベントを変数に格納しておく.
		 */
		keyboardEvent: function() {
			// キーボード入力を受け付ける
			document.onkeydown = function(e) {
				switch ((e || event).keyCode) {
				case 37: // キーボードの左キー
					keys.left = true;
					break;
				case 39: // キーボードの右キー
					keys.right = true;
					break;
				case 38: //キーボードの上キー
					keys.rotate = true;
					break;
				case 40:
					keys.down = true;
					break;
				default:
					// keyboardで何を押されたのかを、consoleに出すことで後々変更できるようにしよう.
					console.log(e.keyCode);
					break;
				}
			};
		},

		/**
		 * げーむすたーとボタンを押した後に、最初に動く挙動を定義する.
		 */
		gameInitial : function() {
			block = blocks[Math.floor(Math.random() * blocks.length)];
			cells = $('#playerGame td');
			setTimeout(tetris.game.move, tetris.config.interval());			
		},
		move: function() {
			tick++;
			leftBefore = left;
			topBefore = top;
			angleBefore = angle;
			
	
			if (tick % speed == 0) {
				top++;
			} else {
				if (keys.left) {
					left--;
				}
				// 別イベント関数 onkeydownにて記録した情報を受け取る
				if (keys.right) {
					left++;
				}
	
				if (keys.down) {
					top++;
				}
	
				if (tick % speed == 0) {
					top++;
				}
	
				if (keys.rotate) {
					angle++;
				}
			}
	
			keys = {}; // 初期化（次のイベントで拾えるようにする）
	
			// 当たり判定処理
			var parts = block.angles[angle % block.angles.length];
			for (var i = -1; i < parts.length; i++) {
				var offset = parts[i] || 0;
				if (fills[top * width + left + offset]) {
	
					if (tick % speed == 0) {
						for (var j = -1; j < partsBefore.length; j++) {
							offset = partsBefore[j] || 0;
							fills[topBefore * width + leftBefore + offset] = block.color;
						}
						if (scoreBefore == score) {
							for ( var d in fills) {
								if (fills[d]) {
									fills[d] = 'black';
								}
							}
							tetris.game.update();

							// このひとかたまりを後で関数にする。
							nows =[];
							for (var i = -1; i < parts.length; i++) {
								nows[top * width + left + offset] = 'black';
							}
							if (tetris.websocket.can()) {
								var sendData = tetris.game.makeSendData();				
								tetris.websocket.sendMessage("info:" + sendData);
							}
							return;
						}
	
						// 削除系処理
						var cleans = 0;
						for (var y = height - 2; y >= 0; y--) {
							var filled = true;
							for (var x = 1; x < width - 1; x++) {
								if (!fills[y * width + x]) {
									filled = false;
									break;
								}
							}
							if (filled) {
								for (var y2 = y; y2 >= 0; y2--) {
									for (var x = 1; x < width - 1; x++) {
										fills[y2 * width + x] = fills[(y2 - 1) * width + x];
									}
								}
								y++;
								cleans++;
	
							}
	
						}
						if (cleans > 0) {
							score += Math.pow(10, cleans) * 10;
							for (var y = height - 2; y >= 0; y--) {
								for (var x = 1; x < width - 1; x++) {
									var color = fills[y * width + x] || '';
									fills[y * width + x] = color;
								}
	
							}
							tetris.game.update();	//これ、なくても良いかも？
						}
	
						//　次のblockを出現させ、上から落とす
						block = blocks[Math.floor(Math.random() * blocks.length)];
						leftBefore = left = Math.floor(width / 2);
						topBefore = top = 2;
						angleBefore = angle = 0;
						partsBefore = parts = block.angles[angle % block.angles.length];
						scoreBefore = score;
					} else {
						//次のイベントタイミングまで保留
						left = leftBefore;
						top = topBefore;
						angle = angleBefore;
						parts = partsBefore;
	
					}
	
					break; //　どこか１か所でもfills内にある＝あたり判定対象
				}
			}
	
			if (top != topBefore) {
				score++;
			}


			// ローカル画面の再描画　
			// TODO ここだけで良いのでは？
			tetris.game.update();


			//一個前の高さのを消して	
			for (var i = -1; i < partsBefore.length; i++) {
				offset = partsBefore[i] || 0;
				cells[topBefore * width + leftBefore + offset].style.backgroundColor = '';
			}

//TODO ここだけどうにかでき無いかなぁ。　fillsには入れられ無いけれども、
			//今の高さのに色を設定する
			nows = []; //初期化し直し
			for (var i = -1; i < parts.length; i++) {
				offset = parts[i] || 0;
				cells[top * width + left + offset].style.backgroundColor = block.color;
				nows[top * width + left + offset] = block.color;
			}
			
			partsBefore = parts;			
			
	
			var info = 'プログラム内の経過：' + tick + '(' + left + ',' + top
					+ ' score: ' + score + ')';
			document.getElementById('info').innerHTML = info;
	
			// var sendTemp = [];
			// for (var i = 0; i < cells.length; i++) {
			// 	sendTemp[i] = cells[i].style.backgroundColor;
			// }
	
			//			webSocket.send(JSON.stringify(sendTemp));
			/* 			alert("befadata:" +                   JSON.stringify(sendTemp).length);
			 alert("befadata:" + LZString.compressToEncodedURIComponent(JSON.stringify(sendTemp)).length); */
			//   5分の１程度
	



			if (tetris.websocket.can()) {
				var sendData = tetris.game.makeSendData();				
				tetris.websocket.sendMessage("info:" + sendData);
			}


			setTimeout(tetris.game.move, tetris.config.interval());
		},
		update: function() {
			//確定物＝fills を使って、cellsの色を更新			
			for(var i = 0; i < fills.length; i++) {
				cells[i].style.backgroundColor=fills[i];
			}
		},
		makeSendData: function() {
			
			// fills及びnowsの情報から、送信予定データを返却する
			// deepCopy ・・・falseで、shallowCopyでもいい気がする			
			var copyData = jQuery.extend(true, {}, fills);
			for (var i = 0;i < nows.length;i++) {
				if (nows[i]) {
					copyData[i] = nows[i];
				}
			}
			var jsonData = JSON.stringify(copyData);
			console.log("圧縮前：" + jsonData.length);

			var sendData = LZString.compressToEncodedURIComponent(jsonData);
			console.log("圧縮後：" + sendData.length);	
			return sendData;
		}
    };
}();

			//			webSocket.send(JSON.stringify(sendTemp));


//window.onload = function() {
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

//	}
//

//	move();
//}
