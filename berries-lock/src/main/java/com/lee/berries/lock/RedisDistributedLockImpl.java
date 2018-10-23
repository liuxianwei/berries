package com.lee.berries.lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Redis分布式锁实现
 * 设计思路：采用redis set key if not exist功能实现加锁操作，初始化一个锁，
 * 加锁的时候redis会创建一个key=lockName的项目，存入当前的时间戳。当本任务运行完成之后，进行解锁操作
 * 解锁操作是删除本条key=本机记录lockValue的值
 * 如果获得锁的机器意外宕机，那么势必造成死锁情况，这样的会超时机制就发挥应有的作用，当出现超时的情况就认为是有死锁产生
 * 那么这个记录将会被删除，其余线程进行锁的抢占。
 * 程序是用时间戳进行加锁操作，所以要求所有的机器时间必须同步
 * @author Liuxianwei
 *
 */
public class RedisDistributedLockImpl implements DistributedLock {

	private RedisTemplate<String, Object> redis;
	private ValueOperations<String, Object> valueOperations;
	
	private String lockName;
	private long lockValue;
	private long timeOut;
	private boolean locked = false;
	
	private final static String LOCK_PREFIX = "lock:";
	
	public static final long DEFAULT_TIMEOUT = 3000L; //默认超时时间，毫秒为单位
	
	public RedisDistributedLockImpl(String lockName, RedisTemplate<String, Object> redis){
		this(lockName, redis, DEFAULT_TIMEOUT);
	}
	
	public RedisDistributedLockImpl(String lockName, RedisTemplate<String, Object> redis, long timeOut){
		this.lockName = LOCK_PREFIX + lockName;
		this.timeOut = timeOut;
		setRedis(redis);
	}
	
	public void lock() {
		int count = 0;
		for(;;){
			lockValue = getTime();
			boolean result = valueOperations.setIfAbsent(lockName, lockValue);
			if(result){
				locked = true;
				break;
			}
			if(count++ % 20 == 0 && isTimeOut()){//没20次检测一次
				redis.delete(lockName);
			}
			try {
				Thread.sleep(3L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean tryLock() {
		lockValue = getTime();
		if(isTimeOut()){
			redis.delete(lockName);
		}
		boolean result = valueOperations.setIfAbsent(lockName, lockValue);
		if(result){
			locked = true;
			return true;
		}
		return false;
	}

	public boolean tryLock(int timeOut) {
		boolean flag = false;
		Long time = System.currentTimeMillis();
		int count = 0;
		for(;;){
			lockValue = getTime();
			boolean result = valueOperations.setIfAbsent(lockName, lockValue);
			if(result){
				flag = true;
				locked = true;
				break;
			}
			if(count++ % 20 == 0 && isTimeOut()){//没20次检测一次
				redis.delete(lockName);
			}
			try {
				Thread.sleep(3L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Long now = System.currentTimeMillis();
			if((now - time) >= timeOut){
				break;
			}
		}
		return flag;
	}

	public void unLock() {
		if(locked) {
			long oldValue = (long) valueOperations.get(lockName);
			if(lockValue == oldValue){
				redis.delete(lockName);
			}
		}
	}
	
	private boolean isTimeOut(){
		Long oldValue = (Long) valueOperations.get(lockName);
		if(oldValue == null){
			return false;
		}
		long now = System.currentTimeMillis();
		long useTime = now - oldValue;
		if(useTime >= timeOut){
			return true;
		}
		return false;
	}
	
	public void setRedis(RedisTemplate<String, Object> redis) {
		this.redis = redis;
		this.valueOperations = redis.opsForValue();
	}

	/**
	 * 获取当前时间戳，如果无法做到所有的机器时间同步，
	 * 可以采用一个机器提供时间服务，这里调用一个时间服务保证所有机器时钟同步
	 * @return 时间戳
	 */
	private long getTime(){
		return System.currentTimeMillis();
	}
}
