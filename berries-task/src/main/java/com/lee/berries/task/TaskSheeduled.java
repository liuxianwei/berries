package com.lee.berries.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskSheeduled {

    private TaskScheduleService service = TaskScheduleService.getInstance();
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    public TaskSheeduled(){
        logger.info("init TaskSheeduled!");
    }
    
    @Scheduled(cron="*/1 * * * * ?")
    public void execute(){
        service.next();
    }
}
