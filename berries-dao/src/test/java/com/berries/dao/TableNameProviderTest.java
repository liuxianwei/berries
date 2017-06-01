package com.berries.dao;

import org.junit.Test;

import com.berries.test.BaseTest;

public class TableNameProviderTest extends BaseTest {

	@Test
	public void testName(){
		String name = "User";
		char[] array = name.toCharArray();
		for(int i = 0; i < array.length; i++){
			char c = array[i];
			char newChar = c;
			if(c >= 'A' && c <= 'Z'){
				newChar = (char)(c+32);
				if(i == 0){
					name = name.replaceFirst(c + "", newChar + "");
				}
				else{
					name = name.replaceFirst(c + "", "_" + newChar);
				}
			}
			
		}
		System.out.println(name);
	}
}
