package com.wedding.api.common.resource.congrats.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.wedding.api.common.resource.congrats.dto.CongratsDatIn;
import com.wedding.api.common.resource.congrats.repository.WeddingPost;
import com.wedding.api.common.resource.congrats.repository.WeddingPostRepository;
import com.wedding.framework.exception.BizException;
import com.wedding.framework.exception.LogicException;
import com.wedding.framework.util.Property;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CongratsService {

	private final WeddingPostRepository repo;

	public void saveMockData() {
		
		List<WeddingPost> list = new ArrayList<>();
		WeddingPost post1 = WeddingPost.builder()//
				.name("MickeyFriend1")
				.password("1234")
				.content("Congraturation Mickey")
				.build();
		
		WeddingPost post2 = WeddingPost.builder()//
				.name("MickeyFriend2")
				.password("1234")
				.content("Congraturation~~ Mickey")
				.build();
		
		WeddingPost post3 = WeddingPost.builder()//
				.name("MickeyFriend3")
				.password("1234")
				.content("You are the Best Mickey~~ ")
				.build();
		
		WeddingPost post4 = WeddingPost.builder()//
				.name("MinnieFriend1")
				.password("1234")
				.content("You are the Best Minnie~~ ")
				.build();
		
		WeddingPost post5 = WeddingPost.builder()//
				.name("MinnieFriend2")
				.password("1234")
				.content("미니야 결혼 축하해~~ 행복해 ")
				.build();
		
		WeddingPost post6 = WeddingPost.builder()//
				.name("MinnieFriend3")
				.password("1234")
				.content("미니야 결혼 축하해~~ 행복해 ")
				.build();
		
		
		list.add(post1);
		list.add(post2);
		list.add(post3);
		list.add(post4);
		list.add(post5);
		list.add(post6);
		repo.saveAllAndFlush(list);
	}

	public Page<WeddingPost> getPostList(Pageable pageable) {

		// TODO Delete MockData
		if (repo.count() == 0) {
			saveMockData();
		}

		return repo.findAll(pageable);
	}
	
	public WeddingPost getPost(Long id) {
		Assert.notNull(id, "ID is required...");		
		return repo.findById(id)
				.orElseThrow(() -> new BizException("THE_POST_IS_NOT_FOUND", "The Post is not Found:" + id, new Property("id", id)));
	}


	@Transactional
	public void putPost(CongratsDatIn input) {
		Assert.notNull(input.getName(), "Name is required...");
		Assert.notNull(input.getPassword(), "Password is required...");
		Assert.notNull(input.getContent(), "Content is required...");				
		
		WeddingPost post = repo.findById(input.getId())
				.orElseThrow(() -> new BizException("THE_POST_IS_NOT_FOUND", "The Post is not Found:" + input.getId(), new Property("id", input.getId())));
		
		if(input.getName() != post.getName() && repo.existsByName(input.getName())) {
			throw new BizException("THE_NAME_IS_ALREADY_EXIST", "The Name is already exist:"+ input.getName(), new Property("id", input.getName()));
		}
				
		post.setName(input.getName());
		post.setPassword(input.getPassword());
		post.setContent(input.getContent());
		repo.save(post);
	}

	@Transactional
	public void putNewPost(CongratsDatIn input) {
		Assert.notNull(input.getName(), "Name is required...");
		Assert.notNull(input.getPassword(), "Password is required...");
		Assert.notNull(input.getContent(), "Content is required...");
		
		if(repo.existsByName(input.getName())) {
			throw new BizException("THE_NAME_IS_ALREADY_EXIST", "The Name is already exist:"+ input.getName(), new Property("id", input.getName()));
		}
		
		WeddingPost post = WeddingPost.builder()//
				.name(input.getName())
				.password(input.getPassword())
				.content(input.getContent())
				.build();
		repo.save(post);
	}
	
	public void deletePost(Long id) {
		Assert.notNull(id, "ID is required...");

		WeddingPost post = repo.findById(id)
				.orElseThrow(() -> new BizException("THE_POST_IS_NOT_FOUND", "The Post is not Found:" + id, new Property("id", id)));
		repo.delete(post);
	}

}
