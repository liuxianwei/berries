package com.lee.berries.test.service.impl;

import org.springframework.stereotype.Service;

import com.lee.berries.service.impl.BaseServiceImpl;
import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.service.AppService;

@Service
public class AppServiceImpl extends BaseServiceImpl implements AppService {

	@Override
	public void save(App app) {
		super.save(app);
	}

}
