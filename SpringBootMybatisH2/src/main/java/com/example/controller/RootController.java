package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RootForm;
import com.example.mapper.TbTestdataMapper;
import com.example.model.TbTestdata;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	private TbTestdataMapper mapper;
	
	@Transactional
	@RequestMapping("/")
	public String test(RootForm rootForm) {
		TbTestdata entity = mapper.selectByPrimaryKey(1);
		rootForm.setId(30);
		rootForm.setName(entity.getName());
		return "index";
	}

}
