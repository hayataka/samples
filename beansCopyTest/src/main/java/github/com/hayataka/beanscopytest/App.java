package github.com.hayataka.beanscopytest;

import java.util.List;

import github.com.hayataka.beanscopytest.Dto.ClassA;
import github.com.hayataka.beanscopytest.Dto.ClassB;
import github.com.hayataka.beanscopytest.copy.BeansCopy;
import github.com.hayataka.beanscopytest.logic.CreateLogic;
import github.com.hayataka.beanscopytest.logic.CreateYield;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 * 
 */
public class App {

	private static final Logger log = LogManager.getLogger(App.class);

	public static void main(String[] args) {

		CreateLogic logic = new CreateLogic();
		int maxSize = 100000;

		logic.createAArray(maxSize);
		logic.createBArray(maxSize);
		logic.createAList(maxSize);
		logic.createBList(maxSize);
		logic.createBArrayInitialize(maxSize);

		log.info("bean系計測");

		final ClassB[] bArray = logic.createBArrayInitialize(maxSize);
		BeansCopy.copyList(logic.createAList(maxSize), bArray);

		if (log.isDebugEnabled()) {
			final int JUST_FOR_DEBUG_SIZE = 100;
			for (int i = 0; i < JUST_FOR_DEBUG_SIZE; i++) {
				if (i > (maxSize - 1)) {
					break;
				}
				log.debug(bArray[i]);
			}
		}

		final CreateYield<ClassB> creator = new CreateYield<ClassB>() {
			@Override
			public ClassB create() {
				return new ClassB();
			}
		};
		final ClassB[] notInitializedBArray = new ClassB[maxSize];
		List<ClassA> srcList = logic.createAList(maxSize);
		BeansCopy.copyList(srcList, notInitializedBArray, creator);

	}
}
