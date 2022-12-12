package com.wedding.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class ReflectionUtils {

	private static final Map<String, Optional<Method>> METHODS = new ConcurrentHashMap<>();

//	public static String toShortName(Class<?> clazz) {
//		return toShortName(clazz, null);
//	}

	public static String toName(Class<?> clazz, String methodName) {
		return (clazz == null ? "" : (clazz.getName() + ".")) + methodName;
	}

	public static String toShortName(Class<?> clazz, String methodName) {
		StringBuilder buf = new StringBuilder();
		appendShortName(buf, clazz);
		if (!ObjectUtils.isEmpty(methodName)) {
			buf.append(clazz == null ? "" : ".").append(methodName);
		}
		return buf.toString();
	}

	public static String getShortName(ProceedingJoinPoint point) {
		LogicUtils.assertNotNull(point, "point");

		Signature signature = point.getSignature();
		String methodName = signature.getName();
		Class<?> impl = signature.getDeclaringType();

		String name = toShortName(impl, methodName);
		return name;
	}

	private static void appendShortName(StringBuilder buf, Class<?> clazz) {
		if (clazz == null) {
			return;
		}
		LogicUtils.assertNotNull(buf, "buf");
		Arrays.asList(StringUtils.tokenizeToStringArray(clazz.getPackage().getName(), ".")).stream().forEach(name -> buf.append(name.charAt(0)).append("."));
		buf.append(clazz.getSimpleName());
	}

	public static Method getMethodQuietly(Class<?> clazz, String name, Class<?>... type) {
		try {
			return clazz.getMethod(name, type);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static Method getMethod(ProceedingJoinPoint point) {
		Signature signature = point.getSignature();
		String methodName = signature.getName();
		Class<?> impl = signature.getDeclaringType();
		Object[] args = point.getArgs();
		String key;
		{
			StringBuilder buf = new StringBuilder();
			buf.append(impl.getClass().getName()).append(".").append(methodName);
			for (Object arg : args) {
				buf.append(",").append(arg == null ? null : arg.getClass().getName());
			}
			key = buf.toString();
		}
		return SyncCtrlUtils.wrap("ReflectionUtils.getMethod." + key, METHODS, key, () -> {
			for (Method item : impl.getMethods()) {
				if (!item.getName().equals(methodName) || item.getParameterCount() != args.length) {
					continue;
				}
				Class<?>[] types = item.getParameterTypes();
				int i = 0;
				boolean diff = false;
				for (Object arg : args) {
					Class<?> type = types[i++];
					if (arg == null || type.isAssignableFrom(arg.getClass())) {
						continue;
					}
					diff = true;
					break;
				}
				if (diff) {
					continue;
				}
				return Optional.of(item);
			}
			return Optional.empty();
		}).orElse(null);
	}

	public static String toFieldName(String methodName) {
		LogicUtils.assertNotEmpty(methodName, "methodName");

		String fieldName;
		int len = methodName.length();
		if (len > 3 && methodName.startsWith("get") || methodName.startsWith("set")) {
			fieldName = StringUtils.uncapitalize(methodName.substring(3));
		} else if (len > 2 && methodName.startsWith("is")) {
			fieldName = StringUtils.uncapitalize(methodName.substring(2));
		} else {
			fieldName = StringUtils.uncapitalize(methodName);
		}
		return fieldName;
	}

	public static Field getFieldAndSetAccessible(Object target, String name) {
		Field field = getField(target, name);
		if (field != null && !field.isAccessible()) {
			field.setAccessible(true);
		}
		return field;
	}

	private static Map<Class<?>, List<Field>> FIELDS = new ConcurrentHashMap<Class<?>, List<Field>>();

	public static List<Field> getFieldList(Object obj, boolean cache) {
		LogicUtils.assertNotNull(obj, "obj");

		Class<?> clazz = obj instanceof Class ? (Class<?>) obj : obj.getClass();
		if (FIELDS.containsKey(clazz)) {
			return FIELDS.get(clazz);
		} else if (cache) {
			loadFieldCache(clazz);
			return FIELDS.get(clazz);
		}

		List<Field> list = new ArrayList<Field>();
		populateFieldInfo(list, clazz);
		return list;
	}

	private static void loadFieldCache(final Class<?> clazz) {
		SyncCtrlUtils.wrap("ReflectionUtils.fieldList." + clazz.getName(), FIELDS, clazz, () -> {
			List<Field> fieldList = new ArrayList<Field>();
			populateFieldInfo(fieldList, clazz);
			return fieldList;
		});
	}

	private static void populateFieldInfo(List<Field> fieldList, Class<?> clazz) {
		if (clazz == null || clazz.equals(Object.class)) {
			return;
		}
		populateFieldInfo(fieldList, clazz.getSuperclass());
		for (Field field : clazz.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (fieldList != null) {
				fieldList.add(field);
			}
		}
	}

	private static final Map<Class<?>, Map<String, Optional<Field>>> NAME_FIELDS = new ConcurrentHashMap<>();

	public static Field getField(Object target, String name) {
		LogicUtils.assertNotNull(target, "target");
		LogicUtils.assertNotEmpty(name, "name");

		Class<?> clazz = target instanceof Class ? (Class<?>) target : target.getClass();
		if (clazz.equals(Object.class)) {
			return null;
		}

		Map<String, Optional<Field>> fields = SyncCtrlUtils.wrap(clazz.getSimpleName(), NAME_FIELDS, target.getClass(), () -> new ConcurrentHashMap<>());
		Field field = SyncCtrlUtils.wrapNullable(clazz.getSimpleName() + "." + name, fields, name, () -> {
			return _getField(clazz, name);
		});

		return field;
	}

	private static Field _getField(Class<?> clazz, String name) {
		if (clazz.equals(Object.class)) {
			return null;
		}

		Field field = null;
		try {
			field = clazz.getDeclaredField(name);
		} catch (SecurityException e) {
			// Skip
		} catch (NoSuchFieldException e) {
			field = _getField(clazz.getSuperclass(), name);
		}
		return field;
	}

	public static List<Type> getActualTypeList(Type genericType) {
		LogicUtils.assertNotNull(genericType, "genericType");

		if (genericType instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericType;
			return Arrays.asList(parameterizedType.getActualTypeArguments());
		}

		if (genericType instanceof GenericArrayType) {
			GenericArrayType arrayType = (GenericArrayType) genericType;
			return getActualTypeList(arrayType.getGenericComponentType());
		}

		throw new IllegalArgumentException("Actual types of genericType: " + genericType + " is ambiguous");
	}

	public static Method getGetter(Object obj, String fieldName) {
		LogicUtils.assertNotNull(obj, "obj");
		LogicUtils.assertNotEmpty(fieldName, "fieldName");

		Class<?> clazz = toClass(obj);

		// TODO Consider Caching Getter after performance test
		try {
			return clazz.getMethod(ReflectionUtils.toGetterName(fieldName));
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static Class<?> toClass(Object obj) {
		Class<?> clazz = obj instanceof Class ? (Class<?>) obj : obj.getClass();
		return clazz;
	}

	public static Method getSetter(Object obj, String fieldName) {
		LogicUtils.assertNotNull(obj, "obj");
		LogicUtils.assertNotEmpty(fieldName, "fieldName");
		Class<?> clazz = toClass(obj);

		// TODO Consider Caching Setter after performance test
		Method getter = getGetter(clazz, fieldName);
		if (getter == null) {
			return null;
		}
		try {
			return clazz.getMethod(ReflectionUtils.toSetterName(fieldName), getter.getReturnType());
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static String toGetterName(String fieldName) {
		return "get" + toMethodName(fieldName);
	}

	public static String toSetterName(String fieldName) {
		return "set" + toMethodName(fieldName);
	}

	private static String toMethodName(String fieldName) {
		LogicUtils.assertNotEmpty(fieldName, "fieldName");

		if (fieldName.length() == 1) {
			return fieldName.toUpperCase();
		}

		char c = fieldName.charAt(1);
		if (c > 96 && c < 123) {
			return StringUtils.capitalize(fieldName);
		}
		return fieldName;
	}

}
