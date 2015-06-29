package com.github.hayataka.tetris.impl;

public class Settings {

	public class ServerCommon {
		public static final String hostName = "localhost";		
	}
	
	public class Http {
		public static final String docRoot = "./webContent";
		public static final int httpPort = 8080;
	}
	
	public class WebSocket {

		public static final int webSocketPort= 8120;
		public static final String contextPath="/tetris";
		public static final String value="/game";
		/** webSocketServerの起動情報.**/
		public static final String uri = "ws://" + ServerCommon.hostName + ":" + webSocketPort + contextPath + value;
	}

	public class Tags {
		/** コマンドと内容を分けるセパレータ**/
		public static final String sepalator = ":";
		public static final String info = "info";
		public static final String start = "start";
		public static final String sorry = "sorry";

		/**clientからの明示的な終了要求.**/
		public static final String quit = "quit";
	}
}
