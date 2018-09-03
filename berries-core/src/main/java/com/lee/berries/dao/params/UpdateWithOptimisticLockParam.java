package com.lee.berries.dao.params;

/**
 * 乐观锁实现的参数对象
 * @author Liuxianwei
 *
 * @param <T>
 */
public class UpdateWithOptimisticLockParam<T> {

	/**
	 * 更新的对象
	 */
	private T update;
	
	/**
	 * 锁变量对应的字段名称
	 */
	private String[] lockFields;
	
	public UpdateWithOptimisticLockParam(){}
	
	public UpdateWithOptimisticLockParam(T update, String... lockFields){
		this.update = update;
		this.lockFields = lockFields;
	}

	public T getUpdate() {
		return update;
	}

	public void setUpdate(T update) {
		this.update = update;
	}
	
	public String[] getLockFields() {
		return lockFields;
	}

	public void setLockFields(String[] lockFields) {
		this.lockFields = lockFields;
	}

	
	
	
}
