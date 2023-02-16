package com.wedding.api.common.resource.congrats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface WeddingPostRepository extends JpaRepository<WeddingPost, Long>, QuerydslPredicateExecutor<WeddingPost>{ 

}