package com.berries.dao;

import java.lang.annotation.Annotation;

public class Children extends Parent {

	public static void main(String args[]) {
		for(Annotation a:Children.class.getAnnotations()) {
			System.out.println(a.annotationType().getName());
		}
	}
}
