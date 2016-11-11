package com.github.hayataka.interfaceexample.c4;

public interface Hand {

	public String dispatch(Hand other);	
	public String judge(Goo goo);
	public String judge(Choki choki);
	public String judge(Par par);	
}
