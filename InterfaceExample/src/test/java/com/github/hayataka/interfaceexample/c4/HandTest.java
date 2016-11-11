package com.github.hayataka.interfaceexample.c4;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.hayataka.interfaceexample.RandomReturn;

public class HandTest {

	@Test
	public void test() {

		Creator creator = new Creator();

		for (int i = 0; i < 20; i++) {
			Hand first = creator.create(RandomReturn.nextInt());
			Hand second = creator.create(RandomReturn.nextInt());

			String result = first.dispatch(second);
			System.out.println("first:" + first + ", second:" + second + ", result:firstの" + result);
		}
	}

}
