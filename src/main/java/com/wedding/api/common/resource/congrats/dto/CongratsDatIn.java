package com.wedding.api.common.resource.congrats.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CongratsDatIn {
	private Long id;
	private String name;	
	private String password;	
	private String content;
}
