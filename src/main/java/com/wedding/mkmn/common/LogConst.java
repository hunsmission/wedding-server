package com.wedding.mkmn.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogConst {			
    // ***
    // Log
    // ***
    public String LOG_SERIAL_CONNECTION;
    public String LOG_HTTP_CONNECTION;
    public String LOG_DB_CONNECTION;
    
    public String LOG_SEND_RECEIVER;    
    public String LOG_SEND_SENSOR;
    public String LOG_SEND_MMS;
    public String LOG_SEND_SERENITY;
    public String LOG_RECV_RECEIVER;
    public String LOG_RECV_SENSOR;
    public String LOG_RECV_MMS;
    public String LOG_RECV_SERENITY;
    
    public String LOG_MMS_HEARTBEAT;
    public String LOG_RECEIVER_HEARTBEAT;
    
    public String LOG_SERIAL_WATCHDOG;
    
    public String LOG_ENQUEUE_DATA;
    public String LOG_DEQUEUE_DATA;
    
    public String LOG_CALL_API;
    public String LOG_TIME_SYNC;
    
    public String LOG_OTA_RECV_SENSOR;
    public String LOG_OTA_RECV_RECEIVER;
    public String LOG_OTA_SEND_SENSOR;
    public String LOG_OTA_SEND_RECEIVER;
    
    @Value("#{'${log.connection.serial}'.trim()}")	
	public void setLOG_SERIAL_CONNECTION(String LOG_SERIAL_CONNECTION) {
		this.LOG_SERIAL_CONNECTION = LOG_SERIAL_CONNECTION;
	}
    
    @Value("#{'${log.connection.http}'.trim()}")    
	public void setLOG_HTTP_CONNECTION(String LOG_HTTP_CONNECTION) {
    	this.LOG_HTTP_CONNECTION = LOG_HTTP_CONNECTION;
	}
    
    @Value("#{'${log.connection.db}'.trim()}")    
	public void setLOG_DB_CONNECTION(String LOG_DB_CONNECTION) {
    	this.LOG_DB_CONNECTION = LOG_DB_CONNECTION;
	}
    
    @Value("#{'${log.send.receiver}'.trim()}")    
	public void setLOG_SEND_RECEIVER(String LOG_SEND_RECEIVER) {
		this.LOG_SEND_RECEIVER = LOG_SEND_RECEIVER;
	}
    
    @Value("#{'${log.send.sensor}'.trim()}")    
	public void setLOG_SEND_SENSOR(String LOG_SEND_SENSOR) {
    	this.LOG_SEND_SENSOR = LOG_SEND_SENSOR;
	}
    
    @Value("#{'${log.send.mms}'.trim()}")    
	public void setLOG_SEND_MMS(String LOG_SEND_MMS) {
    	this.LOG_SEND_MMS = LOG_SEND_MMS;
	}
    
    @Value("#{'${log.send.serenity}'.trim()}")    
	public void setLOG_SEND_SERENITY(String LOG_SEND_SERENITY) {
    	this.LOG_SEND_SERENITY = LOG_SEND_SERENITY;
	}
    
    @Value("#{'${log.receive.receiver}'.trim()}")    
	public void setLOG_RECV_RECEIVER(String LOG_RECV_RECEIVER) {
    	this.LOG_RECV_RECEIVER = LOG_RECV_RECEIVER;
	}
    
    @Value("#{'${log.receive.sensor}'.trim()}")    
	public void setLOG_RECV_SENSOR(String LOG_RECV_SENSOR) {
    	this.LOG_RECV_SENSOR = LOG_RECV_SENSOR;
	}
    
    @Value("#{'${log.receive.mms}'.trim()}")    
	public void setLOG_RECV_MMS(String LOG_RECV_MMS) {
    	this.LOG_RECV_MMS = LOG_RECV_MMS;
	}
    
    @Value("#{'${log.receive.serenity}'.trim()}")    
	public void setLOG_RECV_SERENITY(String LOG_RECV_SERENITY) {
    	this.LOG_RECV_SERENITY = LOG_RECV_SERENITY;
	}
    
    @Value("#{'${log.heartbeat.mms}'.trim()}")    
	public void setLOG_MMS_HEARTBEAT(String LOG_MMS_HEARTBEAT) {
    	this.LOG_MMS_HEARTBEAT = LOG_MMS_HEARTBEAT;
	}
    
    @Value("#{'${log.heartbeat.receiver}'.trim()}")    
	public void setLOG_RECEIVER_HEARTBEAT(String LOG_RECEIVER_HEARTBEAT) {
    	this.LOG_RECEIVER_HEARTBEAT = LOG_RECEIVER_HEARTBEAT;
	}
    
    @Value("#{'${log.watchdog.serial}'.trim()}")    
	public void setLOG_SERIAL_WATCHDOG(String LOG_SERIAL_WATCHDOG) {
    	this.LOG_SERIAL_WATCHDOG = LOG_SERIAL_WATCHDOG;
	}
    
    @Value("#{'${log.enqueue.data}'.trim()}")    
	public void setLOG_ENQUEUE_DATA(String LOG_ENQUEUE_DATA) {
    	this.LOG_ENQUEUE_DATA = LOG_ENQUEUE_DATA;
	}
    
    @Value("#{'${log.dequeue.data}'.trim()}")    
	public void setLOG_DEQUEUE_DATA(String LOG_DEQUEUE_DATA) {
    	this.LOG_DEQUEUE_DATA = LOG_DEQUEUE_DATA;
	}
    
    @Value("#{'${log.call.api}'.trim()}")    
	public void setLOG_CALL_API(String LOG_CALL_API) {
		this.LOG_CALL_API = LOG_CALL_API;
	}
    
    @Value("#{'${log.sync.time}'.trim()}")    
	public void setLOG_TIME_SYNC(String LOG_TIME_SYNC) {
    	this.LOG_TIME_SYNC = LOG_TIME_SYNC;
	}
    
    @Value("#{'${log.ota.receive.sensor}'.trim()}")    
    public void setLOG_OTA_RECV_SENSOR(String lOG_OTA_RECV_SENSOR) {
		LOG_OTA_RECV_SENSOR = lOG_OTA_RECV_SENSOR;
	}

    @Value("#{'${log.ota.receive.receiver}'.trim()}")    
	public void setLOG_OTA_RECV_RECEIVER(String lOG_OTA_RECV_RECEIVER) {
		LOG_OTA_RECV_RECEIVER = lOG_OTA_RECV_RECEIVER;
	}

    @Value("#{'${log.ota.send.sensor}'.trim()}")    
	public void setLOG_OTA_SEND_SENSOR(String lOG_OTA_SEND_SENSOR) {
		LOG_OTA_SEND_SENSOR = lOG_OTA_SEND_SENSOR;
	}

    @Value("#{'${log.ota.send.receiver}'.trim()}")    
	public void setLOG_OTA_SEND_RECEIVER(String lOG_OTA_SEND_RECEIVER) {
		LOG_OTA_SEND_RECEIVER = lOG_OTA_SEND_RECEIVER;
	}

}
