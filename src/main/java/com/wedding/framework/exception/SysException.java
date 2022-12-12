package com.wedding.framework.exception;

import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wedding.framework.util.Property;

//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@SuppressWarnings("serial")
public class SysException extends AbstractException {
	public static final String CODE = "SYS_ERROR";

	public SysException(String code) {
		super(code);
	}

	public SysException(String code, String message) {
		super(code, message);
	}

	public SysException(String code, Throwable cause) {
		super(code, cause);
	}

	public SysException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public SysException(String code, Property... property) {
		super(code, property);
	}

	public SysException(String code, String message, Property... property) {
		super(code, message, property);
	}

	public SysException(String code, String message, Properties properties) {
		super(code, message, properties);
	}

	public SysException(String code, Throwable cause, Property... property) {
		super(code, cause, property);
	}

	public SysException(String code, String message, Throwable cause, Property... property) {
		super(code, message, cause, property);
	}

	@Override
	public String getCode() {
		return CODE;
	}

}
