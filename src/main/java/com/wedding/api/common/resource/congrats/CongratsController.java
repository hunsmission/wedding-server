package com.wedding.api.common.resource.congrats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wedding.framework.dto.SuccessOut;

@RequestMapping("/api/common/sen-his")
public interface CongratsController {
	
	@GetMapping
	SuccessOut getSenHistory();
	
}
