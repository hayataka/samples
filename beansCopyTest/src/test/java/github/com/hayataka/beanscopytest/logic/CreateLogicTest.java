package github.com.hayataka.beanscopytest.logic;

import java.util.List;

import github.com.hayataka.beanscopytest.Dto.ClassA;
import github.com.hayataka.beanscopytest.Dto.ClassB;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateLogicTest {

	private CreateLogic logic;

	@BeforeTest
	public void setup() {
		logic = new CreateLogic();
	}

	@DataProvider(parallel = true)
	public Object[][] dp() {
		return new Object[][] { new Object[] { 10, 100 },
				new Object[] { 100000, 5000 }, new Object[] { 50000, 2000 } };
	}

	@Test(dataProvider = "dp")
	public void createAArray(final int maxSize, final int OKmilliseconds) {

		final StopWatch clock = new StopWatch();
		clock.start();

		// execute
		ClassA[] actual = logic.createAArray(maxSize);

		clock.stop();

		Assert.assertNotNull(actual);
		Assert.assertFalse(actual[0] == actual[1], "参照が同一でないこと");
		Assert.assertTrue(OKmilliseconds > clock.getTime(), "許容時間よりも短いこと"
				+ "許容時間：" + OKmilliseconds + ", 実際の時間：" + clock.getTime());

	}

	@Test(dataProvider = "dp")
	public void createAList(final int maxSize, final int OKmilliseconds) {
		final StopWatch clock = new StopWatch();
		clock.start();

		// execute
		List<ClassA> actual = logic.createAList(maxSize);

		clock.stop();

		Assert.assertNotNull(actual);
		Assert.assertFalse(actual.get(0) == actual.get(1), "参照が同一でないこと");
		Assert.assertTrue(OKmilliseconds > clock.getTime(), "許容時間："
				+ OKmilliseconds + ", 実際の時間：" + clock.getTime());
	}

	//
	// @Test
	// public void createBArray() {
	// }
	//

	@DataProvider(parallel = false)
	public Object[][] dpInitialize() {
		return new Object[][] { new Object[] { 10, 10 },
				new Object[] { 100, 100 }, new Object[] { 50000, 1000 }, new Object[] { 100000, 2000 } };
	}

	@Test(dataProvider = "dpInitialize")
	public void createBArrayInitialize(final int maxSize,
			final int OKmilliseconds) {
		final StopWatch clock = new StopWatch();
		clock.start();

		ClassB[] actual = logic.createBArrayInitialize(maxSize);
		
		clock.stop();
		Assert.assertNotNull(actual);
		Assert.assertFalse(actual[0] == actual[1], "参照が同一でないこと");
		Assert.assertTrue(OKmilliseconds > clock.getTime(), "許容時間よりも短いこと"
				+ "許容時間：" + OKmilliseconds + ", 実際の時間：" + clock.getTime());
	}
	//
	// @Test
	// public void createBList() {
	// }
}
