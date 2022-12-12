package com.wedding.framework.exception;

import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wedding.framework.util.Property;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class BizException extends AbstractException {

	public BizException(String code) {
		super(code);
	}

	public BizException(String code, String message) {
		super(code, message);
	}

	public BizException(String code, Throwable cause) {
		super(code, cause);
	}

	public BizException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public BizException(String code, Property... property) {
		super(code, property);
	}

	public BizException(String code, String message, Property... property) {
		super(code, message, property);
	}

	public BizException(String code, String message, Properties properites) {
		super(code, message, properites);
	}

	public BizException(String code, Throwable cause, Property... property) {
		super(code, cause, property);
	}

	public BizException(String code, Throwable cause, Properties properites) {
		super(code, cause, properites);
	}

	public BizException(String code, String message, Throwable cause, Property... property) {
		super(code, message, cause, property);
	}

}
