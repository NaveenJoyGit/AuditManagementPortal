package com.auditmanagementportal.controller;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.auditmanagementportal.cllient.AuditResponseClient;
import com.auditmanagementportal.cllient.CheckListClient;
import com.auditmanagementportal.cllient.JwtTokenClient;
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
@SessionAttributes("user")
public class ViewController {
	
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
	
	@Autowired
	private AuditResponseClient auditResponse;
	
	
	public AuditResponse getResponse(String token, AuditDetails auditDetails) {
		return auditResponse.getResponse(token, auditDetails);
	}
		
	public JwtResponse jwtResponse = new JwtResponse();
	
	String jwtToken = "sample";
	
	String token = "Bearer " + jwtToken;
	
	@GetMapping("/login")
	public String loginFormm(Model theModel) {
		User user = new User();
		theModel.addAttribute("user",user);
		return "login";
	}
	
	@PostMapping("/loginValidate")
	public String getUser(@ModelAttribute("user") User user, Model model) {
		jwtResponse = getJwtResponse(user);
		
		jwtToken = jwtResponse.getAccessToken();
		log.info(jwtResponse.getAccessToken());
				
		model.addAttribute("Internal", "Internal");
		model.addAttribute("SOX", "SOX");
		model.addAttribute("username", user.getUserName());
		model.addAttribute("user", user);
		return "webportal";
		
	}
	
	@PostMapping("/redirect")
	public String getHomePage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("Internal", "Internal");
		model.addAttribute("SOX", "SOX");
//		model.addAttribute("username", user.getUserName());
		model.addAttribute("user", user);
		return "webportal";	
	}
	
	
	@RequestMapping("/showForm/{type}")
	public String showForm(@PathVariable String type, Model theModel) {
		
		String token2 = "Bearer " + jwtResponse.getAccessToken();
		
		log.info(token);
		log.info("printing jwtObject");
		log.info(jwtResponse.getAccessToken());

		
		AuditType auditType = getAuditType(token2, type);
		
		List<Questions> ques2 = auditType.getQuestions(); 
		for(Long i = 1l; i <= 5l; i++) {
			ques2.get((int) (i-1)).setId(i);			
		}
		
		
		List<String> q = auditType.getQuestions().stream().map(x -> x.getQuestion()).collect(Collectors.toList());
		
		
		FormDetails details = new FormDetails();
		
		
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
		
//		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		
//		headers.add("Authorization", token2);
//		
//		RestTemplate rt = new RestTemplate();
//		HttpEntity<AuditDetails> request = new HttpEntity<AuditDetails>(auditDetails, headers);
//		
//		
//		AuditResponse aresponse = rt.postForObject("http://host.docker.internal:9090/api/AuditSeverity/ProjectExecutionStatus",
//				request, AuditResponse.class);
		
		AuditResponse aresponse = getResponse(token2, auditDetails);
		
		
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
	
	
	/*
	 * RestTemplate Calls
	 */
	
//	@PostMapping("/loginValidate")
//	public String getUser(@ModelAttribute("user") User user, Model model) {
//		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		
//		RestTemplate rt = new RestTemplate();
//		HttpEntity<User> request = new HttpEntity<User>(user, headers);
//		
//		jwtResponse = rt.postForObject("http://localhost:7001/authenticate",
//				request, JwtResponse.class);
//		
//		jwtToken = jwtResponse.getAccessToken();
//		log.info(jwtResponse.getAccessToken());
//		
//		
//		model.addAttribute("Internal", "Internal");
//		model.addAttribute("SOX", "SOX");
//		model.addAttribute("username", user.getUserName());
//		return "webportal";
//		
//	}
	
//	@RequestMapping("/showForm/{type}")
//	public String showForm(@PathVariable String type, Model theModel) {
//		String url = "http://host.docker.internal:9090/api/AuditCheckList/AuditCheckListQuestions/" + type;
		
		// create a student object
//		String url = "http://localhost:8083/AuditCheckListQuestions/" + type;
//		String jwtToken = jwtResponse.getAccessToken();
//		
//		String token = "Bearer " + jwtToken;
		
//		String token2 = "Bearer " + jwtResponse.getAccessToken();
//		
//		log.info(token);
//		log.info("printing jwtObject");
//		log.info(jwtResponse.getAccessToken());
		
//		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//		headers.add("Authorization", token2);	
//		
//		
//		HttpEntity<String> request = new HttpEntity<>(headers);
//		
//		RestTemplate rt = new RestTemplate();	
//		
//		
//		ResponseEntity<AuditType> rs = rt.exchange(
//			    url, HttpMethod.GET, request, AuditType.class);
		
		
//		RestTemplate rt = new RestTemplate();	
//		
//		ResponseEntity<AuditType> rs = rt.getForEntity(url, AuditType.class);
//		AuditType auditType = rs.getBody();
		
//		AuditType auditType = getAuditType(token2, type);
		
//		List<Questions> ques2 = auditType.getQuestions(); 
//		for(Long i = 1l; i <= 5l; i++) {
//			ques2.get((int) (i-1)).setId(i);			
//		}
//		
//		
//		List<String> q = auditType.getQuestions().stream().map(x -> x.getQuestion()).collect(Collectors.toList());
//		
//		
//		FormDetails details = new FormDetails();
//		
//		int count;
		
		
		
		// add student object to the model
//		theModel.addAttribute("details", details);
//		theModel.addAttribute("questions", q);
//		theModel.addAttribute("type", auditType.getAuditType());
//		theModel.addAttribute("ques2", ques2);
//		
//		return "questions2";
//	}

	
	
}
