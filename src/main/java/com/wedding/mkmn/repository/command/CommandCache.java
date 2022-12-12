package com.wedding.mkmn.repository.command;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CommandCache {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String deviceId;	
	private String command;
	private int indexNo;
	private String data;	
	
	public CommandCache(String deviceId, String command, int indexNo, String data) {
		this.deviceId = deviceId;
		this.command = command;
		this.indexNo = indexNo;
		this.data = data;
	}
}
