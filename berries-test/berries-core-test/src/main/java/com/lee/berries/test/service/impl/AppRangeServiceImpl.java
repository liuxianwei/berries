package com.lee.berries.test.service.impl;

import org.springframework.stereotype.Service;

import com.lee.berries.service.impl.BaseServiceImpl;
import com.lee.berries.test.dao.po.AppRange;
import com.lee.berries.test.service.AppRangeService;

@Service
public class AppRangeServiceImpl extends BaseServiceImpl implements AppRangeService {

	@Override
	public void save(AppRange app) {
		super.save(app);
	}

}
