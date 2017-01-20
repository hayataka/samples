package com.github.hayataka.logexample.action;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hayataka.logexample.dto.JsonDto;
import com.github.hayataka.logexample.dto.LombokDto;
import com.github.hayataka.logexample.dto.SampleDto;
import com.github.hayataka.logexample.util.ToJsonUtil;

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

	private Logger log = LoggerFactory.getLogger(JustSampleTest.class);

	@Test
	public void lombokTest() {

		LombokDto dto = new LombokDto();
		dto.setId(15);
		dto.setName("二郎");
		dto.setBrithday(LocalDate.now());
		dto.setPassword("passwordです");
		log.debug("データは：{}", dto);
	}



	@Test
	public void jsonTest() {

		JsonDto dto = new JsonDto();
		dto.setId(243);
		dto.setName("三郎");
		dto.setBirthday(LocalDate.now());
		dto.setPassword("passswwwwwwword");
		ToJsonUtil util = new ToJsonUtil();
		String ret = util.toJson(dto);
		log.debug("jsonデータは{}", ret);

	}



}
