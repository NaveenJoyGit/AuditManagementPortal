package com.auditmanagementportal;

import static org.junit.jupiter.api.Assertions.*;

import java.net.http.HttpHeaders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.auditmanagementportal.model.AuditDetails;
import com.auditmanagementportal.model.AuditResponse;
import com.auditmanagementportal.model.Project;

@SpringBootTest
class SeverityTest {

	@Test
	void test() {
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Project p = new Project("abc", "def", "xyz");
		AuditDetails ad = new AuditDetails("Internal", 2, "21/04/2021", p);
		RestTemplate rt = new RestTemplate();
		HttpEntity<AuditDetails> request = new HttpEntity<AuditDetails>(ad, headers);
		
		AuditResponse aresponse = rt.postForObject("http://localhost:8082/ProjectExecutionStatus",
				request, AuditResponse.class);
		
		assertNotNull(aresponse);
		assertEquals("abc", aresponse.getDetail().getProject().getName());
	}

}
