package jp.co.test.concurrent.korekana;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

	private final BlockingQueue<TargetDto> sharedQueue;
	private int threadNumber;

	public Consumer(BlockingQueue<TargetDto> sharedQueue, int threadNumber) {
		this.sharedQueue = sharedQueue;
		this.threadNumber = threadNumber;
	}

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Consumed: " + sharedQueue.take() + threadNumber);
			} catch (InterruptedException ex) {
				Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}