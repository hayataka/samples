package com.github.hayataka.interfaceexample.c3;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.hayataka.interfaceexample.RandomReturn;
import com.github.hayataka.interfaceexample.c3.Creator;
import com.github.hayataka.interfaceexample.c3.C3Hand;

public class C3HandTest {

	@Test
	public void test() {
		Creator creator = new Creator();

		for (int i = 0; i < 20; i++) {
			C3Hand first = creator.create(RandomReturn.nextInt());
			C3Hand second = creator.create(RandomReturn.nextInt());

			String result = first.judge(second);
			System.out.println("first:" + first + ", second:" + second + ", result:" + result);
		}
	}

}
