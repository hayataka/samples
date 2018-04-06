package jp.co.test.concurrent.executors;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.sample.mapper.SampleMapper;

public class Consumer implements Runnable {

	private final TargetDto dto;
	private final BatchSqlExecutor executor;
	public Consumer(SqlSessionFactory factory, TargetDto dto) {
		this.executor = new BatchSqlExecutor(factory);
		this.dto = dto;
	}

	@Override
	public void run() {

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
		
		System.out.println("Consumed: " + dto.getId());
	}

}