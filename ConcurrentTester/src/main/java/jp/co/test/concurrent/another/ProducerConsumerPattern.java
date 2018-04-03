package jp.co.test.concurrent.another;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//http://omiya6048.hatenablog.com/entry/2013/05/29/145253

public class ProducerConsumerPattern {
	public static void main(String args[]) {

		// Creating shared object
		BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>();

		// Creating Producer and Consumer Thread
		Thread prodThread = new Thread(new Producer(sharedQueue));
		Thread consThread = new Thread(new Consumer(sharedQueue));

		// Starting producer and Consumer thread
		prodThread.start();
		consThread.start();
		

		//producerとmainは、同じにできるね
		//java側は、終了通知受け取りにくいので、諦めてファイル検知パターンにしましょう

	}

}