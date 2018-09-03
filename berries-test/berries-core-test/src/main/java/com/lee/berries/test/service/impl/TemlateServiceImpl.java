package com.lee.berries.test.service.impl;

import org.springframework.stereotype.Service;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.dao.Page;
import com.lee.berries.service.impl.BaseServiceImpl;
import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.dao.po.Template;
import com.lee.berries.test.dao.po.TemplateQuery;
import com.lee.berries.test.service.TemplateService;

@Service
public class TemlateServiceImpl extends BaseServiceImpl implements TemplateService {

	@Override
	public int setEnable(String ids) {
		return updateByIds(Template.class, "enable_flag", true, BerriesUtils.splitToLong(ids));
	}

	@Override
	public int delete(String ids) {
		return deleteByIds(Template.class, BerriesUtils.splitToLong(ids));
	}

	@Override
	public App getAPP(Long id) {
		App app = new App();
		app.setId(id);
		return getByExample(app);
	}

	@Override
	public Template geTemplate(Long id) {
		Template template = new Template();
		template.setId(id);
		template.setTypeId(435440878828388352L);
		return getByExample(template);
	}
	
	  @Override
	    public Page<Template> query(TemplateQuery query, Page<Template> page) {
	        query.setEnableLike(true);
	        return super.query(query, page);
	    }

	@Override
	public void save(Template template) {
		super.save(template);
	}

	@Override
	public void update(Template template) {
		super.update(template);
	}

}
