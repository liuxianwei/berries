package com.lee.berries.common.mybatis;

import com.lee.berries.common.utils.BerriesUtils;

public class TableXML {

	public static void main(String []args) {
		String tablesNames = "gm_activity, gm_ad, gm_bind_type, gm_brokerage_details, gm_brokerage_report, gm_category, gm_game, gm_game_category, gm_game_user, gm_gift, gm_gift_cd_key, gm_gift_record, gm_order, gm_play_record, gm_provider, gm_provider_order, gm_provider_user, gm_proxy, gm_proxy_user, gm_shared_link";
		String tableXMLTemp = "<table tableName=\"@tableName\" domainObjectName=\"@poName\"\r\n" + 
				"        	enableInsert=\"false\" \r\n" + 
				"       	enableSelectByExample=\"false\"\r\n" + 
				"        	enableUpdateByPrimaryKey=\"false\"\r\n" + 
				"        	enableDeleteByPrimaryKey=\"false\"\r\n" + 
				"        	enableDeleteByExample=\"false\"\r\n" + 
				"        	enableCountByExample=\"false\"\r\n" + 
				"        	enableUpdateByExample=\"false\">\r\n" + 
				"        </table>\r\n";
	  String xml = "";	
	  for(String t:tablesNames.split(",")) {
		  t = t.trim();
		  String itemXML = tableXMLTemp.replace("@tableName", t).replace("@poName", BerriesUtils.underlineToUpperCamelCase(t.replace("gm_", "")));
		  xml += itemXML;
	  }
	  System.out.println(xml);
	}
}
