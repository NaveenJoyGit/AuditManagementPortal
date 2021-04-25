package com.auditmanagementportal.controller;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.auditmanagementportal.model.AuditDetails;
import com.auditmanagementportal.model.AuditResponse;
import com.auditmanagementportal.model.AuditType;
import com.auditmanagementportal.model.FormDetails;
import com.auditmanagementportal.model.JwtResponse;
import com.auditmanagementportal.model.Project;
import com.auditmanagementportal.model.Questions;
import com.auditmanagementportal.model.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ViewController {
		
	public JwtResponse jwtResponse = new JwtResponse();
	
	String jwtToken = "sample";
	
	String token = "Bearer " + jwtToken;
	
//	RestTemplate rt = new RestTemplate();	
//	
//	ResponseEntity<AuditType> rs = rt.getForEntity("http://localhost:8083/AuditCheckListQuestions/Internal", AuditType.class);
//	AuditType audiitType = rs.getBody();
	
	@GetMapping("/login")
	public String loginFormm(Model theModel) {
		User user = new User();
		theModel.addAttribute("user",user);
		return "login";
	}
	
	@PostMapping("/loginValidate")
	public String getUser(@ModelAttribute("user") User user, Model model) {
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		RestTemplate rt = new RestTemplate();
		HttpEntity<User> request = new HttpEntity<User>(user, headers);
		
		jwtResponse = rt.postForObject("http://localhost:7001/authenticate",
				request, JwtResponse.class);
		jwtToken = jwtResponse.getAccessToken();
		log.info(jwtResponse.getAccessToken());
		
//		return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
		
		model.addAttribute("Internal", "Internal");
		model.addAttribute("SOX", "SOX");
		model.addAttribute("username", user.getUserName());
		return "webportal";
		
	}


	
//	@RequestMapping(value = "/AuditTypes", method = RequestMethod.GET)
//	public String getAuditTypes(ModelMap model) {
//		model.put("Internal", "Internal");
//		model.put("SOX", "SOX");
//		return "webportal";
//	}
	
//	@GetMapping("/Questions/{type}")
//	public String getQuestions(@PathVariable String type, ModelMap model) {
//		String url = "http://host.docker.internal:9090/api/AuditCheckList" + type;
//		String url = "http://localhost:8083/AuditCheckListQuestions/" + type;
//		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//		headers.add("Authorization: ", "Bearer " + jwtToken);	
//		
//		HttpEntity<AuditDetails> request = new HttpEntity<>(headers);
//		
//		RestTemplate rt = new RestTemplate();	
//		
//		ResponseEntity<AuditType> rs = rt.exchange(
//			    url, HttpMethod.GET, request, AuditType.class);
//		
//		model.put("audit", audiitType);
//		return "questions";
//	}
	
	@RequestMapping("/showForm/{type}")
	public String showForm(@PathVariable String type, Model theModel) {
		String url = "http://host.docker.internal:9090/api/AuditCheckList/AuditCheckListQuestions/" + type;
		
		// create a student object
//		String url = "http://localhost:8083/AuditCheckListQuestions/" + type;
//		String jwtToken = jwtResponse.getAccessToken();
//		
//		String token = "Bearer " + jwtToken;
		
		String token2 = "Bearer " + jwtResponse.getAccessToken();
		
		log.info(token);
		log.info("printing jwtObject");
		log.info(jwtResponse.getAccessToken());
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.add("Authorization", token2);	
		
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		
		RestTemplate rt = new RestTemplate();	
		
		
		ResponseEntity<AuditType> rs = rt.exchange(
			    url, HttpMethod.GET, request, AuditType.class);
		
		
//		RestTemplate rt = new RestTemplate();	
//		
//		ResponseEntity<AuditType> rs = rt.getForEntity(url, AuditType.class);
		AuditType auditType = rs.getBody();
		
		List<Questions> ques2 = auditType.getQuestions(); 
		for(Long i = 1l; i <= 5l; i++) {
			ques2.get((int) (i-1)).setId(i);			
		}
		
		
		List<String> q = auditType.getQuestions().stream().map(x -> x.getQuestion()).collect(Collectors.toList());
		
		
		FormDetails details = new FormDetails();
		
		int count;
		
		
		
		// add student object to the model
		theModel.addAttribute("details", details);
		theModel.addAttribute("questions", q);
		theModel.addAttribute("type", auditType.getAuditType());
		theModel.addAttribute("ques2", ques2);
		
		return "questions2";
	}
	
	@RequestMapping("/processForm")
	public String processForm(@ModelAttribute("details") FormDetails details, Model theModel) {
		
		// log the input data
//		System.out.println("theStudent: " + theStudent.getFirstName()
//							+ " " + theStudent.getLastName());
		
		int count = 0;
		if(details.getQ1().equals("no")) count++;
		if(details.getQ2().equals("no")) count++;
		if(details.getQ3().equals("no")) count++;
		if(details.getQ4().equals("no")) count++;
		if(details.getQ5().equals("no")) count++;
		
		String token2 = "Bearer " + jwtResponse.getAccessToken();
		
		Project project = new Project(details.getName(), details.getManager(), details.getOwner());
		AuditDetails auditDetails = new AuditDetails(details.getType(), count, details.getDate(), project);
		auditDetails.setToken(token2);
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		headers.add("Authorization", token2);
		
		RestTemplate rt = new RestTemplate();
		HttpEntity<AuditDetails> request = new HttpEntity<AuditDetails>(auditDetails, headers);
		
		
		AuditResponse aresponse = rt.postForObject("http://host.docker.internal:9090/api/AuditSeverity/ProjectExecutionStatus",
				request, AuditResponse.class);
		
		theModel.addAttribute("status", aresponse);
//		System.out.println(count);
		
		return "success";
	}
	
	
	@RequestMapping(value = "/severityservice", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<AuditResponse> getSeverity(@RequestBody AuditDetails auditDetails, ModelMap model) {
//		System.out.println(project.getName());
//		return "severity";
		System.out.println(auditDetails.getCount());
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		RestTemplate rt = new RestTemplate();
		HttpEntity<AuditDetails> request = new HttpEntity<AuditDetails>(auditDetails, headers);
		
		AuditResponse aresponse = rt.postForObject("http://localhost:8082/ProjectExecutionStatus",
				request, AuditResponse.class);
		
//		model.put("status", aresponse.getStatus());
		
		return new ResponseEntity<>(aresponse, HttpStatus.CREATED);
	}
	

	
	
}
