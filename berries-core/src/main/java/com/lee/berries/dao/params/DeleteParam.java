package com.lee.berries.dao.params;

/**
 * 乐观锁实现的参数对象
 * @author Liuxianwei
 *
 * @param <T>
 */
public class DeleteParam<T> {

	/**
	 * 更新的对象
	 */
	private T delete;
	
	/**
	 * 除去id之外的字段名称
	 */
	private String[] fields;
	
	public DeleteParam(){}
	
	public DeleteParam(T delete, String... fields){
		this.delete = delete;
		this.fields = fields;
	}

	public T getDelete() {
		return delete;
	}

	public void setDelete(T delete) {
		this.delete = delete;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}
	
	
	
}
