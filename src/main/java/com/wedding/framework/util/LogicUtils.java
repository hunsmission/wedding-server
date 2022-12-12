package com.wedding.framework.util;

import org.springframework.util.ObjectUtils;

import com.wedding.framework.exception.LogicException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogicUtils {

	public static void assertNotNull(Object value, String fieldName) {
		if (value == null) {
			throw new LogicException("NULL_FIELD", fieldName + " is required!!");
		}
	}

	public static void assertNotEmpty(Object value, String fieldName) {
		if (ObjectUtils.isEmpty(value)) {
			throw new LogicException("EMPTY_FIELD", fieldName + " is required!!");
		}
	}

	public static void assertNull(Object value, String fieldName) {
		if (value != null) {
			throw new LogicException("NOT_NULL_FIELD", fieldName + " should be null!!");
		}
	}

	public static LogicException newOverPeriodException(String resource, String maxPeriod, String fromDate, String toDate) {
		return new LogicException("OVER_PERIOD", resource + " longer than " + maxPeriod + " period is not supported yet!!", new Property("fromDate", fromDate),
				new Property("toDate", toDate));
	}

}
