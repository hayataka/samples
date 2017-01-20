package com.github.hayataka.logexample.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonDto {


	private int id;
	private String name;
	private LocalDate birthday;
	@JsonIgnore
	private String password;
}
