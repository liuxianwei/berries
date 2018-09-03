package com.lee.berries.test.service.impl;

import org.springframework.stereotype.Service;

import com.lee.berries.common.utils.BerriesUtils;
import com.lee.berries.service.impl.BaseServiceImpl;
import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.dao.po.TemplateDate;
import com.lee.berries.test.service.TemplateDateService;

@Service
public class TemlateDateServiceImpl extends BaseServiceImpl implements TemplateDateService {

	@Override
	public int setEnable(String ids) {
		return updateByIds(TemplateDate.class, "enable_flag", true, BerriesUtils.splitToLong(ids));
	}

	@Override
	public int delete(String ids) {
		return deleteByIds(TemplateDate.class, BerriesUtils.splitToLong(ids));
	}

	@Override
	public App getAPP(Long id) {
		App app = new App();
		app.setId(id);
		return getByExample(app);
	}

	@Override
	public TemplateDate geTemplateDate(Long id) {
		TemplateDate template = new TemplateDate();
		template.setId(id);
		template.setTypeId(435440878828388352L);
		return getByExample(template);
	}

	@Override
	public void save(TemplateDate template) {
		super.save(template);
	}

	@Override
	public void update(TemplateDate template) {
		super.update(template);
	}

}
