package com.github.hayataka.interfaceexample;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomReturn {

	public static int nextInt() {
		final int size = 9;
		return Integer.valueOf(RandomStringUtils.randomNumeric(size));
	}
}
