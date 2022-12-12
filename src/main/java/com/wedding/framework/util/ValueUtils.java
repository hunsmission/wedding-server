package com.wedding.framework.util;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


import org.apache.commons.lang3.math.NumberUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wedding.framework.exception.AbstractException;
import com.wedding.framework.exception.BizException;
import com.wedding.framework.exception.LogicException;
import com.wedding.framework.exception.SysException;
import com.wedding.framework.util.DateUtils2.DatePattern;
import com.wedding.framework.util.DateUtils2.Zone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValueUtils {

	public static void assertNotEmpty(Object value, String fieldName) {
		if (ObjectUtils.isEmpty(value)) {
			throw new BizException("EMPTY_FIELD", fieldName + " param value is required!!", new Property("fieldName", fieldName));
		}
	}

	public static <T> T get(Optional<T> optional) {
		return ObjectUtils.isEmpty(optional) ? null : optional.get();
	}

	public static String pad(Object obj, int size, String direction, String padStr) {
		// 1. Validation

		String str;
		if (obj == null) {
			str = "";
		} else {
			str = obj.toString();
		}
		if (ObjectUtils.isEmpty(direction)) {
			direction = "right";
		} else {
			direction = direction.toLowerCase();
		}
		if (padStr == null) {
			padStr = " ";
		}

		// 2. Logic

		int length = str.length();
		if (size <= length) {
			return str;
		}

		StringBuilder buf = new StringBuilder();
		if (direction.equals("left")) {
			for (int i = 0; i < size - length; i++) {
				buf.append(padStr);
			}
			buf.append(str);
		} else if (direction.equals("right")) {
			buf.append(str);
			for (int i = 0; i < size - length; i++) {
				buf.append(padStr);
			}
		}

		return buf.toString();
	}

	public static boolean equals(Object a, Object b) {
		if (a == null && b == null) {
			return true;
		} else if ((a != null && b == null) || (a == null && b != null)) {
			return false;
		}
		return a.equals(b);
	}

	public static boolean isNumber(String value) {
		if (value != null) {
			value = value.trim();
			if (value.endsWith("D") || value.endsWith("L") || value.endsWith("F")) {
				return false;
			}
			while (value.length() > 1 && value.startsWith("0") && !value.startsWith("0.") && !value.startsWith("0x") && !value.startsWith("0X")) {
				value = value.substring(1);
			}
		}
		return NumberUtils.isCreatable(value);
	}

	public static String getString(Object data, String fieldName) {
		return getString(data, fieldName, null);
	}

	public static String getString(Object data, String fieldName, String defaultValue) {
		Object obj = get(data, fieldName);
		String value = toString(obj, defaultValue);
		return value;
	}

	public static Object get(Object data, String fieldName) {
		Method getter = ReflectionUtils.getGetter(data, fieldName);
		if (getter == null) {
			throw new LogicException("METHOD_NOT_FOUND", "Cannot find getter of " + ReflectionUtils.toClass(data).getName());
		}
		Object obj;
		try {
			obj = getter.invoke(data);
		} catch (IllegalAccessException | InvocationTargetException e) {
			AbstractException ae = toAe(e, null);
			throw ae;
		}
		return obj;
	}

	public static void set(Object data, String fieldName, Object value) {
		Method setter = ReflectionUtils.getSetter(data, fieldName);
		if (setter == null) {
			throw new LogicException("METHOD_NOT_FOUND", "Cannot find setter of " + ReflectionUtils.toClass(data).getName());
		}
		Class<?> type = setter.getParameterTypes()[0];

		if (ValueUtils.isPrimitiveType(type)) {
			value = ValueUtils.toRequiredType(value, type);
		}

		try {
			setter.invoke(data, value);
		} catch (IllegalAccessException | InvocationTargetException e) {
			AbstractException ae = toAe(e, null);
			throw ae;
		}
	}

	public static String toString(Object value, String... defaultValue) {
		if (value == null //
				|| (ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(defaultValue))) {
			String notNull = value == null ? null : value.toString();
			if (ObjectUtils.isEmpty(defaultValue)) {
				return notNull;
			}
			for (String str : defaultValue) {
				if (str == null) {
					continue;
				}
				if (!ObjectUtils.isEmpty(str)) {
					return str;
				} else if (notNull == null) {
					notNull = str;
				}
			}
			return notNull;
		}
		// TODO many other cases
		return value.toString();
	}

	private static final Map<Class<?>, Object> SKIP_TO_JSON_MAP = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	private static String toStringFromObject(Object value) {
		if (value instanceof Instant) {
			return DateUtils2.format((Instant) value, DatePattern.yyyy_MM_dd_HH_mm_ss, Zone.SYS);
		} else if (value instanceof Date) {
			Instant instant = Instant.ofEpochMilli(((Date) value).getTime());
			return DateUtils2.format(instant, DatePattern.yyyy_MM_dd_HH_mm_ss, Zone.SYS);
		} else if (value instanceof Double) {
			return BigDecimal.valueOf((Double) value).toString();
		} else if (value instanceof Object[]) {
			StringBuilder buf = new StringBuilder("[");
			int i = 0;
			for (Object obj : (Object[]) value) {
				buf.append(i++ == 0 ? "" : ",").append(ValueUtils.toString(obj));
			}
			buf.append("]");
			return buf.toString();
		} else if (value instanceof Collection) {
			StringBuilder buf = new StringBuilder("[");
			int i = 0;
			for (Object obj : (Collection<Object>) value) {
				buf.append(i++ == 0 ? "" : ",").append(ValueUtils.toString(obj));
			}
			buf.append("]");
			return buf.toString();
		} else if (isPrimitiveType(value) || SKIP_TO_JSON_MAP.containsKey(value.getClass())) {
			return value.toString();
		}

		try {
			String str = toJsonStr(value);
			return str;
		} catch (Exception e) {
			SKIP_TO_JSON_MAP.put(value.getClass(), new Object());
			log.warn(e.getMessage());
			return value.toString();
		}
	}

	public static Integer toInteger(Object value, Integer defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Boolean) {
			return (Boolean) value ? 1 : 0;
		} else if (value instanceof Long) {
			return ((Long) value).intValue();
		} else if (value instanceof Double) {
			return ((Double) value).intValue();
		}

		String str = value.toString();
		str = adjustNumber(str);

		if (str.contains("+")) {
			Integer i = new BigDecimal(str).intValue();
			return i;
		} else if (str.contains(".")) {
			Integer i = new Double(str).intValue();
			return i;
		}

		return NumberUtils.toInt(str);
	}

	public static Long toLong(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Long) {
			return (Long) value;
		} else if (value instanceof Boolean) {
			return (Boolean) value ? 1L : 0L;
		} else if (value instanceof Integer) {
			return ((Integer) value).longValue();
		} else if (value instanceof Double) {
			return ((Double) value).longValue();
		}

		String str = value.toString();
		str = adjustNumber(str);

		if (str.contains("+")) {
			Long i = new BigDecimal(str).longValue();
			return i;
		} else if (str.contains(".")) {
			Long i = new Double(str).longValue();
			return i;
		}

		return NumberUtils.toLong(str);
	}

	public static Double toDouble(Object value, Double defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof Boolean) {
			return (Boolean) value ? 1d : 0d;
		}

		String str = value.toString();
		str = adjustNumber(str);

		if (str.contains("+")) {
			Double d = new BigDecimal(str).doubleValue();
			return d;
		} else if (str.contains(".")) {
			Double d = new Double(str);
			return d;
		}

		return NumberUtils.toDouble(str);
	}

	public static String toVersion(Long versionNo) {
		if (versionNo == null) {
			return null;
		} else if (versionNo == 0L) {
			return "0";
		}

		StringBuilder buf = new StringBuilder();
		String str = versionNo + "";
		int i = 6;
		int len;
		do {
			len = str.length();
			if (len > i) {
				String ver = str.substring(0, len - i);
				str = str.substring(len - i);
				buf.append(ValueUtils.toInteger(ver, 0));
			} else {
				buf.append("0");
			}
			buf.append(i == 2 ? "-" : ".");
			i = i - 2;
		} while (i > 0);
		if (len > 1) {
			if ("99".equals(str)) {
				buf.append("RELEASE");
			} else {
				buf.append("RC").append(ValueUtils.toInteger(str, 0));
			}
		}
		return buf.toString();
	}

	public static long toVersionNo(String version) {
		if (version == null) {
			version = "0";
		}
		StringBuilder buf = new StringBuilder();
		int i = 0;
		for (String str : StringUtils.tokenizeToStringArray(version, ".-")) {
			if (i++ >= 4) {
				throw new BizException("VERSION_FORMAT_INVALID", new Property("version", version));
			}

			// Number
			if (ValueUtils.isNumber(str)) {
				if (i == 4) {
					throw new BizException("VERSION_FORMAT_INVALID", new Property("version", version));
				} else if (str.length() > 2) {
					throw new BizException("VERSION_NO_TOO_BIG", new Property("version", version));
				}
				buf.append(ValueUtils.pad(str, 2, "left", "0"));
				continue;
			}

			// Intermediates
			while (i < 4) {
				i++;
				buf.append("00");
			}

			// Suffix
			if ("RELEASE".equals(str)) {
				buf.append("99");
			} else if (str.startsWith("RC") && ValueUtils.isNumber(str.substring(2))) {
				String rc = str.substring(2);
				if (rc.length() > 2) {
					throw new BizException("VERSION_NO_TOO_BIG", new Property("version", version));
				}
				buf.append(ValueUtils.pad(rc, 2, "left", "0"));
			} else {
				throw new BizException("VERSION_FORMAT_INVALID", new Property("version", version));
			}
		}
		while (i < 4) {
			i++;
			if (i == 4) {
				buf.append("99");
			} else {
				buf.append("00");
			}
		}
		return ValueUtils.toLong(buf, 0L);
	}

	@SuppressWarnings("unchecked")
	public static <T> T toRequiredType(Object value, Class<T> requiredType) {
		LogicUtils.assertNotNull(requiredType, "requiredType");
		if (value == null) {
			return null;
		}
		if (requiredType.isAssignableFrom(value.getClass())) {
			return (T) value;
		} else if (isPrimitiveType(requiredType)) {
			T to;
			if (String.class.equals(requiredType)) {
				to = (T) ValueUtils.toString(value);
			} else if (int.class.equals(requiredType) || Integer.class.equals(requiredType)) {
				to = (T) toInteger(value, null);
			} else if (long.class.equals(requiredType) || Long.class.equals(requiredType)) {
				to = (T) toLong(value, null);
			} else if (double.class.equals(requiredType) || Double.class.equals(requiredType)) {
				to = (T) toDouble(value, null);
			} else {
				throw new LogicException("UNSUPPORTED_CONVERSION", "Not yet supported From String to " + requiredType.getName());
			}
			return to;
		} else if (value instanceof String) {
			return fromJsonStr((String) value, requiredType);
		} else {
			return JACKSON.convertValue(value, requiredType);
		}
	}

	public static Instant min(@SuppressWarnings("unchecked") Closure<Instant>... closures) {
		return min(Arrays.asList(closures).stream().map(closure -> closure.execute()).collect(Collectors.toList()));
	}

	public static Instant min(Instant... instants) {
		return min(Arrays.asList(instants));
	}

	public static Instant min(List<Instant> instants) {
		Instant min = null;
		for (Instant item : instants) {
			min = min == null || min.compareTo(item) > 0 ? item : min;
		}
		return min;
	}

	private static String adjustNumber(String str) {
		int i = 0;
		while (str.startsWith("0", i)) {
			if (str.startsWith(".", i + 1)) {
				break;
			}
			i++;
		}

		String numStr;
		if (i == 0) {
			numStr = str;
		} else if (str.length() == i) {
			return "0";
		} else {
			numStr = str.substring(i);
		}

		if (!NumberUtils.isCreatable(numStr)) {
			throw new IllegalArgumentException("This value is not numberFormat: " + str);
		}
		return numStr;
	}

	private final static Set<String> TRUE_SET = asSet("true", "TRUE", "t", "T", "yes", "YES", "Yes", "y", "Y", "on");

	public static Boolean toBoolean(Object value, Boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		}
		String str = value.toString().trim();
		if (NumberUtils.isCreatable(str)) {
			return NumberUtils.toInt(str) > 0;
		}
		return TRUE_SET.contains(str);
	}

	/**
	 * Convert array to Set<br>
	 *
	 * <pre>
	 * For example:
	 * {@code
	 * Set <String> set = ValueUtils.toSet("a", "b", "c");
	 * }
	 * </pre>
	 *
	 * @param <T>   The items' data type of array and Set
	 * @param value &lt;T&gt; array
	 * @return Set&lt;T&gt; data
	 */
	public static <T> Set<T> asSet(@SuppressWarnings("unchecked") T... value) {
		return Arrays.asList(value).stream().collect(Collectors.toSet());
	}

	public static <T> T[] asArray(@SuppressWarnings("unchecked") T... value) {
		return value;
	}

	/**
	 * Convert StringValue to ObjectValue by DataTypeStr
	 *
	 * @param str
	 * @param dataType
	 * @return
	 */
	public static Object toObjectByDataTypeStr(String str, String dataType) {
		if (str == null || dataType == null) {
			return null;
		}

		if ("Boolean".equals(dataType)) {
			return new Boolean(str);
		} else if ("Double".equals(dataType)) {
			return new Double(str);
		} else if ("Integer".equals(dataType)) {
			return new Integer(str);
		} else if ("Long".equals(dataType)) {
			return new Integer(str);
		}

		return str;
	}

	public static String toLabel(boolean space, Object... strs) {
		StringBuilder buf = new StringBuilder();
		int i = 0;
		for (Object str : strs) {
			if (ObjectUtils.isEmpty(str)) {
				continue;
			}
			if (i == 0) {
				buf.append(str);
			} else if (i == 1) {
				buf.append(space ? " " : "").append("(").append(str);
			} else {
				buf.append(space ? " " : "").append("-").append(space ? " " : "").append(str);
			}
			i++;
		}
		if (i > 1) {
			buf.append(")");
		}
		return buf.toString();
	}

	private static DozerBeanMapper DOZER = new DozerBeanMapper();
	static {
		DOZER.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
	}

	public static <T> T map(Object from, Class<T> requiredType) {
		return DOZER.map(from, requiredType);
	}

	public static void map(Object from, Object to) {
		DOZER.map(from, to);
	}

	private static final ObjectMapper JACKSON;
	static {
		JACKSON = new ObjectMapper();
		JACKSON.setSerializationInclusion(Include.NON_NULL);
//		JACKSON.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
//		JACKSON.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
//		JACKSON.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		JACKSON.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
		JACKSON.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// JACKSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		JACKSON.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		JACKSON.registerModule(new JavaTimeModule());
	}

	public static <T> T fromMap(Map<String, Object> map, Class<T> requiredType) {
		return JACKSON.convertValue(map, requiredType);
//		return fromJsonStr(toJsonStr(map), requiredType);
	}

	public static <T> T fromJsonStr(String str, Class<T> requiredType) {
		if (str == null) {
			return null;
		}
		try {
			return JACKSON.readValue(str, requiredType);
		} catch (IOException e) {
			throw new LogicException("JSON_PARSE_FAIL", e.getMessage());
		}
	}

	public static <T> T fromJsonStr(String str, TypeReference<T> requiredType) {
		if (str == null) {
			return null;
		}
		try {
			return JACKSON.readValue(str, requiredType);
		} catch (IOException e) {
			throw new LogicException("JSON_PARSE_FAIL", e.getMessage());
		}
	}

	public static String toJsonStr(Object data) {
		if (data == null) {
			return null;
		}
		try {
			return JACKSON.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			throw new LogicException("JSON_GEN_FAIL", e.getMessage());
		}
	}

	/**
	 * Get DataTypeStr from ObjectValue
	 *
	 * @param value
	 * @return
	 */
	public static String getDataTypeStr(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Boolean) {
			return "Boolean";
		} else if (value instanceof Double) {
			return "Double";
		} else if (value instanceof Integer) {
			return "Integer";
		} else if (value instanceof Long) {
			return "Long";
		} else if (value instanceof String) {
			return "String";
		} else {
			throw new RuntimeException("Unsupported dataType: " + value.getClass().getName());
		}
	}

	public static boolean isPrimitiveType(Object value) {
		if (value instanceof Class) {
			Class<?> clazz = (Class<?>) value;
			if ((String.class.isAssignableFrom(clazz) || String[].class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz) || Integer[].class.isAssignableFrom(clazz)
					|| Long.class.isAssignableFrom(clazz) || Long[].class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz) || Float[].class.isAssignableFrom(clazz)
					|| Double.class.isAssignableFrom(clazz) || Double[].class.isAssignableFrom(clazz) || BigDecimal.class.equals(clazz) || BigDecimal[].class.equals(clazz)
					|| Boolean.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz) || Instant.class.isAssignableFrom(clazz)
					|| Date.class.isAssignableFrom(clazz) || clazz.isEnum() || Class.class.equals(clazz))) {
				return true;
			}
		} else if (value instanceof String || value instanceof String[] || value instanceof Integer || value instanceof Integer[] || value instanceof Long
				|| value instanceof Long[] || value instanceof Float || value instanceof Float[] || value instanceof Double || value instanceof Double[]
				|| value instanceof BigDecimal || value instanceof BigDecimal[] || value instanceof Boolean || value instanceof Character || value instanceof Instant
				|| value instanceof Date || value.getClass().isEnum()) {
			return true;
		}
		return false;
	}

	public static String abbreviate(String str, int maxLength) {
		if (str == null || str.length() < maxLength) {
			return str;
		}
		return str.substring(0, maxLength);
	}

	public static Instant max(Instant a, Instant b) {
		if (a == null) {
			return b;
		} else if (b == null) {
			return a;
		} else {
			return a.compareTo(b) > 0 ? a : b;
		}
	}

	public static Integer max(Integer a, Integer b) {
		if (a == null) {
			return b;
		} else if (b == null) {
			return a;
		} else {
			return a.compareTo(b) > 0 ? a : b;
		}
	}

	public static Integer min(Integer a, Integer b) {
		if (a == null) {
			return b;
		} else if (b == null) {
			return a;
		} else {
			return a.compareTo(b) > 0 ? b : a;
		}
	}

	public static Double max(Double a, Double b) {
		if (a == null) {
			return b;
		} else if (b == null) {
			return a;
		} else {
			return a.compareTo(b) > 0 ? a : b;
		}
	}

	public static Throwable unwrap(Throwable t) {
		while (t instanceof InvocationTargetException) {
			t = ((InvocationTargetException) t).getTargetException();
		}
		return t;
	}

	@Deprecated
	public static RuntimeException toRuntimeException(Throwable t) {
		t = unwrap(t);

		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		}
		// TODO Runtime Exception
		return new RuntimeException(t.getMessage(), t);
	}

	public static AbstractException toAe(Throwable t, String defaultCode) {
		return t instanceof AbstractException ? (AbstractException) t : new SysException(ValueUtils.toString(defaultCode, "UNKNOWN"), t);
	}

