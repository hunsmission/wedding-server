package com.wedding.framework.util;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyncCtrlUtils {

	public static <V> V wrap(String name, Closure<V> closure) {
		synchronized (get(name)) {
			try {
				return closure.execute();
			} finally {
				release(name);
			}
		}
	}

	public static void wrap(String name, ClosureNoReturn closure) {
		synchronized (get(name)) {
			try {
				closure.execute();
			} finally {
				release(name);
			}
		}
	}

	private static Map<String, Monitor> cache = new ConcurrentHashMap<String, Monitor>();

	private static Object get(String name) {
		Assert.notNull(name, "name is required!");

		synchronized (cache) {
			Monitor monitor = null;
			if (cache.containsKey(name)) {
				monitor = cache.get(name);
				monitor.i++;
			} else {
				monitor = Monitor.newInstance();
				cache.put(name, monitor);
			}
			return monitor;
		}
	}

	private static void release(String name) {
		Assert.notNull(name, "name is required!");

		synchronized (cache) {
			if (!cache.containsKey(name)) {
				return;
			}

			Monitor monitor = cache.get(name);
			if (monitor.i > 0) {
				monitor.i--;
				return;
			}

			cache.remove(name);
		}
	}

	private static class Monitor {
		static Monitor newInstance() {
			return new Monitor();
		}

		int i = 0;
	}

	public static <K, V> V wrap(final String name, final Map<K, V> cache, final K key, final Closure<V> closure) {
		LogicUtils.assertNotEmpty(name, "name");
		LogicUtils.assertNotNull(cache, "cache");

		final boolean debug = log.isDebugEnabled();

		if (key == null) {
			if (debug) {
				log.debug("key is null, so execute closure right away.");
			}
			return (V) wrap(name, closure);
		}

		if (cache.containsKey(key)) {
			if (debug) {
				log.debug("get data from map cache by key: " + key);
			}
			return cache.get(key);
		}

		return wrap(name, () -> {
			V value = CacheUtils.get(cache, key, closure);
			return value;
		});
	}
	
	public static <K, V> V wrapNullable(final String name, final Map<K, Optional<V>> cache, final K key, final Closure<V> closure) {
		LogicUtils.assertNotEmpty(name, "name");
		LogicUtils.assertNotNull(cache, "cache");

		final boolean debug = log.isDebugEnabled();

		if (key == null) {
			if (debug) {
				log.debug("key is null, so execute closure right away.");
			}
			return (V) wrap(name, closure);
		}

		if (cache.containsKey(key)) {
			if (debug) {
				log.debug("get data from map cache by key: " + key);
			}
			return cache.get(key).orElse(null);
		}

		return wrap(name, () -> {
			V value = CacheUtils.getNullable(cache, key, closure);
			return value;
		});
	}

}
