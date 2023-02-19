package com.wedding.framework.exception;

import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wedding.framework.util.Property;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
@SuppressWarnings("serial")
public class LogicException extends AbstractException {
	public static final String CODE = "LOGIC_ERROR";

	public LogicException(String code) {
		super(code);
	}

	public LogicException(String code, String message) {
		super(code, message);
	}

	public LogicException(String code, Throwable cause) {
		super(code, cause);
	}

	public LogicException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public LogicException(String code, Property... property) {
		super(code, property);
	}

	public LogicException(String code, String message, Property... property) {
		super(code, message, property);
	}

	public LogicException(String code, String message, Properties properties) {
		super(code, message, properties);
	}

	public LogicException(String code, Throwable cause, Property... property) {
		super(code, cause, property);
	}

	public LogicException(String code, String message, Throwable cause, Property... property) {
		super(code, message, cause, property);
	}

	@Override
	public String getCode() {
		return CODE;
	}

}