//	public static <K, V> Map<V, K> reverseMap(Map<K, V> map) {
//		return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
//	}

	public static String appendTrace(StringBuilder buf, boolean selfOnly, String prevFirstLine, StackTraceElement[] stes, Set<String> exceptions) {
		if (ObjectUtils.isEmpty(stes)) {
			return prevFirstLine;
		}

		int j = 0;
		for (StackTraceElement ste : stes) {
			String className = ste.getClassName();
			if (className.contains("CGLIB$$")) {
				continue;
			}
			if (exceptions != null && exceptions.contains(className)) {
				continue;
			}
			boolean ourPkg = className.startsWith("com.emoldino.") || className.startsWith("saleson.");
			if (selfOnly && !ourPkg) {
				continue;
			}

			boolean aspect = false;
			boolean controller = false;
			boolean service = false;
			boolean util = false;
			if (ourPkg) {
				aspect = className.endsWith("Aspect");
				int len = className.length();
				controller = !aspect && len > 14 && className.substring(len - 14).contains("Controller");
				service = !controller && className.contains(".service.");
				util = !controller && !service && className.contains(".util.");
			}

			String methodName = ste.getMethodName();
			if (j++ == 0) {
				String line = System.lineSeparator() + "\tat " + className + "." + methodName + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
				if (prevFirstLine != null && prevFirstLine.equals(line)) {
					break;
				}
				if (prevFirstLine != null) {
					buf.append(System.lineSeparator()).append("Caused By: ");
				}
				prevFirstLine = line;
				buf.append(line);
			} else {
				buf.append(System.lineSeparator()).append("\tat ").append(className).append(".").append(methodName).append("(").append(ste.getFileName()).append(":")
						.append(ste.getLineNumber()).append(")");
			}

			if (!selfOnly && (aspect || controller || service || util)) {
//				int index = className.lastIndexOf(".");
//				StringBuilder pathName = new StringBuilder();
//				Arrays.asList(StringUtils.tokenizeToStringArray(className.substring(0, index), ".")).stream().filter(name -> !pathName.toString().endsWith(name))
//						.forEach(name -> pathName.append(name.charAt(0)).append("."));
//				pathName.append(className.substring(index + 1));
//				path.append(path.length() == 0 ? "" : "/").append(pathName);
				buf.append(" <<<============= The point of a ");
				if (aspect) {
					buf.append("aspect.");
				} else if (controller) {
					buf.append("controller.");
				} else if (service) {
					buf.append("service.");
				} else if (util) {
					buf.append("util.");
				}
			}
		}

		return prevFirstLine;
	}

	public static String generateNameWithAutoIncrementIndex(String prefix, int digitNum, int index) {
		return prefix + String.format("%0" + digitNum + "d", index + 1);
	}

	public static void closeQuietly(Closeable closeable) {
		if (closeable == null) {
			return;
		}
		try {
			closeable.close();
		} catch (Exception e) {
			// Do Nothing
		}
	}

}
