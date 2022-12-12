package com.wedding.framework.util;

import java.net.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerUtils {
	
	@Value("${server.port:0}")
	private static int httpPort;

	public static String getName() {
		String name;
		try {
			name = InetAddress.getLocalHost().getHostName() + ":" + httpPort;
		} catch (UnknownHostException e) {
			name = httpPort + "";
		}
		return name;
	}

}
