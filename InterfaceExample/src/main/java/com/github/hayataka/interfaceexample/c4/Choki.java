package com.github.hayataka.interfaceexample.c4;

public class Choki implements Hand {

	@Override
	public String dispatch(Hand other) {
		return other.judge(this);
	}

	@Override
	public String judge(Goo goo) {
		return "勝ち";
	}

	@Override
	public String judge(Choki choki) {
		return "あいこ";
	}

	@Override
	public String judge(Par par) {
		return "負け";
	}

	@Override
	public String toString() {
		return "Choki";
	}

	
}
