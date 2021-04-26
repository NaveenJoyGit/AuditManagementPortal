package com.auditmanagementportal.fallback;

import org.springframework.stereotype.Component;

import com.auditmanagementportal.cllient.CheckListClient;
import com.auditmanagementportal.model.AuditType;

@Component
public class CheckListFallback implements CheckListClient {

	@Override
	public AuditType getAuditType(String authorizationHeader, String type) {
		return new AuditType();
	}

}
