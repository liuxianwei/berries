package com.lee.berries.web.convert;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

public class CustomDateConvertEditor extends PropertyEditorSupport {

	private final DateFormat dateFormat;
	
	private final DateFormat timeFormat;
	
	public CustomDateConvertEditor() {
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
	}
	
	public CustomDateConvertEditor(DateFormat dateFormat, DateFormat timeFormat) {
		super();
		this.dateFormat = dateFormat;
		this.timeFormat = timeFormat;
	}

	@Override
	public void setAsText(String text) {
		if(StringUtils.isEmpty(text)) {
			setValue(null);
		}
		else {
			Object date = null;
			try {
				date = timeFormat.parse(text);
			}
			catch (Exception e) {}
			if(date == null) {
				try {
					date = dateFormat.parse(text);
				} catch (ParseException e) {}
			}
			if(date != null) {
				setValue(date);
			}
			else {
				throw new IllegalArgumentException("Could not parse date: " + text);
			}
			
		}
	}
	
	@Override
	public String getAsText() {
		Object value = getValue();
		if(value == null) {
			return null;
		}
		return timeFormat.format(getValue()).replaceAll(" 00:00:00", "");
	}
}
