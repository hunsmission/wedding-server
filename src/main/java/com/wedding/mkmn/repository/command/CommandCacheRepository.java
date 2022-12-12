package com.wedding.mkmn.repository.command;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandCacheRepository extends JpaRepository<CommandCache, Long>, QuerydslPredicateExecutor<CommandCache>{

}
