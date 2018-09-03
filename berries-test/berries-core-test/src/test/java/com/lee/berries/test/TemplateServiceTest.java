package com.lee.berries.test;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.lee.berries.dao.Page;
import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.dao.po.Template;
import com.lee.berries.test.dao.po.TemplateQuery;
import com.lee.berries.test.service.TemplateService;

public class TemplateServiceTest extends BaseTest {

	int count = 0;
	volatile long scond = 0;
	
	@Test
	public void testSave() {
		TemplateService service = getBean(TemplateService.class);
		for(int i = 0; i < 10; i++) {
			Template template = new Template();
			template.setCode("sdsds");
			template.setTypeId(Long.valueOf(i));
			service.save(template);
		}
	}
	
	@Test
	public void testSaveWithWhere() {
		TemplateService service = getBean(TemplateService.class);
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
		TemplateService service = getBean(TemplateService.class);
		Template template = new Template();
		template.setCode("sadassdsasdsa");
		template.setId(450608635122962432L);
		service.update(template);
	}
	
	@Test
	public void testUpdateByIds() {
		TemplateService service = getBean(TemplateService.class);
		service.setEnable("450608635122962432");
	}
	
	@Test
	public void testRow() {
		TemplateService service = getBean(TemplateService.class);
		Template template = new Template();
		template.setAppId(1234567L);
		service.listByExampleRow(template);
	}
	
	@Test
	public void testQuery() {
		TemplateService service = getBean(TemplateService.class);
		TemplateQuery query = new TemplateQuery();
		query.setMin_template_create_time(new Date());
		query.setApp_app_id(1L);
		query.setApp_app_name("sdsdsd");
		query.setMax_template_create_time(new Date());
		query.setEnableLike(true);
		query.setTemplate_type_id(1L);
		service.query(query, new Page<>());
	}
	
	@Test
	public void testDeleteByIds() {
		TemplateService service = getBean(TemplateService.class);
		service.delete("450608635122962432,449612462425788416,449612244036767744");
	}
	
	@Test
	public void testGet() throws IOException {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		ThreadLocal<TemplateService> local = new ThreadLocal<>();
		TemplateService service = getBean(TemplateService.class);
		
		Long time = System.currentTimeMillis();
		for(int i =0; i < 20000; i++) {
			
			
			Runnable task = null;
			if(i %2 == 0) {
				task = new Runnable() {
					
					@Override
					public void run() {
						/*try {
						App app = service.getAPP(435442618936066048L);
			
						System.out.println("get app:" + app.getAppName());
						
						}
						catch (Exception e) {
							//e.printStackTrace();
							System.out.println(Thread.currentThread().getName());
							System.exit(0);
						}*/
						//TemplateService service = getBean(TemplateService.class);
						App app = service.getAPP(435442678906224640L);
						if(app == null) {
							count++;
						}
						scond++;
						//service.setEnable("428574739640877056,428583658618617856");
						//System.out.println("get app:" + app.getAppName());
						if(scond % 1000 == 0) {
							System.out.println(count + ":" +scond);
							System.out.println(System.currentTimeMillis() - time);
						}
						
					}
				};
			}
			else {
				task = new Runnable() {
					
					@Override
					public void run() {
						//TemplateService service = getBean(TemplateService.class);
						Template template = service.geTemplate(435442890760519680L);
						if(template == null) {
							count++;
						}
						scond++;
						//System.out.println("get template:" + template.getName());
						if(scond % 1000 == 0) {
							System.out.println(count + ":" +scond);
							System.out.println(System.currentTimeMillis() - time);
						}
					}
				};
			}
			executorService.execute(task);
		}
		
		System.in.read();
	}
	
	@Test
	public void testStrPlus() {
		String aString = "";
		StringBuffer stringBuffer = new StringBuffer(100002);
		Long now = System.currentTimeMillis();
		for(int i = 0; i <100000; i++) {
			stringBuffer.append(i);
		}
		System.out.println(System.currentTimeMillis() - now);
		now = System.currentTimeMillis();
		for(int i = 0; i <100000; i++) {
			aString += String.valueOf(i);
		}
		System.out.println(System.currentTimeMillis() - now);
	}
}
