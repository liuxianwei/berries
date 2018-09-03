package com.lee.berries.test;

import java.util.Date;

import org.junit.Test;

import com.lee.berries.test.dao.po.Template;
import com.lee.berries.test.dao.po.TemplateDate;
import com.lee.berries.test.service.TemplateDateService;

public class TemplateDateServiceTest extends BaseTest {

	int count = 0;
	volatile long scond = 0;
	
	@Test
	public void testSave() {
		TemplateDateService service = getBean(TemplateDateService.class);
		for(int i = 0; i < 10; i++) {
			TemplateDate template = new TemplateDate();
			template.setCode("sdsds");
			template.setCreateTime(new Date());
			template.setTypeId(Long.valueOf(i));
			service.save(template);
		}
	}
	
	@Test
	public void testSaveWithWhere() {
		TemplateDateService service = getBean(TemplateDateService.class);
		Template template = new Template();
		template.setCode("11111");
		template.setDeleteFlag(true);
		template.setCreateTime(new Date());
		service.save(template);
		service.save(template);
		service.save(template);
		service.save(template);
	}
	
	@Test
	public void testUpdate() {
		TemplateDateService service = getBean(TemplateDateService.class);
		Template template = new Template();
		template.setCode("sadassdsasdsa");
		template.setId(450608635122962432L);
		service.update(template);
	}
	
	
}
