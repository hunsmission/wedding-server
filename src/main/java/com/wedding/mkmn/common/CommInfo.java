package com.wedding.mkmn.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Component
public class CommInfo {
	
	@Value("#{'${terminal.serial.port}'.trim()}")
	private String serialPort;
	
	@Value("#{'${terminal.mms.url}'.trim()}")		
	private String serverUrl;
	
	@Value("#{'${terminal.mms.timeout}'.trim()}")	
	private int serverWatchdog = 0;
	
	@Value("#{'${terminal.auth.url}'.trim()}")	
	private String regServerUrl;
	
	@Value("#{'${terminal.gmt}'.trim()}")		
	private String timeZone;
	
	@Value("#{'${terminal.encrypt}'.trim()}")		
	private boolean enableAES128;	
			
}
