package com.wedding.api.common.resource.congrats;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wedding.api.common.resource.congrats.dto.CongratsDatIn;
import com.wedding.api.common.resource.congrats.repository.WeddingPost;
import com.wedding.framework.dto.SuccessOut;

@RequestMapping("/api/mkmn/congrats")
public interface CongratsController {
	
	@GetMapping
	Page<WeddingPost> getPostList();
	
	@GetMapping("/{id}")
	WeddingPost getPost();
	
	@PutMapping("/{id}")
    SuccessOut putPost(CongratsDatIn input);
	
	@DeleteMapping("/{id}")
	SuccessOut deletePost();
		
	@GetMapping("/mock")
	SuccessOut saveMockData();
}
