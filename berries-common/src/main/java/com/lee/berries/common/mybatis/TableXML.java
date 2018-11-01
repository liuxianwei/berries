package com.lee.berries.common.mybatis;

import com.lee.berries.common.utils.BerriesUtils;

public class TableXML {

	public static void main(String []args) {
		String tablesNames = "base_area, base_construction_type, base_cost_highland, base_cost_highway_classification, base_cost_rainy, base_cost_sandstorm, base_cost_terrain, base_cost_traffic_interference, base_cost_type, base_engineering_stage, base_fee_item, base_fee_item_setting, base_fee_type, base_preparation, base_project_type, base_unit, sys_cost_template, sys_fee_extend, sys_labour, sys_labour_machine_fee_details, sys_machine_fee_item, sys_price_foundation, sys_price_standard, sys_quota_store, sys_quota_store_details, sys_quota_store_expend, usr_fee_template, usr_fee_template_details, usr_fee_template_setting, usr_labour, usr_my_labour, usr_project, usr_project_cost_details, usr_project_cost_expend, usr_project_fee, usr_project_info, usr_project_labour, usr_project_labour_machine_fee_details";
		String tableXMLTemp = "        <table tableName=\"@tableName\" domainObjectName=\"@poName\"\r\n" + 
				"        	enableInsert=\"false\" \r\n" + 
				"        	enableSelectByExample=\"false\"\r\n" + 
				"        	enableUpdateByPrimaryKey=\"false\"\r\n" + 
				"        	enableDeleteByPrimaryKey=\"false\"\r\n" + 
				"        	enableDeleteByExample=\"false\"\r\n" + 
				"        	enableCountByExample=\"false\"\r\n" + 
				"        	enableUpdateByExample=\"false\">\r\n" + 
				"        </table>\r\n";
	  String xml = "";	
	  for(String t:tablesNames.split(",")) {
		  t = t.trim();
		  String tableName = t;
		  t = t.replace("base_", "");
		  t = t.replace("usr_", "");
		  t = t.replace("sys_", "");
		  String itemXML = tableXMLTemp.replace("@tableName", tableName).replace("@poName", BerriesUtils.underlineToUpperCamelCase(t));
		  xml += itemXML;
	  }
	  System.out.println(xml);
	}
}
