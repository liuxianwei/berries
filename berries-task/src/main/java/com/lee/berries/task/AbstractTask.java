package com.lee.berries.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTask implements Runnable {

    private int cycNum; 
    private int index;
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void run() {
        try{
            doProcess();
        }
        catch(Exception e){
            logger.error("任务执行出错", e);
            //防御性容错！
        }
    }
    
    /**
     * 执行任务的方法
     */
    public abstract void doProcess();

    public int getCycNum() {
        return cycNum;
    }

    public void setCycNum(int cycNum) {
        this.cycNum = cycNum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
