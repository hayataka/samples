package jp.co.test.concurrent.executors;

public class Consumer implements Runnable {

	private final TargetDto dto;

	public Consumer(TargetDto dto) {
		this.dto = dto;
	}

	@Override
	public void run() {
		System.out.println("Consumed: " + dto.getId());
	}

}