package com.example.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class RootForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
}
