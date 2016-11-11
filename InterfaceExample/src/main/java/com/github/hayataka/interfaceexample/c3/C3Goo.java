package com.github.hayataka.interfaceexample.c3;

public class C3Goo implements C3Hand {

	@Override
	public String judge(C3Hand other) {
		if (other instanceof C3Goo) {
			return "あいこ";
		} else if (other instanceof C3Choki) {
			return "firstの勝ち";			
		} else if (other instanceof C3Par) {
			return "firstの負け";
		} else {
			throw new RuntimeException("想定外です。値：" + other);
		}
	}

	@Override
	public String toString() {
		return "goo";
	}
}
