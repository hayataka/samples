/**
 * tetris全体に関する設定値（変更され無い定数群）を定義.
 */



var tetris = tetris || {};
/**
 * 定数群定義。
 */
tetris.config = function() {
    var width = 12;             // 全体の横幅(セル数）) + あたり判定用+２(左右）)
    var height = 21;            // 全体の高さ(セル数）) + あたり判定用+１(下)
	var moveSpeed = 300;        // 画面再描画速度(ミリ秒）)
	var speed = 30;             // 縦に１セル動くまでに、何回move関数を呼ぶかを指定する。

	// websocket接続先URL
	var uri = "ws://" + location.hostname + ":8120/tetris/game";
    // 現在未使用
	var reConnectSecond=30;     // webSocket再接続試行までの待機秒数 


    return {
        /**
         * @return ゲーム盤面の幅（セル数）（当たり判定込み）を返却します。
         */
        w: function() {
            return width;
        },
        /**
         * @return ゲーム盤面の高さ（セル数）（当たり判定込み）を返却します。
         */
        h: function() {
            return height;
        },
        /**
         * @return 画面再描画速度（ms）を返却します。
         */
        ms: function() {
            return moveSpeed;
        },
        /**
         * 縦に１セル動くまでに、何回move関数を実行するかを定義する.
         */
        s: function() {
            return speed;
        },
        /**
         * settimeout関数に渡す、再度move関数を呼び出すまでの時間を定義
         */
        interval: function() {
            return moveSpeed / speed;
        },
        reConnect: function() {
            return reConnectSecond;
        },

        /**
         * 通信圧縮から元の色名に戻すためのmap
         */
        blockDecompress : function() {
            var bks = {
                c: 'cyan',    
                y: 'yellow',
                g: 'green',
                r: 'red',
                a: 'aqua',
                o: 'orange',
                m: 'magenta',
                s: 'silver',
                b: 'black'
            };
            return bks;       
        },

        // TODO いづれ、２次元配列及び、 sin cos tan 使って適宜の計算で形を作れるようにしたい
        blocks: function() {
            var w = width;            
            // 角度を変えた時の情報を、先に計算した結果として保持しておく
            // 1次元配列で定義しているので、角度を変えた時などに  幅分変更してたりする
            var bks = [
					{
						color : 'cyan',
						angles : [ [ -1, 1, 2 ], [ -w, w, w + w ], [ -2, -1, 1 ],
								[ -w - w, -w, w ] ]
					},
					{
						color : 'yellow',
						angles : [ [ -w - 1, -w, -1 ] ]
					},
					{
						color : 'green',
						angles : [ [ -w, 1 - w, -1 ], [ -w, 1, w + 1 ],
								[ 1, w - 1, w ], [ -w - 1, -1, w ] ]
					},
					{
						color : 'red',
						angles : [ [ -w - 1, -w, 1 ], [ 1 - w, 1, w ],
								[ -1, w, w + 1 ], [ -w, -1, w - 1 ] ]
					},
					{
						color : 'aqua',
						angles : [ [ -w - 1, -1, 1 ], [ -w, 1 - w, w ],
								[ -1, 1, w + 1 ], [ -w, w - 1, w ] ]
					},
					{
						color : 'orange',
						angles : [ [ 1 - w, -1, 1 ], [ -w, w, w + 1 ],
								[ -1, 1, w - 1 ], [ -w - 1, -w, w ] ]
					},
					{
						color : 'magenta',
						angles : [ [ -w, -1, 1 ], [ -w, 1, w ], [ -1, 1, w ],
								[ -w, -1, w ] ]
					} ];
            return bks;
        },
        u : function() {
        	return uri;
        }
        
    };
}();

