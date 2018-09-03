package com.lee.berries.web.convert;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class DateConverter implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		CustomDateConvertEditor dateEditor = new CustomDateConvertEditor();  
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	public void initBinder(WebDataBinder binder) {
		CustomDateConvertEditor dateEditor = new CustomDateConvertEditor();  
		binder.registerCustomEditor(Date.class, dateEditor);
	}

}
