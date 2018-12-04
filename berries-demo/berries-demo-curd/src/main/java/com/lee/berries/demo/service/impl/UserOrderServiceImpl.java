package com.lee.berries.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.lee.berries.demo.po.UserOrder;
import com.lee.berries.demo.service.UserOrderService;
import com.lee.berries.service.impl.BaseServiceImpl;

@Service
public class UserOrderServiceImpl extends BaseServiceImpl implements UserOrderService {

	@Override
	public void save(UserOrder userOrder) {
		userOrder.setCreateTime(new Date());
		super.save(userOrder);

	}

	@Override
	public void update(UserOrder userOrder) {
		super.update(userOrder);
	}

	@Override
	public void delete(Long[] id) {
		super.deleteByIds(UserOrder.class, id);
	}

	@Override
	public UserOrder get(Long id) {
		return get(UserOrder.class, id);
	}

}
