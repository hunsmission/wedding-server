package com.wedding.api.common.resource.congrats;

import org.springframework.data.domain.Page;
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
	public Page<WeddingPost> getPostList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WeddingPost getPost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuccessOut putPost(CongratsDatIn input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuccessOut deletePost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuccessOut saveMockData() {
		service.saveMockData();
		return SuccessOut.getDefault();
	}
}
