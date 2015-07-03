package com.github.hayataka.tetris;

import static com.github.hayataka.tetris.impl.Settings.WebSocket.contextPath;
import static com.github.hayataka.tetris.impl.Settings.ServerCommon.hostName;
import static com.github.hayataka.tetris.impl.Settings.Http.docRoot;
import static com.github.hayataka.tetris.impl.Settings.Http.httpPort;
import static com.github.hayataka.tetris.impl.Settings.WebSocket.webSocketPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.DeploymentException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.tyrus.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hayataka.tetris.impl.Settings;
import com.github.hayataka.tetris.impl.TetrisServerEndPoint;

/**
 * エントリポイント.
 * <p>
 * grizzlyのwebsocketのサーバ及びSimpleなHttpServerの起動制御を行う
 * </p>
 * 
 * @author hayakawatakahiko
 *
 */
public class App {

	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) throws DeploymentException, IOException {
		runServer();
	}

	/**
	 * WebSocketのサーバを起動する.
	 * <p>
	 * 起動に関する情報は {@link Settings} に記載あり ServerをStopするには、標準入力に何らかのキーを入力する.
	 * </p>
	 * 
	 * @throws DeploymentException
	 * @throws IOException
	 */
	public static void runServer() throws DeploymentException, IOException {

		Map<String, Object> properties = new HashMap<>();

		// websocket
		Server server = new Server(hostName, webSocketPort, contextPath, properties, TetrisServerEndPoint.class);
		// httpServer
		HttpServer httpServer = HttpServer.createSimpleServer(docRoot, hostName, httpPort);

		try {
			server.start();
			httpServer.start();
			log.info("Settings.javaに記載のlocalhostの箇所を自身のIPアドレスに変えてから、ブラウザでのアクセスもそのIPにしてアクセスしてください.");
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
				log.info("Please press a key to stop the server.");
				reader.readLine();
			}
		} finally {
			stop(server);
			if (httpServer != null && httpServer.isStarted()) {
				httpServer.shutdownNow();
			}
		}
	}
	
	/**
	 * 例外発生時にlog出力後、意図的に処理を継続する物として作成.
	 * <p>他で再利用されない位置に作成</p>
	 * @param server
	 */
	private static void stop(Server server) {
		if (server == null) {
			return;
		}
		try {
			server.stop();			
		} catch (Exception e) {
			log.error("Server#stopにてエラー発生、処理は継続します：", e);
		}
	}
}
