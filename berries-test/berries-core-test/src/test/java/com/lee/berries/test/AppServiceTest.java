package com.lee.berries.test;

import org.junit.Test;

import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.service.AppService;

public class AppServiceTest extends BaseTest {

	int count = 0;
	volatile long scond = 0;
	
	@Test
	public void testSave() {
		AppService service = getBean(AppService.class);
		service.update("AppMapper.t");
	}
	
	@Test
	public void testUpdate() {
		AppService service = getBean(AppService.class);
		
		App app = new App();
		app.setId(459044623209877504L);
		app.setAppId(10000000L);
		app.setAppName("new_sadsad_");
		service.update(app);
	}
	
	@Test
	public void testGet() {
		AppService service = getBean(AppService.class);
		
		App app = new App();
		app.setAppId(10000000L);
		App old = service.getByExample(app);
		System.out.println(old.getAppName());
	}
	
	@Test
	public void testDelete() {
		AppService service = getBean(AppService.class);
		
		App app = new App();
		app.setAppId(10000010L);
		app.setId(459294204262764544L);
		service.remove(app, "appId");
		
		String a = "sdsd";
		System.out.println(a.substring(1, a.length() - 1));
	}
	
}
