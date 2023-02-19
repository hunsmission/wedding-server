package com.wedding.api.common.resource.congrats;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wedding.api.common.resource.congrats.dto.CongratsDatIn;
import com.wedding.api.common.resource.congrats.repository.WeddingPost;
import com.wedding.framework.dto.SuccessOut;

@RequestMapping("/api/mkmn/congrats")
public interface CongratsController {
	
	@GetMapping
	Page<WeddingPost> getPostList(Pageable pageable);
	
	@GetMapping("/{id}")
	WeddingPost getPost(@PathVariable Long id);
	
	@PutMapping("/{id}")
    SuccessOut putPost(CongratsDatIn input);
	
	@PostMapping("/one")
	SuccessOut postNewPost(CongratsDatIn input);
	
	@DeleteMapping("/{id}")
	SuccessOut deletePost(@PathVariable Long id);
		
	@GetMapping("/mock")
	SuccessOut saveMockData();
}
