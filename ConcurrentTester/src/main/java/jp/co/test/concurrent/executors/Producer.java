package jp.co.test.concurrent.executors;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.sample.Main;
import org.mybatis.sample.mapper.SampleMapper;


/**
 * Executorsを使う
 */
public class Producer {
	
	public static void main(String[] args) throws IOException {
		Producer me = new Producer();
		me.start();
	}

	private SqlSessionFactory factory = null;
	
	void start() throws IOException {

		String stopFilePath = "./stopFile.txt";
		int threadSize = 3; // ここは本来propertyから取得する

		ExecutorService service = Executors.newFixedThreadPool(threadSize);		
		try {
			File stopFile = new File(stopFilePath );		
			// 設定ファイルの読み込み一度だけ
			try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
				// applicationScopeで使う
				factory = new SqlSessionFactoryBuilder().build(in);
			}
			// 親スレッド＝Producer側の動作を規定する
			while (loop(service, stopFile)) {
			}
			
		} finally {
			service.shutdown();
		}
		

		
	}

	boolean loop(ExecutorService service, File stopFile) {
		//本来はDBからデータを取ってきて、
		List<TargetDto> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			TargetDto dto = new TargetDto();
			dto.setId(i);
			list.add(dto);
		}
		BatchSqlExecutor executor = new BatchSqlExecutor(factory);
		executor.doSql(session ->{
			SampleMapper mapper = session.getMapper(SampleMapper.class);
			List<Map<String, Object>> result = mapper.selectTest();
			System.out.println(session.getConnection());
			result.forEach(row -> {
				row.forEach((columnName, value) -> {
	//				System.out.printf("columnName=%s, value=%s%n", columnName, value);
				});
			});	
		});

		for(TargetDto dto : list) {
			service.execute(new Consumer(factory, dto));
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
		if (stopFile.exists()) {
			System.out.println(stopFile.getAbsolutePath() + "があるので終了");
			return true;			
		} else {
			return false;
		}

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
