


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
        can: function() {
            return canConnect;
        },
		cannotConnect: function() {
			console.log("websocketへのコネクションがエラーです。一人寂しく動きます。");
			canConnect = false;
		},
		open: function(onOpen, onMessage, onClose, onError) {
			// WebSocket の初期化
			webSocket = new WebSocket(uri);
			// イベントハンドラの設定
			webSocket.onopen = onOpen;
			webSocket.onmessage = onMessage;
			webSocket.onclose = onClose;
			webSocket.onerror = onError;
		}

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
		
    };
}();
 
//

//	open();
//
