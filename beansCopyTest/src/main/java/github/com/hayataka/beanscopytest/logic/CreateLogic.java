package github.com.hayataka.beanscopytest.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import github.com.hayataka.beanscopytest.Dto.ClassA;
import github.com.hayataka.beanscopytest.Dto.ClassB;

public class CreateLogic {

	private static final Logger log = LogManager.getLogger(CreateLogic.class);

	public List<ClassA> createAList(final int maxSize) {
		final StopWatch clock = new StopWatch();
		clock.start();

		final List<ClassA> list = new ArrayList<>(maxSize);

		for (int i = 0; i < maxSize; i++) {
			final ClassA data = new ClassA();

			data.setAddress("東京都杉並区AAAAAAAAAAAAAAAA" + i);
			data.setBirthday(new Date());
			data.setMail("AABBBBBBB@CC.DD.EE" + i);
			data.setName("ZZZZZZZhGGGa" + i);
			data.setSize(i);
			data.setWeight(i * 2);
			list.add(data);
		}
		clock.stop();
		log.info("{}個のClassAList作成に要した時間は{}ミリ秒", maxSize, clock.getTime());
		return list;
	}

	public ClassA[] createAArray(final int maxSize) {
		final StopWatch clock = new StopWatch();
		clock.start();

		final ClassA[] array = new ClassA[maxSize];

		for (int i = 0; i < maxSize; i++) {
			final ClassA data = new ClassA();

			data.setAddress("東京都杉並区AAAAAAAAAAAAAAAA" + i);
			data.setBirthday(new Date());
			data.setMail("AABBBBBBB@CC.DD.EE" + i);
			data.setName("ZZZZZZZhGGGa" + i);
			data.setSize(i);
			data.setWeight(i * 2);
			array[i] = data;
		}

		clock.stop();
		log.info("{}個のClassA配列作成に要した時間は{}ミリ秒", maxSize, clock.getTime());
		return array;
	}
	
	public List<ClassB> createBList(final int maxSize) {
		final StopWatch clock = new StopWatch();
		clock.start();

		final List<ClassB> list = new ArrayList<>(maxSize);

		for (int i = 0; i < maxSize; i++) {
			final ClassB data = new ClassB();

			data.setAddress("東京都杉並区AAAAAAAAAAAAAAAA" + i);
			data.setBirthday(new Date());
			data.setMail("AABBBBBBB@CC.DD.EE" + i);
			data.setName("ZZZZZZZhGGGa" + i);
			data.setSize(i);
			data.setWeight(i * 2);
			list.add(data);
		}
		clock.stop();
		log.info("{}個のClassBList作成に要した時間は{}ミリ秒", maxSize, clock.getTime());
		return list;
	}
	
	public ClassB[] createBArray(final int maxSize) {
		final StopWatch clock = new StopWatch();
		clock.start();

		final ClassB[] array = new ClassB[maxSize];

		for (int i = 0; i < maxSize; i++) {
			final ClassB classB = new ClassB();

			classB.setAddress("東京都杉並区AAAAAAAAAAAAAAAA" + i);
			classB.setBirthday(new Date());
			classB.setMail("AABBBBBBB@CC.DD.EE" + i);
			classB.setName("ZZZZZZZhGGGa" + i);
			classB.setSize(i);
			classB.setWeight(i * 2);
			array[i] = classB;
		}

		clock.stop();
		log.info("{}個のClassB配列作成に要した時間は{}ミリ秒", maxSize, clock.getTime());
		return array;
	}

	public ClassB[] createBArrayInitialize(final int maxSize) {
		final StopWatch clock = new StopWatch();
		clock.start();

		// NG全て同一インスタンスとなるため
		// Arrays.fill(bb, new ClassB());

		final ClassB[] array = new ClassB[maxSize];

		for (int i = 0; i < maxSize; i++) {
			array[i] = new ClassB();
		}

		clock.stop();
		log.info("{}個のClassB配列初期化に要した時間は{}ミリ秒", maxSize, clock.getTime());
		return array;
	}

}
