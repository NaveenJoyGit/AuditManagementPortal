package com.auditmanagementportal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.auditmanagementportal.cllient.CheckListClient;
import com.auditmanagementportal.cllient.JwtTokenClient;
import com.auditmanagementportal.model.AuditType;
import com.auditmanagementportal.model.JwtResponse;
import com.auditmanagementportal.model.User;

@SpringBootTest
class CheckListTest {
	
	@Autowired
	private JwtTokenClient jwtClient;
	
	
	public JwtResponse getJwtResponse(User user) {
		return jwtClient.getJwt(user);
	}
	
	@Autowired
	private CheckListClient checkListClient;
	
	public AuditType getAuditType(String token, String type) {
		return checkListClient.getAuditType(token, type);
	}
	
	@Test
	void testFeign() {
		User user1 = new User("niraj", "password");
		JwtResponse jwt = getJwtResponse(user1);
		assertNotNull(jwt);
		assertEquals("Bearer", jwt.getTokenType());
	}
	
	@Test
	void testCheckListFeign() {
		String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJEZW1vIEFwcCIsInN1YiI6Im5pcmFqIiwiaWF0IjoxNjE5MzU1MzI4LCJleHAiOjE2MjAwNjAzNjEsIlJvbGVzIjpbIlJPTEVfQURNSU4iXX0.FRLaXjdwkb5luEgOnWNPsqdWBYGfk5M9N8-IMvfmF3QhzNeowgLBcYsI48yiCqu-1ZVxf8cvaxd3whNSO3h5Nw";
		AuditType auditType = getAuditType(token, "Internal");
		assertEquals("Internal", auditType.getAuditType());
	}

	@Test
	void test() {
		RestTemplate rt = new RestTemplate();	
		
		ResponseEntity<AuditType> rs = rt.getForEntity("http://localhost:8083/AuditCheckListQuestions/Internal", AuditType.class);
		AuditType audiitType = rs.getBody();
		
		assertEquals("Internal", audiitType.getAuditType());
	}

}
