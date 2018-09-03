package com.lee.berries.lock;


import org.springframework.data.redis.core.RedisTemplate;

/**
 * 创建分布式锁工厂方法
 * @author Liuxianwei
 *
 */
public class DistributedLockFactory {

	/**
	 * 构造一个默认的分布式锁，默认3秒超时
	 * @param lockName
	 * @return
	 */
	public static DistributedLock builder(String lockName, RedisTemplate<String, Object> redis){
		RedisDistributedLockImpl lock = new RedisDistributedLockImpl(lockName, redis);
		return lock;
	}
	
	/**
	 * 构造一个分布式锁，以传入的timeout为超时时间
	 * @param lockName
	 * @param timeOut
	 * @return
	 */
	public static DistributedLock builder(String lockName, RedisTemplate<String, Object> redis, long timeOut){
		RedisDistributedLockImpl lock = new RedisDistributedLockImpl(lockName, redis, timeOut);
		return lock;
	}
}
