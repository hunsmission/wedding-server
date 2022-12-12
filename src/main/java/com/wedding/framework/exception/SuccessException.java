package com.wedding.framework.exception;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class SuccessException extends AbstractException {
	private Object output;

	public SuccessException(Object output) {
		super("SUCCESS");
		this.output = output;
	}

}
