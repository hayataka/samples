package github.com.hayataka.beanscopytest;

import github.com.hayataka.beanscopytest.Dto.ClassA;
import github.com.hayataka.beanscopytest.Dto.ClassB;
import github.com.hayataka.beanscopytest.copy.BeansCopy;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final int MAX_LENGTH = 1000000;
	
	private static final Logger log = LogManager.getLogger(App.class);
	
	public static void main(String[] args) {

		log.info("てすと {0}/{1}", "a","2");

	    // NG全て同一インスタンス
//	    Arrays.fill(bb, new ClassB());
		ClassA a = new ClassA();
		a.setName("nameerdaa");
		a.setUserId("userId");
		ClassB ret = BeansCopy.copy(a, new ClassB());
		System.out.println(ret);

		listAddTime();

		arraysAddTime();

		arraysInitializeTime();
		
		
		
		
//		final Creater<ClassB> creater = new Creater<ClassB>() {
//			@Override
//			public ClassB create() {
//				return new ClassB();
//			}
//		};
		
		List<ClassA> lista = new ArrayList<>(MAX_LENGTH);
		for (int i = 0; i < MAX_LENGTH; i++) {
			ClassA classA = new ClassA();
			classA.setName(i+ "name");
			classA.setUserId(i+"userId");
			lista.add(classA);
		}

		ClassB[] bb = new ClassB[MAX_LENGTH];
		for (int j = 0; j < MAX_LENGTH; j++) {
			bb[j] = new ClassB();
		}

		BeansCopy.copyList(lista, bb);
		
		System.out.println("aaaaaaa");
	}

	private static void arraysInitializeTime() {
		long before = System.currentTimeMillis();
		ClassB[] bb = new ClassB[MAX_LENGTH];
		for (int j = 0; j < MAX_LENGTH; j++) {
			bb[j] = new ClassB();
		}
		long after = System.currentTimeMillis();
		
		System.out.println(after - before);
	
		System.out.println("stop arraysInitalizeTime");

		
	}

	private static void arraysAddTime() {
		long b2 = System.currentTimeMillis();
		ClassA[] arraysA = new ClassA[MAX_LENGTH];
		for (int i = 0; i < MAX_LENGTH; i++) {
			ClassA classA = new ClassA();
			classA.setName(i+ "name");
			classA.setUserId(i+"userId");
			arraysA[i] = classA;
		}
		long a2 = System.currentTimeMillis();
		System.out.println(a2-b2);
		System.out.println("stop arraysAddTime");
	}

	private static void listAddTime() {
		long b1 = System.currentTimeMillis();
		List<ClassA> lista = new ArrayList<>(MAX_LENGTH);
		for (int i = 0; i < MAX_LENGTH; i++) {
			ClassA classA = new ClassA();
			classA.setName(i+ "name");
			classA.setUserId(i+"userId");
			lista.add(classA);
		}
		long a1 = System.currentTimeMillis();
		System.out.println(a1-b1);
		System.out.println("stop listAddTime");

		
	}

}
