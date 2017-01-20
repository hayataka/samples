package com.github.hayataka.logexample.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;

public class ToJsonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();

	public String toJson(Object... args) {

		if (args == null) {
			return "null";
		}

		return Arrays.stream(args).map(arg ->{
			try {
				final val val = mapper.writeValueAsString(arg);
				return val;
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}).collect(Collectors.joining(","));
	}
}
