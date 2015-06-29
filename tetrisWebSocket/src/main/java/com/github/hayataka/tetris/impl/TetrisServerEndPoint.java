package com.github.hayataka.tetris.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.core.TyrusSession;

import com.github.hayataka.tetris.impl.Settings.Tags;

import static com.github.hayataka.tetris.impl.Settings.WebSocket.value;

/**
 * messageDataFormat: command:text. commandList
 * <ul>
 * <li>start</li>
 * <li>sorry</li>
 * 
 * @author hayakawatakahiko
 *
 */
//  こうやって、型を指定させて、テキストから型変換させるconverterを指定させることができるようになった
//@ServerEndpoint(value = value, decoders=Implemented.class, encoders=implemented.class)
@ServerEndpoint(value = value)
public class TetrisServerEndPoint {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private static List<Session> sessions = new CopyOnWriteArrayList<Session>();

	/**
	 * after Session opened. called this method. websocket
	 * connectionにつき、１回だけこの処理は呼ばれる
	 * 
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session) {

		logger.info("Connected ... " + session.getId());
		sessions.add(session);
		judgeStart(sessions, session);
	}

	void judgeStart(List<Session> list, Session session) {
		final int size = list.size();
		if (size == 2) {
			// 2人が接続した -> ゲーム準備ができた
			// session.getOpenSessions() に対する処理と同等
			((TyrusSession) session).broadcast(Tags.start + ":スタートボタンを押してゲームを開始してください");
		} else if (size > 2) {
			// 3人目以降が接続した -> 未対応
			session.getAsyncRemote().sendText(Tags.sorry + "ごめんのび太。このテトリスは二人用なんだ");
		} else if (size < 2) {
			// まだ１人目
			session.getAsyncRemote().sendText(Tags.sorry + "他の人が来るまで待ってください");
		}
	}

	/**
	 * メッセージの受信の度にこのメソッドは呼び出される
	 * 
	 * @param message
	 * @param session
	 * @return play framework v1を見たときにも思ったことだけど ここに手を入れないと仕組みとして苦しすぎるのだなぁ・・・
	 */
	@OnMessage
	public String onMessage(String message, Session session) {

		// prepare
		String[] msgs = message.split(Tags.sepalator);
		if (msgs.length != 2) {
			// 明示的な破棄
			logger.warning("予定外のメッセージが来ています:" + message + ", sessionId:" + session.getId());
			return message;
		}
		final String command = msgs[0];
//		final String context = msgs[1];

		// patterns 
		// 本来は、EndPointそのものを分ける方が自然？　
		//Encode・Decodeを指定できてJavaのオブジェクトとして引数の型を表現できるように
		//なったようなので・・・
		switch (command) {
		case Tags.quit:
			closeSession(session);
			break;
		case Tags.start:
			break;
		case Tags.info:
			// contextには、当処理を呼び出した側の情報が詰まっているので、それを反対の相手に送信する
			Consumer<Session> send = s -> s.getAsyncRemote().sendText(message);
			doOtherSession(send, session);
			break;
		default:
			logger.warning("未設定のコマンドです：" + command);
		}

		return message; // これは自分自身に対して返信するだけ
	}

	void closeSession(Session session) {
		try {
			session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game ended"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * closeした「後」に呼び出される処理
	 * 
	 * @param session
	 * @param closeReason
	 */
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
		Consumer<Session> send = s -> s.getAsyncRemote().sendText(Tags.sorry + "相方は抜けたみたいだよ");
		doOtherSession(send, session);
		sessions.remove(session);
	}
	
	void doOtherSession(Consumer<Session> consumer, Session session) {

		// TODO 他に良い方法があるか後で探す。
		// eclipse->window->設定->java->code style->formatter->edit->on/off tags		
		//@formatter:off
		final String nowId = session.getId();
		sessions.stream()
			.filter (s -> {return !s.getId().equals(nowId);})
			.forEach(consumer);
		//@formatter:on
	}
	
	
}