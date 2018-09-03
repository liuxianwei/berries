package com.lee.berries.test.service;

import com.lee.berries.service.IBaseService;
import com.lee.berries.test.dao.po.App;

public interface AppService extends IBaseService{

	void save(App app);
}
