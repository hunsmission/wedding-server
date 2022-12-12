package com.wedding.framework.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {
	public static BeanUtils instance;

	@Autowired
	private ApplicationContext beans;

	public BeanUtils() {
		instance = this;
	}

	private static Map<Class<?>, Object> BEAN_BY_CLASS_CACHE = new ConcurrentHashMap<Class<?>, Object>();

	public static <T> T get(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		T bean = (T) SyncCtrlUtils.wrap("BeanUtils." + clazz.getName(), BEAN_BY_CLASS_CACHE, clazz, () -> instance.beans.getBean(clazz));
		return bean;
	}

	private static Map<String, Object> BEAN_BY_NAME_CACHE = new ConcurrentHashMap<String, Object>();

	public static <T> T get(final String name, final Class<T> requiredType) {
		@SuppressWarnings("unchecked")
		T bean = (T) SyncCtrlUtils.wrap("BeanUtils." + name, BEAN_BY_NAME_CACHE, name, () -> instance.beans.getBean(name, requiredType));
		return bean;
	}

	public static boolean isBean(String name, Class<?> clazz) {
		try {
			get(name, clazz);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
}
