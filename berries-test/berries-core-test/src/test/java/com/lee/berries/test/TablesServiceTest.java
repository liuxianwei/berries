package com.lee.berries.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.lee.berries.router.model.BaseSharing;
import com.lee.berries.test.service.TemplateService;

public class TablesServiceTest extends BaseTest {

	int count = 0;
	volatile long scond = 0;
	
	
	@Test
	public void testGetTable() {
		TemplateService service = getBean(TemplateService.class);
		List<String> list = service.selectList("TableMapper.getTables", null);
		System.out.println(list.toString());
	}
	
	@Test
	public void testGetTableDDL() {
		TemplateService service = getBean(TemplateService.class);
		Map<String, String> obj = service.get("TableMapper.getTableDDL", "ad_access");
		String ddl = obj.get("Create Table");
		//ddl = ddl.replace("ad_access", "ad_access_2");
		System.out.println(ddl);
		int count = service.update("TableMapper.createTable", ddl);
		System.out.println(count);
	}
	
	@Test
	public void testJSON() {
		
		String json = "{\"rule\":\"MOD\", \"tableName\":\"accss\"}";
		BaseSharing baseSharing = JSON.parseObject(json, BaseSharing.class);
		System.out.println(baseSharing.getTableName());
	}
	
}
