package com.wedding.mkmn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchExeConfig implements SchedulingConfigurer{

	final static int SCHEDULER_POOL_SIZE = 1000;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(SCHEDULER_POOL_SIZE);
		taskScheduler.initialize();
		taskRegistrar.setTaskScheduler(taskScheduler);		
	}
	
}
