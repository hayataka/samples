package com.github.hayataka.interfaceexample.c1;

import org.junit.Test;

import com.github.hayataka.interfaceexample.RandomReturn;

public class GoTest {

	@Test
	public void test() {
		C1Pattern c1 = new C1Pattern();
		
		for (int i = 0; i<10; i++) {
			int one = RandomReturn.nextInt();
			int other = RandomReturn.nextInt();

			System.out.print("first:" + one + "  second:" + other + "  ");
			c1.calc(one, other);
			
		}
		

	}

	
}
