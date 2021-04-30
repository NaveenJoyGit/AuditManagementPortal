package com.auditmanagementportal.cllient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auditmanagementportal.model.AuditDetails;
import com.auditmanagementportal.model.AuditResponse;

@FeignClient(url = "http://3.134.88.8:9090/api/AuditSeverity", name = "RESPONSE")
public interface AuditResponseClient {

	@PostMapping("/ProjectExecutionStatus")
	public AuditResponse getResponse(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody AuditDetails auditDetails);
	
}
