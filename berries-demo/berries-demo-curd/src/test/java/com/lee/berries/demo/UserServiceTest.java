package com.lee.berries.demo;

import org.junit.Test;

import com.lee.berries.demo.po.User;
import com.lee.berries.demo.service.UserService;

public class UserServiceTest extends BaseTest {

	@Test
	public void testSave() {
		UserService userService = getBean(UserService.class);
		User user = new User();
		user.setName("Hello Berries!");
		userService.save(user);
	}
	
	@Test
	public void testUpdate() {
		UserService userService = getBean(UserService.class);
		User user = new User();
		user.setId(519533689367236608L);
		user.setName("Hello Berries! I updated.");
		userService.update(user);
	}
	
	
	@Test
	public void testGet() {
		UserService userService = getBean(UserService.class);
		User user = userService.get(519533689367236608L);
		System.out.println(user.getName());
	}
	
	@Test
	public void testDelete() {
		UserService userService = getBean(UserService.class);
		Long []id = {1L,1L};
		userService.delete(id);
	}
	
	
}
