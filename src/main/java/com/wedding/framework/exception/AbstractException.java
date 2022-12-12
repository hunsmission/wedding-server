package com.wedding.framework.exception;

import java.util.Properties;

import org.springframework.util.ObjectUtils;

import com.wedding.framework.util.Property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public abstract class AbstractException extends RuntimeException {
	private String code;
	private String codeMessage;
	private Properties properties = new Properties();
	private Object alternativeOutput;
	private Long id;

	public AbstractException(String code) {
		super();
		setCode(code);
	}

	public AbstractException(String code, String message) {
		super(message);
		setCode(code);
	}

	public AbstractException(String code, Throwable cause) {
		super(cause);
		setCode(code);
	}

	public AbstractException(String code, String message, Throwable cause) {
		super(message, cause);
		setCode(code);
	}

	public AbstractException(String code, Property... property) {
		super(code);
		this.code = code;
		addProperty(property);
	}

	public AbstractException(String code, String message, Property... property) {
		super(message);
		this.code = code;
		addProperty(property);
	}

	public AbstractException(String code, String message, Properties properites) {
		super(message);
		this.code = code;
		this.properties = properites;
	}

	public AbstractException(String code, Throwable cause, Property... property) {
		super(cause);
		this.code = code;
		addProperty(property);
	}

	public AbstractException(String code, Throwable cause, Properties properites) {
		super(cause);
		this.code = code;
		this.properties = properites;
	}

	public AbstractException(String code, String message, Throwable cause, Property... property) {
		super(message, cause);
		this.code = code;
		addProperty(property);
	}

	private void setCode(String code) {
		this.code = code;
		setCodeMessage(null);
	}

	public String getSysCode() {
		return code == null ? getCode() : code;
	}

	public void addProperty(Property... property) {
		if (ObjectUtils.isEmpty(property)) {
			return;
		}
		for (Property prop : property) {
			String key = prop.getName();
			String value = prop.getValue() == null ? "" : prop.getValue().toString();
			properties.setProperty(key, value);
		}
	}
}
