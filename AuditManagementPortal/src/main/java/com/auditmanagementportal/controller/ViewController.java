package com.auditmanagementportal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.auditmanagementportal.model.AuditType;
import com.auditmanagementportal.model.Project;

@Controller
public class ViewController {
		
	RestTemplate rt = new RestTemplate();	
	
	ResponseEntity<AuditType> rs = rt.getForEntity("http://localhost:8083/AuditCheckListQuestions/Internal", AuditType.class);
	AuditType audiitType = rs.getBody();

	@RequestMapping(value = "/AuditTypes", method = RequestMethod.GET)
	public String getAuditTypes(ModelMap model) {
		model.put("Internal", "Internal");
		model.put("SOX", "SOX");
		return "webportal";
	}
	
	@GetMapping("/Questions/{type}")
	public String getQuestions(@PathVariable String type, ModelMap model) {
		String url = "http://localhost:8083/AuditCheckListQuestions/" + type;
		RestTemplate rt = new RestTemplate();	
		
		ResponseEntity<AuditType> rs = rt.getForEntity(url, AuditType.class);
		AuditType audiitType = rs.getBody();
		
		model.put("audit", audiitType);
		return "questions";
	}
	
	@RequestMapping(value = "/ProjectExecutionStatus", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Project getSeverity(@RequestBody Project project) {
		System.out.println(project.getName());
//		return "severity";
		return project;
	}
	
	
}
