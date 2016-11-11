package com.github.hayataka.interfaceexample.c2;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.hayataka.interfaceexample.RandomReturn;

public class C2PatternTest {

	@Test
	public void test() {

		for (int i = 0; i < 10; i++) {
			C2Pattern first = C2Pattern.create(RandomReturn.nextInt());
			C2Pattern second = C2Pattern.create(RandomReturn.nextInt());		
			
			String result = first.judge(second);
			System.out.println("first:" + first + ", second:" + second + ", result:" + result);
			
		}
		
	}

}
