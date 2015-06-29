package com.github.hayataka.tetris;

import static com.github.hayataka.tetris.Constants.Tetris.contextPath;
import static com.github.hayataka.tetris.Constants.Tetris.hostName;
import static com.github.hayataka.tetris.Constants.Tetris.port;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.tyrus.server.Server;

public class TetrisWebSocketServer {

	public static void main(String[] args) {
		runServer();
	}

	/**
	 * WebSocketのサーバを起動する. 起動に関する情報は {@link Constants.Tetris} に記載あり
	 * ServerをStopするには、標準入力に何らかのキーを入力する.
	 */
	public static void runServer() {

		Map<String, Object> properties = new HashMap<>();
		Server server = new Server(hostName, port, contextPath, properties, TetrisServerEndPoint.class);
		HttpServer httpServer = HttpServer.createSimpleServer("./webContent", "localhost", 8080);

		try {
			server.start();
			httpServer.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Please press a key to stop the server.");
			reader.readLine();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			server.stop();
			httpServer.shutdownNow();
		}
	}
}
