package com.auditmanagementportal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auditmanagementportal.cllient.CheckListClient;
import com.auditmanagementportal.model.AuditType;
import com.auditmanagementportal.model.Questions;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CheckListService {

	@Autowired
	private CheckListClient checkListClient;

	public List<Questions> reliable(String token, String type) {
		log.info("inside fallback method----------------------");
//		AuditType at = new AuditType("Internal");
		Questions q1 = new Questions("q1");
		Questions q2 = new Questions("q2");
		List<Questions> qList = new ArrayList<>();
		qList.add(q1);
		qList.add(q2);
//		at.setQuestions(qList);
//		return "Internal Error please check if your services are running";
		return qList;
	}
	
	
	public AuditType getAuditType(String token, String type) {
		return checkListClient.getAuditType(token, type);
	}

	@HystrixCommand(fallbackMethod = "reliable")
	public List<Questions> getQuestions(String token, String type) {
		AuditType auditType = checkListClient.getAuditType(token, type);
//		AuditType auditType = getAuditType(token, type);
		List<Questions> ques2 = auditType.getQuestions();
		for (Long i = 1l; i <= 5l; i++) {
			ques2.get((int) (i - 1)).setId(i);
		}
		log.info("------------------inside service method-----------------");
		log.info(ques2.get(0).getQuestion());
		return ques2;
}

}
