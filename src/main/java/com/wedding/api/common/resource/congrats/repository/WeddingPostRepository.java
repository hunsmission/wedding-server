package com.wedding.api.common.resource.congrats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WeddingPostRepository extends JpaRepository<WeddingPost, Long>, QuerydslPredicateExecutor<WeddingPost>{ 
	boolean existsByName(String name);
}
