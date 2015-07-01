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
        s: function() {
            return speed;
        },
        interval: function() {
            return moveSpeed / speed;
        },
        reConnect: function() {
            return reConnectSecond;
        },
        blocks: function() {
            var w = width;            
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
						color : 'blue',
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
        }
        
    };
}();

