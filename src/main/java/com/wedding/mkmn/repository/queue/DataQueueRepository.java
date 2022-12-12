package com.wedding.mkmn.repository.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataQueueRepository extends JpaRepository<DataQueue, Long>, QuerydslPredicateExecutor<DataQueue>{

}
