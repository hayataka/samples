package jp.co.test.concurrent.executors;

import static org.junit.Assert.*;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

import java.util.concurrent.ExecutorService;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

public class ProducerTest {

	@Mock
	private SqlSessionFactory factory;
	@Mock
	private ExecutorService service;
	@Mock
	private BatchSqlExecutor executor;
	
	@InjectMocks
	private Producer producer = new Producer();
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
//	@Test
//	public void testInit() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testStart() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testLoop() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testSleep() {
		producer.sleep(2);
	}
//
//	@Test
//	public void testStop() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testDestroy() {
		producer.destroy();
		Object fac = getInternalState(producer, "factory");
		assertNull(fac);
		
		Object service = getInternalState(producer, "service");
		assertNull(service);
		
		Object executor = getInternalState(producer, "executor");
		assertNull(executor);

	}

}
