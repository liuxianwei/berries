package com.lee.berries.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping
	public void init(Model model){
		model.addAttribute("see", "hello springmvc");
	}
}
