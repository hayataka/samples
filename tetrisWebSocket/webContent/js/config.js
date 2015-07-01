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
	var speed = 30;             // 

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
        }
    };
}();

