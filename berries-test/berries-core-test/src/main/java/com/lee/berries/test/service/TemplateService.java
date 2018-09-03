package com.lee.berries.test.service;

import com.lee.berries.dao.Page;
import com.lee.berries.service.IBaseService;
import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.dao.po.Template;
import com.lee.berries.test.dao.po.TemplateQuery;

public interface TemplateService extends IBaseService{

	int setEnable(String ids);
	
	void save(Template template);
	
	void update(Template template);
	
	int delete(String ids);
	
	App getAPP(Long id);
	
	Template geTemplate(Long id);
	
	Page<Template> query(TemplateQuery query, Page<Template> page);
}
