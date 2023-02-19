package com.wedding.api.common.resource.congrats;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.wedding.api.common.resource.congrats.dto.CongratsDatIn;
import com.wedding.api.common.resource.congrats.repository.WeddingPost;
import com.wedding.api.common.resource.congrats.service.CongratsService;
import com.wedding.framework.dto.SuccessOut;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CongratsControllerImpl implements CongratsController {

	private final CongratsService service;

	@Override
	public Page<WeddingPost> getPostList(Pageable pageable) {
		return service.getPostList(pageable);
	}

	@Override
	public WeddingPost getPost(Long id) {
		return service.getPost(id);
	}

	@Override
	public SuccessOut postNewPost(CongratsDatIn input) {
		service.putNewPost(input);
		return SuccessOut.getDefault();
	}
	
	@Override
	public SuccessOut putPost(CongratsDatIn input) {
		service.putPost(input);
		return SuccessOut.getDefault();
	}

	@Override
	public SuccessOut deletePost(Long id) {
		service.deletePost(id);
		return SuccessOut.getDefault();
	}
	
	@Override
	public SuccessOut saveMockData() {
		service.saveMockData();
		return SuccessOut.getDefault();
	}

}
