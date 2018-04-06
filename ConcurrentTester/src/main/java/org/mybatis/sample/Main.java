package org.mybatis.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.sample.mapper.SampleMapper;

public class Main {
	public static void main(String[] args) throws IOException {
		Main me = new Main();
		me.process();
	}	

	private SqlSessionFactory factory = null;
	
	public void process() throws IOException {
		factory = null;
		// 設定ファイルの読み込み一度だけ
		try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
			// applicationScopeで使う
			factory = new SqlSessionFactoryBuilder().build(in);
		}

		doSql(session ->{
			SampleMapper mapper = session.getMapper(SampleMapper.class);
			List<Map<String, Object>> result = mapper.selectTest();
			System.out.println(session.getConnection());
			result.forEach(row -> {
				row.forEach((columnName, value) -> {
	//				System.out.printf("columnName=%s, value=%s%n", columnName, value);
				});
			});
		});
	}	
	
	public void doSql(Consumer<SqlSession> sql) {
		boolean autoCommit = true;
		try (SqlSession session = factory.openSession(autoCommit)) {
			sql.accept(session);
		}		
	}
	
	

	
	
}
