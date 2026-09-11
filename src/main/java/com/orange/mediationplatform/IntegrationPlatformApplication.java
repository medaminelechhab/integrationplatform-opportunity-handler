package com.MyProject.mediationplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.MyProject")
@EnableAutoConfiguration
public class IntegrationPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationPlatformApplication.class, args);
	}

}
