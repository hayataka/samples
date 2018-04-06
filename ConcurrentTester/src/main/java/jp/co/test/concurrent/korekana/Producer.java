package jp.co.test.concurrent.korekana;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Producer/Consumerパターンで作成する
 *
 */
public class Producer {
	
	public static void main(String[] args) {
		Producer me = new Producer();
		me.start();
	}
	
	void start() {

		String stopFilePath = "./stopFile.txt";
		
		// スレッド間共有用途のデータobjectを作成する
		BlockingQueue<TargetDto> sharedQueue = new LinkedBlockingQueue<>();

		int threadSize = 1; // ここは本来propertyから取得する
		Thread[] consumers = new Thread[threadSize];

		// Consumerを起動
		startConsumers(sharedQueue, threadSize, consumers);
		File stopFile = new File(stopFilePath );		
		// 親スレッド＝Producer側の動作を規定する
		while (true) {
			//DBからデータを取ってきて、
			TargetDto dto = new TargetDto();
			dto.setId(1);

			//ワーカースレッド、Consumerに受け渡しして
			put(sharedQueue, dto);

			
			int seconds = 2;
			sleep(seconds);
			
			if (isStopCondition(stopFile)) {
				//  子スレッドへの終了を伝える
			}
		}

		// producerとmainは、同じにできるね
		// java側は、終了通知受け取りにくいので、諦めてファイル検知パターンにしましょう

		
	}

	private boolean put(BlockingQueue<TargetDto> sharedQueue, TargetDto dto) {
		try {
			System.out.println("Produced: " + dto.getId());
			sharedQueue.put(dto);
			return true;
		} catch (InterruptedException ex) {
			Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	boolean isStopCondition(File stopFile) {
		// javaのプロジェクトだとprocessのシグナルを受け取るのが問題ありのため、
		// 特定ディレクトリに特定のファイル名があるかどうかでチェックする		
		return stopFile.exists();
	}
	void startConsumers(BlockingQueue<TargetDto> sharedQueue, int threadSize, Thread[] consumers) {
		for (int i = 0; i < threadSize; i++) {
			consumers[i] = new Thread(new Consumer(sharedQueue, i));
			consumers[i].start();
		}
	}


	void sleep(int seconds) {
		int millis = 1000;
		int waitTime = seconds * millis;
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace(); // 挙動を確定させる事
		}
		
	}
}
