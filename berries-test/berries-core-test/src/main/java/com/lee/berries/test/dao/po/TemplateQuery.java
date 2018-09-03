package com.lee.berries.test.dao.po;

import java.util.Date;

import com.lee.berries.dao.query.BaseQuery;

public class TemplateQuery extends BaseQuery<Template> {
	
	private Long app_app_id;
	private String app_app_name;
	private Boolean app_delete_flag = false;
	
	private Long template_id;
	private Long template_type_id;
	private String template_name;
	private String template_provider_code;
	private String template_title;
	private Long template_signature_id;
	private String template_code;
	private Boolean template_enable_flag;
	private Date min_template_create_time;
	private Date max_template_create_time;
	private Boolean template_delete_flag = false;
	
	private String order_template_id = DESC;
	
	protected String getStatisticsSQL() {
		String statisticsSQL = "select count(1) as c, ifnull(sum(template.enable_flag),0) as enable_flag"
				+ " from ad_template template"
				+ " left join ad_app app on app.app_id=template.app_id";
		return statisticsSQL;
	}
	
	@Override
	protected String getQuerySQL() {
		String sql = "select template.id,template.signature_id, "
				+ " template.name, template.code,template.title,"
				+ " template.app_id, template.type_id,template.provider_code,"
				+ " template.create_time, template.enable_flag,"
				+ " app.app_name"
				+ " from ad_template template"
				+ " left join ad_app app on app.app_id=template.app_id";
		return sql;
	}

	public Long getApp_app_id() {
		return app_app_id;
	}

	public void setApp_app_id(Long app_app_id) {
		this.app_app_id = app_app_id;
	}

	public String getApp_app_name() {
		return app_app_name;
	}

	public void setApp_app_name(String app_app_name) {
		this.app_app_name = app_app_name;
	}

	public Boolean getApp_delete_flag() {
		return app_delete_flag;
	}

	public void setApp_delete_flag(Boolean app_delete_flag) {
		this.app_delete_flag = app_delete_flag;
	}
	
	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public Boolean getTemplate_enable_flag() {
		return template_enable_flag;
	}

	public void setTemplate_enable_flag(Boolean template_enable_flag) {
		this.template_enable_flag = template_enable_flag;
	}

	public Date getMin_template_create_time() {
		return min_template_create_time;
	}

	public void setMin_template_create_time(Date min_template_create_time) {
		this.min_template_create_time = min_template_create_time;
	}

	public Date getMax_template_create_time() {
		return max_template_create_time;
	}

	public void setMax_template_create_time(Date max_template_create_time) {
		this.max_template_create_time = max_template_create_time;
	}

	public Boolean getTemplate_delete_flag() {
		return template_delete_flag;
	}

	public void setTemplate_delete_flag(Boolean template_delete_flag) {
		this.template_delete_flag = template_delete_flag;
	}

	public String getOrder_template_id() {
		return order_template_id;
	}

	public void setOrder_template_id(String order_template_id) {
		this.order_template_id = order_template_id;
	}

	public String getTemplate_provider_code() {
		return template_provider_code;
	}

	public void setTemplate_provider_code(String template_provider_code) {
		this.template_provider_code = template_provider_code;
	}

	public String getTemplate_title() {
		return template_title;
	}

	public void setTemplate_title(String template_title) {
		this.template_title = template_title;
	}

	public Long getTemplate_signature_id() {
		return template_signature_id;
	}

	public void setTemplate_signature_id(Long template_signature_id) {
		this.template_signature_id = template_signature_id;
	}

	public Long getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}

	public Long getTemplate_type_id() {
		return template_type_id;
	}

	public void setTemplate_type_id(Long template_type_id) {
		this.template_type_id = template_type_id;
	}
}
