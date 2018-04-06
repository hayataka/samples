package jp.co.test.concurrent.executors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Executorsを使う
 */
public class Producer {
	
	public static void main(String[] args) {
		Producer me = new Producer();
		me.start();
	}
	void start() {

		String stopFilePath = "./stopFile.txt";

		int threadSize = 1; // ここは本来propertyから取得する
		ExecutorService service = Executors.newFixedThreadPool(threadSize);

		File stopFile = new File(stopFilePath );		
		// 親スレッド＝Producer側の動作を規定する
		while (loop(service, stopFile)) {
		}
	}

	boolean loop(ExecutorService service, File stopFile) {
		//本来はDBからデータを取ってきて、
		List<TargetDto> list = new ArrayList<>();
		for(TargetDto dto : list) {
			service.execute(new Consumer(dto));
		}
		
		int seconds = 2;
		if (isStopCondition(stopFile) || !sleep(seconds)) {
			//  子スレッドへの終了を伝える
			// java側は、終了通知受け取りにくいので、諦めてファイル検知パターンにしましょう
			// →jsvc形式なら受け取れるようなので、これが良さそう
			return false;
		} else {
			return true;
		}

	}
	
	boolean isStopCondition(File stopFile) {
		// javaのプロジェクトだとprocessのシグナルを受け取るのが問題ありのため、
		// 特定ディレクトリに特定のファイル名があるかどうかでチェックする		
		return stopFile.exists();
	}


	boolean sleep(int seconds) {
		int millis = 1000;
		int waitTime = seconds * millis;
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			return false;
		}	
		return true;
	}
}
