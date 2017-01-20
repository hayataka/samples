package com.github.hayataka.logexample.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLog {

	private Logger log;

	MyLog(Class<?> clz) {
		log = LoggerFactory.getLogger(clz);
	}

	MyLog(String name) {
		log = LoggerFactory.getLogger(name);
	}

	public void debug(String msg) {
		log.debug(msg);
	}

	public void debug(String format, Object arg) {
		log.debug(format, arg);
	}

	// ・・・以下こんな感じ

}
