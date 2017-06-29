package com.lee.berries.dao.query;

import com.lee.berries.dao.po.UserDetails;

public class UserQuery extends BaseQuery<UserDetails> {
	
	private Long u_id;
	private String order_u_id;
	

	@Override
	protected String getQuerySQL() {
		return "select id as id, user_name as userName from user_details u ";
	}


	public Long getU_id() {
		return u_id;
	}


	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}


	public String getOrder_u_id() {
		return order_u_id;
	}


	public void setOrder_u_id(String order_u_id) {
		this.order_u_id = order_u_id;
	}

}
