package com.lee.berries.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lee.berries.web.config.BerriesPlaceholderConfigurer;

@Controller
public class MainController {

	@Value("${name}")
	private String name;
	
	@RequestMapping
	public void init(Model model){
		model.addAttribute("see", "hello springmvc! my name is " + name);
	}
	
	@RequestMapping
	public void set(String key, String value, Model model){
		BerriesPlaceholderConfigurer.getInstance().reloadProperties(key, value);
	}
}
