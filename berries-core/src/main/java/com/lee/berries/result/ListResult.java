package com.lee.berries.result;

import java.util.List;

import com.lee.berries.dao.Page;

public class ListResult<T> {
	// 状态码
	private Integer code;
	// 消息
	private String msg;
	// 分页总大小
	private Integer count;
	// 列表数据
	private List<T> list;

	public ListResult(ResultCodeEnum codeEnum,  Integer count, List<T> list) {
		this.setCode(codeEnum.getCode());
		this.setMsg(codeEnum.getMessage());
		this.setCount(count);
		this.setList(list);
	}
	
	public static <T> ListResult<T> bulder(ResultCodeEnum codeEnum, List<T> list) {
		int count = 0;
		if(list != null && list.size() > 0) {
			count = list.size();
		}
		return bulder(codeEnum, count, list);
	}
	
	public static <T> ListResult<T> bulder(ResultCodeEnum codeEnum, Integer count,List<T> list) {
		return new ListResult<T>(codeEnum, count, list);
	}

	public static <T> ListResult<T> successed(List<T> list) {
		return bulder(ResultCodeEnum.SUCCESS, list);
	}
	
	public static <T> ListResult<T> successed(List<T> list, int count) {
		return bulder(ResultCodeEnum.SUCCESS, count, list);
	}
	
	public static <T> ListResult<T> successed(Page<T> page) {
		return bulder(ResultCodeEnum.SUCCESS, page.getTotalResult(), page.getResult());
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
