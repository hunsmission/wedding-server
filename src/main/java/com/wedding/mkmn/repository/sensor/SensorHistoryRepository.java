package com.wedding.mkmn.repository.sensor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorHistoryRepository extends JpaRepository<SensorHistory, Long>, QuerydslPredicateExecutor<SensorHistory> {
	
}
