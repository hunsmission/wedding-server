package com.wedding.mkmn.repository.sensor;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class SensorHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String deviceId;
	private String deviceType;
	private String deviceSwVersion;
	private Instant lastCommTime;
	private Long cumulNum;
	@CreatedDate
	@Column(updatable = false)
	private Instant createdAt;
	@LastModifiedDate
	private Instant updatedAt;
	
}
