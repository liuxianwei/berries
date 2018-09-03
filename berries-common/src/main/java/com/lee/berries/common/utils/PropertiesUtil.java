package com.lee.berries.common.utils;

import org.springframework.beans.BeanUtils;

public class PropertiesUtil {

	public static void copyProperties(Object dest, Object source) {
		try {
			BeanUtils.copyProperties(source, dest);
		} catch (Exception e) {
			e.printStackTrace();
			//防御性容错
		}
	}
}
