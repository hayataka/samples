package com.github.hayataka.interfaceexample.c3;

public class Creator {

	public C3Hand create(int i) {
		int key = i % 3;
		switch (key) {
		case 0:		
			return new C3Goo();
		case 1:
			return new C3Choki();
		case 2:
			return new C3Par();
		default:
			throw new RuntimeException("想定外です。値："+ i);
		}
		
	}

}
