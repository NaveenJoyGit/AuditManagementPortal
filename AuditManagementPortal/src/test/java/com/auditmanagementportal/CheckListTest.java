package com.auditmanagementportal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.auditmanagementportal.model.AuditType;

@SpringBootTest
class CheckListTest {

	@Test
	void test() {
		RestTemplate rt = new RestTemplate();	
		
		ResponseEntity<AuditType> rs = rt.getForEntity("http://localhost:8083/AuditCheckListQuestions/Internal", AuditType.class);
		AuditType audiitType = rs.getBody();
		
		assertEquals("Internal", audiitType.getAuditType());
	}

}
