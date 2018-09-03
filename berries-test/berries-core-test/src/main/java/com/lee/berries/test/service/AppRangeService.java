package com.lee.berries.test.service;

import com.lee.berries.service.IBaseService;
import com.lee.berries.test.dao.po.AppRange;

public interface AppRangeService extends IBaseService{

	void save(AppRange appRange);
}
