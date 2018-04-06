package jp.co.test.concurrent.executors;

import java.util.function.Consumer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class BatchSqlExecutor {
	private final SqlSessionFactory factory;
	public BatchSqlExecutor(SqlSessionFactory factory) {
		this.factory =factory;
	}
	
	public void doSql(Consumer<SqlSession> sql) {
		boolean autoCommit = true;
		try (SqlSession session = factory.openSession(autoCommit)) {
			sql.accept(session);
		}		
	}
	
	
}
