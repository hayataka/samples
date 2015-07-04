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
 	var angle = 0;
 	var angleBefore = angle;
 	var partsBefore = [];
 	var score = 0;
 	var scoreBefore = score;
 	var block = {};
	var tick = 0;
	var speed = tetris.config.s();
	var blocks = tetris.config.blocks();

	var viewHandle; // 
	
	//今すでに埋まっているものが  fills[i] = 'yellow' のような形で設定している
	var fills = []; 		// 確定済みの、もう動か無いブロック類が保持されている変数



// fills の概念が、「もう確定していて動かない項目」と定義しているのに対して、今操作中
// オブジェクトを混ぜてしまうと、　自分自身とぶつかって動けなくなってしまうので
// fillsで全てを解決でき無い →nowsを作った

	/**
	 * fills[]に当たり判定のための壁を作る
	 * privateメソッドとして定義
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

	var isGameOver = function() {
		return scoreBefore == score;
	};

	var gameOver = function(parts, offset) {

		for (var i in fills) {
			// 操作していたブロックは全て黒くする
			if (fills[i]) {
				fills[i] = 'black';
			}
		}
	
		viewHandle.trigger('updateFills', { fills : fills});
		
		if (tetris.websocket.can()) {
			// このoffsetがないと最後のブロックの位置が、ブラウザと通信相手でずれる
			var sendData = makeSendData(nowCells(parts, 'black', offset)); 
			tetris.websocket.sendMessage(sendData);
		}
	};

	var deleteBlocks = function() {

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
		}
		
	};

	var positionShift = function() {

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
		
	};

	/**
	 * すでに壁・block配置済みの場所に移動しようとしたので移動をキャンセルする
	 */
	var cancelMove = function() {
		for (var j = -1; j < partsBefore.length; j++) {
			var offset = partsBefore[j] || 0;
			fills[topBefore * width + leftBefore + offset] = block.color;
		}
	};

	var nowCells = function(parts, color, offset) {

		var	nows =[];
		if (offset) {
			for (var i = -1; i < parts.length; i++) {
				// gameover時の最後の移動でoffset修正
				nows[top * width + left + offset] = color;
			}
		} else {
			//今の高さのに色を設定する
			for (var i = -1; i < parts.length; i++) {
				offset = parts[i] || 0;
				// 相手への通信のために、今の場所を保持しておく
				nows[top * width + left + offset] = color;
			}
		}
		return nows;
		
	};

		
	var	move = function() {
		tick++;
		leftBefore = left;
		topBefore = top;
		angleBefore = angle;

		positionShift();	//キーイベントを変数に
		keys = {}; 			// 初期化（次のイベントで拾えるようにする）

		// 当たり判定処理　　パーツのどれかが、すでにblockがある位置にぶつかっていたら移動をキャンセルする
		var parts = block.angles[angle % block.angles.length];
		for (var i = -1; i < parts.length; i++) {
			var offset = parts[i] || 0;

			// 当たり判定でぶつかっていて
			if (fills[top * width + left + offset]) {
				//一定時間経過したら
				if (tick % speed == 0) {

					cancelMove();					// 移動をキャンセルさせて
					if (isGameOver()) {
						gameOver(parts, offset);
						return;
					}

					deleteBlocks();					// 削除系処理

					//　次のblockを出現させ、上から落とす
					block = blocks[Math.floor(Math.random() * blocks.length)];
					leftBefore = left = Math.floor(width / 2);
					topBefore = top = 2;
					angleBefore = angle = 0;
					partsBefore = parts = block.angles[angle % block.angles.length];
					scoreBefore = score;
				} else {
					//次のイベントタイミングまで保留
					// 当たり判定でぶつかっていたので前の状態に戻す
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
		viewHandle.trigger('updateFills', { fills : fills});
		// 一つ前の情報を消すため
		viewHandle.trigger('updatePad', {
				base : topBefore * width + leftBefore,
				parts : partsBefore,
				color : ''					
		});

		//今の高さのに色を設定する(ための情報保持)
		var nows = nowCells(parts, block.color);
		viewHandle.trigger('updatePad', {
				base : top * width + left,
				parts : parts,
				color : block.color
		});
		
		
		partsBefore = parts;	// 次のために、今を保持

		var info = '経過：' + tick + '(' + left + ',' + top + ' score: ' + score + ')';
		$('#info').html(info);


		// 二人用で動いている時には、 相手に通知するために 送信する
		if (tetris.websocket.can()) {
			var sendData2 = makeSendData(nows);
			tetris.websocket.sendMessage(sendData2);
		}

		setTimeout(move, tetris.config.interval());
	};
	
	/**
	 * 送信データ作成、圧縮
	 * @param nows 操作中のブロックで、 nows[i] = 'yellow'のような形で設定している。 操作しているものは
	 *             １blockなので、せいぜい４つ入っていて後はundefined扱いになっている
	 */
	var	makeSendData = function(nows) {
			
		// fills及びnowsの情報から、送信予定データを返却する
		// deepCopy ・・・falseで、shallowCopyでもいい気がする			
//		var copyData = jQuery.extend(true, {}, fills);
		
		// 少しでも転送を減らす
		var copy = {};
		for (var i = 0; i < fills.length; i++) {
			if (fills[i] && fills[i] != '' && fills[i] != 'silver') {
				copy[i] = fills[i].substring(0,1);
			}
		}
		var key;
		for(key in nows) {
			copy[key] = nows[key].substring(0,1);
		}
		var wrapper = {};
		wrapper.info = copy;
		
		var jsonData = JSON.stringify(wrapper);
		// console.log("圧縮前：" + jsonData.length);
		// var sendData = LZString.compressToEncodedURIComponent(jsonData);
		// console.log("圧縮後：" + sendData.length);
		
		// なんの工夫もせずに全量送信した場合には、２５００→８００程度に圧縮されたが
		// 転送量を減らした場合には  ３７→３８など　微増微減のため、圧縮しない
		return jsonData;
	};


	/**
	 * publicメソッド.
	 */
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
					console.log('入力されたキー：' + e.keyCode);
					break;
				}
			};
		},

		/**
		 * げーむすたーとボタンを押した後に、最初に動く挙動を定義する.
		 */
		gameInitial : function(obj) {
			block = blocks[Math.floor(Math.random() * blocks.length)];
			// 操作しているユーザ側の 画面描画を実現するために、先に変数として格納。
			setTimeout(move, tetris.config.interval());
			viewHandle = obj;
		}
    };
}();

