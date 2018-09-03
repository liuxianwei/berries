package com.lee.berries.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskSheeduled {

    private TaskScheduleService service = TaskScheduleService.getInstance();
    
    public TaskSheeduled(){
        System.out.println("=============");
    }
    
    @Scheduled(cron="*/1 * * * * ?")
    public void execute(){
        service.next();
    }
}
