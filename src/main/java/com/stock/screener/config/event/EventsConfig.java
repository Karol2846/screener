package com.stock.screener.config.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class EventsConfig {

    @Bean
    @Primary
    public TaskExecutor asynctaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
