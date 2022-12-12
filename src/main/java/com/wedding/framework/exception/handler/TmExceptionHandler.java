package com.wedding.framework.exception.handler;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TmExceptionHandler implements AsyncUncaughtExceptionHandler {

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		log.error("[Thread Error Exception]");
		log.error("[Exception Message] :: " + ex.getMessage());
		log.error("[Method Name] :: " + method.getName());
		for (Object param : params) {
			log.error("[Param Value] :: " + param);
		}
	}

}
