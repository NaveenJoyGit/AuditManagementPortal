package com.auditmanagementportal.cllient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auditmanagementportal.model.AuditType;

@FeignClient(url = "http://3.134.88.8:9090/api/AuditCheckList", name = "CHECKLIST")
public interface CheckListClient {

	@GetMapping("/AuditCheckListQuestions/{type}")
	public AuditType getAuditType(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable String type);
	
}
