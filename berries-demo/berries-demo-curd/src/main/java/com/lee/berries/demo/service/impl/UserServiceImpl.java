package com.lee.berries.demo.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.lee.berries.demo.po.User;
import com.lee.berries.demo.service.UserService;
import com.lee.berries.service.impl.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Override
	public void save(User user) {
		user.setCreateTime(new Date());
		super.save(user);

	}

	@Override
	public void update(User user) {
		super.update(user);
	}

	@Override
	public void delete(Long[] id) {
		super.deleteByIds(User.class, id);
	}

	@Override
	public User get(Long id) {
		return get(User.class, id);
	}

}
