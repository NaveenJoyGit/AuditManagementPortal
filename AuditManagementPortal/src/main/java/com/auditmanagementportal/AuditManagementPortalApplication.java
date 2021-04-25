package com.auditmanagementportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuditManagementPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditManagementPortalApplication.class, args);
	}

}
