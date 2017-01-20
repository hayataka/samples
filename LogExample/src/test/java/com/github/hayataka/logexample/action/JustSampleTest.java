package com.github.hayataka.logexample.action;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.github.hayataka.logexample.dto.SampleDto;

import static org.hamcrest.CoreMatchers.*;

public class JustSampleTest {

	@Test
	public void testAdd() {

		JustSample target = new JustSample();
		int actual = target.add(10, 20);
		assertThat(actual, is(30));
		
	}

	@Test
	public void testTest() {
		
		JustSample target = new JustSample();
		SampleDto dto = new SampleDto();
		dto.setId(20);
		dto.setBirthday(LocalDate.now());
		dto.setName("太郎");
		target.test(dto);
		
		
	}
}
