package jp.co.test.concurrent.executors;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.sample.mapper.SampleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executorsを使う
 */
public class Producer implements Daemon {

	private SqlSessionFactory factory;
	private ExecutorService service;
	private BatchSqlExecutor executor;

	private static final Logger log = LoggerFactory.getLogger(Producer.class);

	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		// 設定ファイルの読み込み一度だけ
		try (InputStream in = getClass().getResourceAsStream("/mybatis-config.xml")) {
			factory = new SqlSessionFactoryBuilder().build(in); // applicationScope
		}
		executor = new BatchSqlExecutor(factory);
		int threadSize = 3; // ここは本来propertyから取得する
		service = Executors.newFixedThreadPool(threadSize);
	}

	@Override
	public void start() throws Exception {
		try {
			while (loop()) {
			}
		} finally {
			stop();
		}
	}

	boolean loop() {
		// 本来はDBからデータを取ってきて、
		List<TargetDto> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			TargetDto dto = new TargetDto();
			dto.setId(i);
			list.add(dto);
		}
		
		executor.doSql(session -> {
			SampleMapper mapper = session.getMapper(SampleMapper.class);
			List<Map<String, Object>> result = mapper.selectTest();
			System.out.println(session.getConnection());
			result.forEach(row -> {
				row.forEach((columnName, value) -> {
					// System.out.printf("columnName=%s, value=%s%n",
					// columnName, value);
				});
			});
		});

		for (TargetDto dto : list) {
			service.execute(new Consumer(factory, dto));
		}

		int seconds = 2;
		return sleep(seconds);
	}

	boolean sleep(int seconds) {
		int millis = 1000;
		int waitTime = seconds * millis;
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			log.warn("親thread側のsleepが中断されています");
			return false;
		}
		return true;
	}

	@Override
	public void stop() throws Exception {
		if (service == null) {
			return;
		}

		service.shutdown(); // Disable new tasks from being submitted
		try {
			int awaitTime = 10;
			// Wait a while for existing tasks to terminate
			if (!service.awaitTermination(awaitTime, SECONDS)) {
				service.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!service.awaitTermination(awaitTime, SECONDS))
					log.error("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			service.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void destroy() {
		executor = null;
		service = null;
		factory = null;
	}
}
