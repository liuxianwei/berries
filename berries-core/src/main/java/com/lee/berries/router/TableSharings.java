package com.lee.berries.router;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lee.berries.router.model.BaseSharing;
import com.lee.berries.router.model.DateSharing;
import com.lee.berries.router.model.ModSharing;
import com.lee.berries.router.model.RangeSharing;
import com.lee.berries.router.model.SharingRuleEnum;
import com.lee.berries.router.model.ValueSharing;

@Component
public class TableSharings {

	@Value("${sharing.enable:false}")
	private boolean enableSharing;
	
	private Map<String, BaseSharing> rules = new HashMap<>();

	public Map<String, BaseSharing> getRules() {
		return rules;
	}
	
	public BaseSharing get(String tableName) {
		return rules.get(tableName);
	}
	
	@Value("${sharing.rule:[]}")
	public void setShareRule(String shareRule) {
		JSONArray jsonArray = JSON.parseArray(shareRule);
		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String rule = jsonObject.getString("rule").toUpperCase();
			jsonObject.put("rule", rule);
			SharingRuleEnum sharingRuleEnum = SharingRuleEnum.valueOf(rule);
			BaseSharing baseSharing = null;
			if(SharingRuleEnum.DATE == sharingRuleEnum) {
				baseSharing = JSON.toJavaObject(jsonObject, DateSharing.class);
			}
			else if(SharingRuleEnum.MOD == sharingRuleEnum){
				baseSharing = JSON.toJavaObject(jsonObject, ModSharing.class);
			}
			else if(SharingRuleEnum.VALUE == sharingRuleEnum){
				baseSharing = JSON.toJavaObject(jsonObject, ValueSharing.class);
			}
			else if(SharingRuleEnum.RANGE == sharingRuleEnum){
				baseSharing = JSON.toJavaObject(jsonObject, RangeSharing.class);
			}
			else {
				throw new RuntimeException("unsuport sharing rule!");
			}
			rules.put(baseSharing.getTableName(), baseSharing);
		}
	}
	
	public boolean isEnableSharing() {
		return enableSharing;
	}

	
}
