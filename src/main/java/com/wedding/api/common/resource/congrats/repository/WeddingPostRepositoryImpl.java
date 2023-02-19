package com.wedding.api.common.resource.congrats.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class WeddingPostRepositoryImpl extends QuerydslRepositorySupport {

	public WeddingPostRepositoryImpl() {
		super(WeddingPost.class);	
	}

}
