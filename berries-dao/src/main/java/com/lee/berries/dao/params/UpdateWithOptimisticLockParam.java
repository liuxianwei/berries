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
	 * 老数据存放，这里一般存除过id意外的一个或者多个锁变量旧值
	 */
	private T lock;
	
	public UpdateWithOptimisticLockParam(){}
	
	public UpdateWithOptimisticLockParam(T update, T lock){
		this.update = update;
		this.lock = lock;
	}

	public T getUpdate() {
		return update;
	}

	public void setUpdate(T update) {
		this.update = update;
	}

	public T getLock() {
		return lock;
	}

	public void setLock(T lock) {
		this.lock = lock;
	}

	
	
	
}
