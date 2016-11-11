package com.github.hayataka.interfaceexample.c4;

public class Creator {

	public Hand create(int i) {
		int key = i % 3;
		switch (key) {
		case 0:		
			return new Goo();
		case 1:
			return new Choki();
		case 2:
			return new Par();
		default:
			throw new RuntimeException("想定外です。値："+ i);
		}
		
	}

}
