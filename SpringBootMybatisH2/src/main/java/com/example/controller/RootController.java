package com.example.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RootForm;
import com.example.form.SearchForm;
import com.example.mapper.TbTestdataMapper;
import com.example.model.TbTestdata;
import com.example.model.TbTestdataExample;
import com.example.filter.RequestDatas;


@Controller
@RequestMapping("/")
public class RootController {

	@Resource
	private RequestDatas a;

	@Autowired
	private TbTestdataMapper mapper;
	
	@Transactional
	@RequestMapping("/")
	public String test(RootForm kokoForm) {
		System.out.println("RootController:" + a.getAa());
		
//		TbTestdataExample example = new TbTestdataExample();
//		example.createCriteria().andIdEqualTo(1).andNameEqualTo("太郎");
//		mapper.selectByExample(example);
//		
//		TbTestdata entity = mapper.selectByPrimaryKey(1);
		
		//FIXME 気にくわない。
		kokoForm.setSearchForm(new SearchForm()); 
		kokoForm.getSearchForm().setId(30);

		return "index";
	}

	@Transactional
	@RequestMapping("/search")
	public String search(RootForm form) {
		return null;
	}
}
