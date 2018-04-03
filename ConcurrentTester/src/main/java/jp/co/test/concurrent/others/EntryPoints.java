package jp.co.test.concurrent.others;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EntryPoints {

	public static void main(String[] args) {

		final BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();
		
		int loopTime = 20;
		// キューに要素を追加するスレッド
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < loopTime; i++) {
					System.out.println("parent");
					try {
						blockingQueue.put("PUT");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		// キューから要素を取得するスレッド
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < loopTime; i++) {
					System.out.println("child");
					try {
						blockingQueue.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		System.out.println("end");
	}

}
