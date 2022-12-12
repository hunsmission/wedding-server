package com.wedding.mkmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = { "com.wedding" })
@EnableAsync
public class WeddingApplication {
		
	public static void main(String[] args) {			   	    	   	    	    	    
		SpringApplication application = new SpringApplication(WeddingApplication.class);
	    application.run(args);		
	}
}
