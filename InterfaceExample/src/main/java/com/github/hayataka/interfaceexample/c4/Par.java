package com.github.hayataka.interfaceexample.c4;

public class Par implements Hand {

	@Override
	public String dispatch(Hand other) {
		return other.judge(this);
	}

	@Override
	public String judge(Goo goo) {
		return "負け";
	}

	@Override
	public String judge(Choki choki) {
		return "勝ち";
	}

	@Override
	public String judge(Par par) {
		return "あいこ";
	}

	@Override
	public String toString() {
		return "Par";
	}

}
