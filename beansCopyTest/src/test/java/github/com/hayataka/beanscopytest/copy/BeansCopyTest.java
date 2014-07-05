package github.com.hayataka.beanscopytest.copy;

import java.util.List;

import github.com.hayataka.beanscopytest.Dto.ClassA;
import github.com.hayataka.beanscopytest.Dto.ClassB;
import github.com.hayataka.beanscopytest.logic.CreateLogic;
import github.com.hayataka.beanscopytest.logic.CreateYield;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

public class BeansCopyTest {

	private CreateLogic logic;
	private CreateYield<ClassB> creator;

	@BeforeTest
	public void beforeTest() {
		logic = new CreateLogic();
		creator = new CreateYield<ClassB>() {
			@Override
			public ClassB create() {
				return new ClassB();
			}
		};
	}

	@Test
	public void copy() {

		ClassA[] aArray = logic.createAArray(1);
		ClassB classB = new ClassB();
		ClassA expected = aArray[0];

		BeansCopy.copy(expected, classB);

		Assert.assertNotNull(classB);
		Assert.assertEquals(classB.toString(), expected.toString());

	}

	@DataProvider(parallel = false)
	public Object[][] dpList() {
		return new Object[][] { new Object[] { 10, 10 },
				new Object[] { 100, 100 }, new Object[] { 50000, 3000 },
				new Object[] { 100000, 6000 } };
	}

	@Test(dataProvider="dpList")
	public void copyList(final int maxSize, final int OKmilliseconds) {

		final StopWatch clock = new StopWatch();
		clock.start();
		
		final List<ClassA> src = logic.createAList(maxSize);
		final ClassB[] dest = logic.createBArrayInitialize(maxSize);
		for (ClassB init : dest) {
			Assert.assertNotNull(init);
		}

		BeansCopy.copyList(src, dest);

		clock.stop();

		
		
		Assert.assertTrue(OKmilliseconds > clock.getTime(), "許容時間よりも短いこと"
				+ "許容時間：" + OKmilliseconds + ", 実際の時間：" + clock.getTime());
		
	}

	@Test(dataProvider="dpList")
	public void copyListYieldT(final int maxSize, final int OKmilliseconds) {

		final StopWatch clock = new StopWatch();
		clock.start();
		
		final List<ClassA> src = logic.createAList(maxSize);
		final ClassB[] dest = new ClassB[src.size()];
		
		BeansCopy.copyList(src, dest, creator);

		clock.stop();
		
		Assert.assertTrue(OKmilliseconds > clock.getTime(), "許容時間よりも短いこと"
				+ "許容時間：" + OKmilliseconds + ", 実際の時間：" + clock.getTime());
		
	}
}
