package com.mj.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("asyncTaskExecution")
    public Executor asyncOperation() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        we have created pools, this pool are alive even tasks are ideal
        taskExecutor.setCorePoolSize(4);
//        it will set max numbers of tasks that can be held in the queue, if queue is full again it will trigger and create pool again
        taskExecutor.setQueueCapacity(150);
//        if 4 pools are full the additional tasks will be rejected
        taskExecutor.setMaxPoolSize(4);
//
        taskExecutor.setThreadNamePrefix("AsyncTaskExecute-");
        taskExecutor.initialize();
        return taskExecutor;

    }
}
