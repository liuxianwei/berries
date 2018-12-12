package com.lee.berries.demo.service;

import com.lee.berries.demo.po.User;
import com.lee.berries.service.IBaseService;

public interface UserService extends IBaseService{

	void save(User user);
	
	void update(User user);
	
	void delete(Long []id);
	
	User get(Long id);
}
