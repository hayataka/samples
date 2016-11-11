package com.github.hayataka.interfaceexample.c2;

enum C2Pattern {
	Goo, Choki, Par;
	String judge(C2Pattern hand) {

		switch (this) {
		case Goo:
			switch (hand) {
			case Goo:
				return "あいこ";
			case Choki:
				return "firstの勝ち";
			default:
				return "firstの負け";
			}
			
		case Choki:
			switch (hand) {
			case Goo:
				return "firstの負け";
			case Choki:
				return "あいこ";
			default:
				return "firstの勝ち";
			}

		case Par:
			switch (hand) {
			case Goo:
				return "firstの勝ち";
			case Choki:
				return "firstの負け";
			default:
				return "あいこ";
			}

		}
		throw new RuntimeException("想定外です。this:" + this + ", second:" + hand);
		
	}
	
	static C2Pattern create(int i) {

		int key = i % 3;

		switch (key) {
		case 0:
			return Goo;
		case 1:
			return Choki;
		case 2:
		default:
			return Par;
		}

	}

}
