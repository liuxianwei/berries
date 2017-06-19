package com.lee.berries.task;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时任务调度器
 * @author Liuxianwei
 *
 */
public class TaskScheduleService {

    private final static int SHOLT_SIZE = 60; //每秒一次刚好一个小时
    
    private int currentIndex = 0;
    private Vector<LinkedList<AbstractTask>> sholt;
    private ScheduledExecutorService executorService;
    
    private static TaskScheduleService instance;
    private static Object lock = new Object();
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private TaskScheduleService(){
        sholt = new Vector<LinkedList<AbstractTask>>(SHOLT_SIZE);
        for(int i = 0; i < SHOLT_SIZE; i++){
            sholt.add(new LinkedList<AbstractTask>());
        }
        executorService = Executors.newScheduledThreadPool(10);
        logger.info("init TaskScheduleService!");
    }
    
    public void next(){
        logger.debug("start execute task on index:{}", currentIndex);
        LinkedList<AbstractTask> list = sholt.get(currentIndex);
        synchronized(lock){
            if(list != null){
                for(Iterator<AbstractTask> it = list.iterator(); it.hasNext();){
                    AbstractTask task = it.next();
                    int cycNum = task.getCycNum();
                    if(cycNum == 0){
                        executorService.execute(task);
                        it.remove();
                    }
                    else{
                        task.setCycNum(cycNum - 1);
                    }
                }
            }
            currentIndex ++;
            if(currentIndex == SHOLT_SIZE){
                currentIndex = 0;
            }
        }
    }
    
    /**
     * 添加一个定时执行任务
     * @param task 任务
     * @param delay 相对当前时间延迟秒数
     */
    public void addTask(AbstractTask task, int delay){
        if(delay < 1){
            delay = 1; //最少为一秒，避免当前秒没办法读取这个任务
        }
        int cycNum = (delay - (delay % SHOLT_SIZE)) / SHOLT_SIZE;
        int index = (currentIndex + delay) % SHOLT_SIZE;
        task.setIndex(index);
        task.setCycNum(cycNum);
        LinkedList<AbstractTask> list = sholt.get(index);
        synchronized(lock){
            if(list == null){
                list = new LinkedList<AbstractTask>();
            }
            list.add(task);
            logger.info("add task on index:{}, cycNum:{}", index, cycNum);
        }
    }
    
    public static TaskScheduleService getInstance(){
        if(instance == null){
            synchronized(lock){
                if(instance == null){
                    instance = new TaskScheduleService();
                }
            }
        }
        return instance;
    }
}
