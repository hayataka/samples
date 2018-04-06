package org.mybatis.sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.sample.mapper.SampleMapper;

public class Main {

	public static void main(String[] args) throws IOException {

		SqlSessionFactory factory = null;
		// 設定ファイルの読み込み
		try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
			// applicationScopeで使う
			factory = new SqlSessionFactoryBuilder().build(in);
		}

		boolean autoCommit = true;
		try (SqlSession session = factory.openSession(autoCommit)) {
			// ★SqlSession を使って SQL を実行する
			// List<Map<String, Object>> result =
			// session.selectList("org.mybatis.mapper.SampleMapper.selectTest");
			SampleMapper mapper = session.getMapper(SampleMapper.class);
			List<Map<String, Object>> result = mapper.selectTest();

			result.forEach(row -> {
				System.out.println("---------------");
				row.forEach((columnName, value) -> {
					System.out.printf("columnName=%s, value=%s%n", columnName, value);
				});
			});
		}
	}
}
