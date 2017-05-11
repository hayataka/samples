package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RootForm;

@Controller
@RequestMapping("/")
public class RootController {

	@RequestMapping("/")
	public String test(RootForm rootForm) {
		rootForm.setId(30);
		return "index";
	}

}
