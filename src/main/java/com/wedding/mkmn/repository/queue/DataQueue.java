package com.wedding.mkmn.repository.queue;

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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class DataQueue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String deviceId;	
	private String deviceType;
	private String deviceSwVersion;
	private String deviceTime;
	private String dataType;
	private String data;
		
	@CreatedDate
	@Column(updatable = false)
	private Instant createdAt;
}
