package com.wedding.mkmn.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SerialConst {

	/** Terminal Mode **/
	public String MODE_WRITE = "MODE_WRITE";
	public String MODE_READ = "MODE_READ";
	public String MODE_OTA = "MODE_OTA";
	
	/** Serial Communication **/
	public String DELIMITER = "*";
	public int LEN_STRING_BYTE = 2;

	public byte STX = 0x02;
	public byte ETX = 0x03;

	public int LEN_STX = 1;
	public int LEN_LENGTH = 2;
	public int LEN_CRC = 2;
	public int LEN_ETX = 1;
	
	/** Receiver Command  **/
	public String CMD_START;
	public String CMD_STOP;
	public String CMD_CONNECT;
	public String CMD_DISCONNECT;
	public String CMD_INVALID;
	public String CMD_HEARTBEAT;

	/** Sensor Command  **/
	public String ACK_OK;
	public String ACK_NG;
	public String CMD_CREATE;
	public String CMD_INSTALLED;
	public String CMD_DATA;
	public String CMD_ACK;
	public String CMD_NACK;

	/** Sensor Control  **/
	public String CTL_CMD;
	public String CTL_PRE;

	/** Type  **/
	public String TYPE_NO_ENCRYPT;
	public String TYPE_ENCRYPT;	
	
	/** OTA **/
	public String OTA_DFU;
	public String OTA_DFUSERVER;	
	public String OTA_DFURESP;
	public String OTA_OBJSELECT;
	public String OTA_OBJCREATE;
	public String OTA_DFUWRITE;
	public String OTA_DFUWRITEDATA;
	public String OTA_OBJEXECUTE;
	
	/** Mode Command **/
	public String CMD_MODE_OTA;
	public String CMD_MODE_RECV;
	
	@Value("#{'${command.receiver.start}'.trim()}")		
	public void setCMD_START(String CMD_START) {		
		this.CMD_START = CMD_START;
	}
	
	@Value("#{'${command.receiver.stop}'.trim()}")			
	public void setCMD_STOP(String CMD_STOP) {
		this.CMD_STOP = CMD_STOP;
	}

	@Value("#{'${command.receiver.connect}'.trim()}")			
	public void setCMD_CONNECT(String CMD_CONNECT) {
		this.CMD_CONNECT = CMD_CONNECT;
	}

	@Value("#{'${command.receiver.disconnect}'.trim()}")			
	public void setCMD_DISCONNECT(String CMD_DISCONNECT) {
		this.CMD_DISCONNECT = CMD_DISCONNECT;
	}

	@Value("#{'${command.receiver.invalid}'.trim()}")			
	public void setCMD_INVALID(String CMD_INVALID) {
		this.CMD_INVALID = CMD_INVALID;
	}

	@Value("#{'${command.receiver.heartbeat}'.trim()}")			
	public void setCMD_HEARTBEAT(String CMD_HEARTBEAT) {
		this.CMD_HEARTBEAT = CMD_HEARTBEAT;
	}

	@Value("#{'${ack.sensor.ok}'.trim()}")			
	public void setACK_OK(String ACK_OK) {
		this.ACK_OK = ACK_OK;
	}

	@Value("#{'${ack.sensor.ng}'.trim()}")	
	public void setACK_NG(String ACK_NG) {
		this.ACK_NG = ACK_NG;
	}

	@Value("#{'${command.sensor.create}'.trim()}")			
	public void setCMD_CREATE(String CMD_CREATE) {
		this.CMD_CREATE = CMD_CREATE;
	}

	@Value("#{'${command.sensor.installed}'.trim()}")				
	public void setCMD_INSTALLED(String CMD_INSTALLED) {
		this.CMD_INSTALLED = CMD_INSTALLED;
	}

	@Value("#{'${command.sensor.data}'.trim()}")			
	public void setCMD_DATA(String CMD_DATA) {
		this.CMD_DATA = CMD_DATA;
	}

	@Value("#{'${command.sensor.ack}'.trim()}")			
	public void setCMD_ACK(String CMD_ACK) {
		this.CMD_ACK = CMD_ACK;
	}

	@Value("#{'${command.sensor.nack}'.trim()}")			
	public void setCMD_NACK(String CMD_NACK) {
		this.CMD_NACK = CMD_NACK;
	}

	@Value("#{'${control.sensor.command}'.trim()}")			
	public void setCTL_CMD(String CTL_CMD) {
		this.CTL_CMD = CTL_CMD;
	}

	@Value("#{'${control.sensor.pre}'.trim()}")		
	public void setCTL_PRE(String CTL_PRE) {
		this.CTL_PRE = CTL_PRE;
	}

	@Value("#{'${type.no.encrypt}'.trim()}")			
	public void setTYPE_NO_ENCRYPT(String TYPE_NO_ENCRYPT) {
		this.TYPE_NO_ENCRYPT = TYPE_NO_ENCRYPT;
	}

	@Value("#{'${type.encrypt}'.trim()}")			
	public void setTYPE_ENCRYPT(String TYPE_ENCRYPT) {
		this.TYPE_ENCRYPT = TYPE_ENCRYPT;
	}

	@Value("#{'${ota.dfu}'.trim()}")			
	public void setOTA_DFU(String OTA_DFU) {
		this.OTA_DFU = OTA_DFU;
	}

	@Value("#{'${ota.dfuserver}'.trim()}")			
	public void setOTA_DFUSERVER(String OTA_DFUSERVER) {
		this.OTA_DFUSERVER = OTA_DFUSERVER;
	}

	@Value("#{'${ota.dfuresp}'.trim()}")			
	public void setOTA_DFURESP(String OTA_DFURESP) {
		this.OTA_DFURESP = OTA_DFURESP;
	}
	
	@Value("#{'${ota.objselect}'.trim()}")			
	public void setOTA_OBJSELECT(String OTA_OBJSELECT) {
		this.OTA_OBJSELECT = OTA_OBJSELECT;
	}

	@Value("#{'${ota.objcreate}'.trim()}")			
	public void setOTA_OBJCREATE(String OTA_OBJCREATE) {
		this.OTA_OBJCREATE = OTA_OBJCREATE;
	}

	@Value("#{'${ota.dfuwrite}'.trim()}")			
	public void setOTA_DFUWRITE(String OTA_DFUWRITE) {
		this.OTA_DFUWRITE = OTA_DFUWRITE;
	}
	
	@Value("#{'${ota.dfuwritedata}'.trim()}")			
	public void setOTA_DFUWRITEDATA(String OTA_DFUWRITEDATA) {
		this.OTA_DFUWRITEDATA = OTA_DFUWRITEDATA;
	}

	@Value("#{'${ota.objexecute}'.trim()}")			
	public void setOTA_OBJEXECUTE(String OTA_OBJEXECUTE) {
		this.OTA_OBJEXECUTE = OTA_OBJEXECUTE;
	}
	
	@Value("#{'${command.mode.ota}'.trim()}")			
	public void setCMD_MODE_OTA(String CMD_MODE_OTA) {
		this.CMD_MODE_OTA = CMD_MODE_OTA;
	}

	@Value("#{'${command.mode.recv}'.trim()}")			
	public void setCMD_MODE_RECV(String CMD_MODE_RECV) {
		this.CMD_MODE_RECV = CMD_MODE_RECV;
	}

}
