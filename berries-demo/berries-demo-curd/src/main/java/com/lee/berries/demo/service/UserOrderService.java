package com.lee.berries.demo.service;

import com.lee.berries.demo.po.UserOrder;

public interface UserOrderService {

	void save(UserOrder userOrder);
	
	void update(UserOrder userOrder);
	
	void delete(Long []id);
	
	UserOrder get(Long id);
}
