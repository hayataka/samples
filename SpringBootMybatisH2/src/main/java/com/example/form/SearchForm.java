package com.example.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class SearchForm implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private int id;
	private String name;
	private String birthDay;
}
