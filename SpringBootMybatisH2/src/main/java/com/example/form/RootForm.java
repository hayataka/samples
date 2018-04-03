package com.example.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RootForm implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private SearchForm searchForm;
	
	private List<ResultForm> list;
}
