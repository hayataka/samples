package com.github.hayataka.interfaceexample.c1;

/**
 * 非常に露骨にそのまま作成する
 *
 */
class C1Pattern {

	void calc(int one, int other) {

		// 手札の判定
		String first = calc(one);
		String second = calc(other);
		
		System.out.print("firstは" + first + ", secondは" + second + "  ");
		
		if (first.equals("goo")) {
			if (second.equals("goo")) {
				System.out.println("あいこです。");
			} else if (second.equals("choki")) {
				System.out.println("firstの勝ちです。");
			} else if (second.equals("par")) {
				System.out.println("firstの負けです。");
			} else {
				throw new RuntimeException("secondの手札がありえない手札です。：" + second);
			}
		} else if (first.equals("choki")) {
			if (second.equals("goo")) {
				System.out.println("firstの負けです。");
			} else if (second.equals("choki")) {
				System.out.println("あいこです。");
			} else if (second.equals("par")) {
				System.out.println("firstの勝ちです。");
			} else {
				throw new RuntimeException("secondの手札がありえない手札です。：" + second);
			}
		} else if (first.equals("par")) {
			if (second.equals("goo")) {
				System.out.println("firstの勝ちです。");
			} else if (second.equals("choki")) {
				System.out.println("firstの負けです。");
			} else if (second.equals("par")) {
				System.out.println("あいこです。");
			} else {
				throw new RuntimeException("secondの手札がありえない手札です。：" + second);
			}
		} else {
			throw new RuntimeException("ありえない手札です:" + one);
		}
	}

	/**
	 * 送られた手札を元に、グー、チョキ、パーに判定する
	 * @param hand 手札
	 * @return じゃんけんの手
	 */
	String calc(int hand) {
		int t = hand % 3;
		switch (t) {
		case 0:
			return "goo";
		case 1:
			return "choki";
		case 2:
			return "par";
		default:
			throw new RuntimeException("たどり着かない想定です");
		}
	}

}
