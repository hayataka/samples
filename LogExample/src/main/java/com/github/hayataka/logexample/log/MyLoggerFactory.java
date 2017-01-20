package com.github.hayataka.logexample.log;

public class MyLoggerFactory {

	public static MyLog getLogger(Class<?> clz) {

		return new MyLog(clz);
	}
}
