package com.lee.berries.test;

import org.junit.Test;

import com.lee.berries.test.dao.po.App;
import com.lee.berries.test.dao.po.AppRange;
import com.lee.berries.test.service.AppRangeService;
import com.lee.berries.test.service.AppService;

public class AppRangeServiceTest extends BaseTest {

	int count = 0;
	volatile long scond = 0;
	
	@Test
	public void testSave() {
		AppRangeService service = getBean(AppRangeService.class);
		for(long i = 0; i < 10000; i++) {
			AppRange app = new AppRange();
			app.setAppId(i);
			app.setId(i);
			app.setAppName("sadsad_" + i);
			service.save(app, "appId");
		}
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
		service.remove(app);
	}
	
}
