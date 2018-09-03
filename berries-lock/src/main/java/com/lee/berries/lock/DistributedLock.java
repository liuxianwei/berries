package com.lee.berries.lock;


/**
 * 分布式锁接口定义
 * @author Liuxianwei
 *
 */
public interface DistributedLock {

	/**
	 * 上锁接口，当线程持有该所的时候，立刻返回，否则阻塞
	 */
	public void lock();
	
	/**
	 * 尝试上锁，立刻返回，非阻塞，true为获得该锁，false为没有获得该锁
	 */
	public boolean tryLock();
	
	/**
	 * 在timeOut的时间内尝试上锁，立刻返回，非阻塞，true为获得该锁，false为没有获得该锁
	 * 
	 * @param timeOut
	 * @return
	 */
	public boolean tryLock(int timeOut);
	
	/**
	 * 释放锁。只有当锁中记录的值和该锁的值匹配的时候才可Del
	 * 
	 */
	public void unLock();
}
