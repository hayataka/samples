package com.github.hayataka.logexample.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LombokDto {
//TODO 実務上の問題点を指摘すること
	private int id;
	private String name;
	private LocalDate brithday;
	private String password;
}
