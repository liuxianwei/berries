package com.lee.berries.test.service;

import com.lee.berries.service.IBaseService;
import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.dao.po.TemplateDate;

public interface TemplateDateService extends IBaseService{

	int setEnable(String ids);
	
	void save(TemplateDate template);
	
	void update(TemplateDate template);
	
	int delete(String ids);
	
	App getAPP(Long id);
	
	TemplateDate geTemplateDate(Long id);
}
