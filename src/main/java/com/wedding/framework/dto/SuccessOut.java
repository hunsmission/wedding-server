package com.wedding.framework.dto;

import lombok.*;

@Data
public class SuccessOut {
	private static final SuccessOut instance = new SuccessOut();

	public static SuccessOut getDefault() {
		return instance;
	}

	public boolean isSuccess() {
		return true;
	}
}
