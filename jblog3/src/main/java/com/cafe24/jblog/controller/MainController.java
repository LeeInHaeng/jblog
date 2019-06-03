package com.cafe24.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping(value= {"/", "/main", "/index"})
	public String main() {
		return "main/index";
	}
}
