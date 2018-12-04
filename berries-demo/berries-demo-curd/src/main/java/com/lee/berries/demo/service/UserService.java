package com.lee.berries.demo.service;

import com.lee.berries.demo.po.User;

public interface UserService {

	void save(User user);
	
	void update(User user);
	
	void delete(Long []id);
	
	User get(Long id);
}
