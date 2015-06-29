package com.github.hayataka.tetris;

public class Constants {
	public class Tetris {
		public static final String hostName = "localhost";
		public static final int port= 8120;
		public static final String contextPath="/tetris";
		public static final String value="/game";
		/** webSocketServerの起動情報.**/
		public static final String uri = "ws://" + hostName + ":" + port + contextPath + value;
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
