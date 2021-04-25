package com.auditmanagementportal.cllient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auditmanagementportal.model.JwtResponse;
import com.auditmanagementportal.model.User;

@FeignClient(url = "http://host.docker.internal:9090/api/AuditAuthentication", name = "TOKEN")
public interface JwtTokenClient {

	@PostMapping("/authenticate")
	public JwtResponse getJwt(@RequestBody User user);
	
}
