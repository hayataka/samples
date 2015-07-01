/**
 * websocketへの接続や、送信・受信を担当する.
 */

var tetris = tetris || {};

/**
 * Websocketとのやりとりを担当する箇所.
 */
tetris.websocket = function() {
	var uri = "ws://localhost:8120/tetris/game";
	var canConnect = true;							// 初期値true 接続できなかったら一人用で動かすために保持
	var webSocket = null;							// webSocketオブジェクト
    return {
		/**
		 * webSocketにこれから処理をして良いかを確認する。
		 * @return 送信して良いかどうかのフラグ
		 */
        can: function() {
            return canConnect;
        },
		/**
		 * 接続できなかった時にこの処理を呼び出すこと。
		 * 内部フラグを変更する.
		 */
		cannotConnect: function() {
			console.log("websocketへのコネクションがエラーです。一人寂しく動きます。");
			canConnect = false;
		},
		/**
		 * 接続した時に、各種イベントハンドラを登録する.
		 * @param onOpen 接続成功時のハンドラ
		 * @param onMessage メッセージ受信時のハンドラ
		 * @param onClose 切断時のハンドラ
		 * @param onErro エラー発生時のハンドラ
		 */
		open: function(onOpen, onMessage, onClose, onError) {
			// WebSocket の初期化
			webSocket = new WebSocket(uri);
			// イベントハンドラの設定

			// バグの原因確認、解消するまで onOpenは削除
//			webSocket.onopen = onOpen;
			webSocket.onmessage = onMessage;
			webSocket.onclose = onClose;
			webSocket.onerror = onError;
		}
		
    };
}();