package com.auditmanagementportal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

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
import com.auditmanagementportal.service.CheckListService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes("user")
public class ViewController {

	String invalid;

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

	@Autowired
	private CheckListService checkListService;

	public JwtResponse jwtResponse = new JwtResponse();

	String jwtToken = "sample";

	String token = "Bearer " + jwtToken;

	@GetMapping("/login")
	public String loginFormm(Model theModel) {
		User user = new User();
		theModel.addAttribute("user", user);
		theModel.addAttribute("invalid", invalid);
		return "login";
	}

	@PostMapping("/loginValidate")
	public String getUser(@ModelAttribute("user") User user, Model model) {
		try {
			jwtResponse = getJwtResponse(user);
			invalid = null;
			model.addAttribute("Internal", "Internal");
			model.addAttribute("SOX", "SOX");
			model.addAttribute("username", user.getUserName());
			model.addAttribute("user", user);
			return "webportal";
		} catch (Exception e) {
			invalid = "invalid credentials";
			model.addAttribute("invalid", invalid);
			return "redirect:login";
		}


	}

	public String reliable(String type, Model model) {
		Questions q1 = new Questions(1l, "q1");
		Questions q2 = new Questions(2l, "q2");
		Questions q3 = new Questions(3l, "q3");
		Questions q4 = new Questions(4l, "q4");
		Questions q5 = new Questions(5l, "q5");

		List<Questions> qList = new ArrayList<>();
		qList.add(q1);
		qList.add(q2);
		qList.add(q3);
		qList.add(q4);
		qList.add(q5);
		FormDetails details = new FormDetails();

		model.addAttribute("details", details);
		model.addAttribute("type", type);
		model.addAttribute("ques2", qList);
		return "questions2";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}

	@PostMapping("/redirect")
	public String getHomePage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("Internal", "Internal");
		model.addAttribute("SOX", "SOX");
		model.addAttribute("user", user);
		return "webportal";
	}

	@RequestMapping("/showForm/{type}")
	@HystrixCommand(fallbackMethod = "reliable", commandProperties = {
	        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "80000")})
	public String showForm(@PathVariable String type, Model theModel) {

		String token2 = "Bearer " + jwtResponse.getAccessToken();
		log.info("inside show form controller------------------------------");
		log.info(token);
		log.info("printing jwtObject");
		log.info(jwtResponse.getAccessToken());

//		List<String> q = auditType.getQuestions().stream().map(x -> x.getQuestion()).collect(Collectors.toList());
		
		AuditType auditType = checkListService.getAuditType(token2, type);
		List<Questions> ques2 = checkListService.getQuestions(token2, type);

		FormDetails details = new FormDetails();

		theModel.addAttribute("details", details);
		theModel.addAttribute("type", auditType.getAuditType());
		theModel.addAttribute("ques2", ques2);

		return "questions2";
	}

	
	@RequestMapping("/processForm")
	@HystrixCommand(fallbackMethod = "statusFallback", commandProperties = {
	        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "80000"),
	        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
	        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
	        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "180000") }, threadPoolProperties = {
	        @HystrixProperty(name = "coreSize", value = "30"),
	        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "180000")})
	public String processForm(@ModelAttribute("details") FormDetails details, Model theModel) {

		int count = 0;
		if (details.getQ1().equals("no"))
			count++;
		if (details.getQ2().equals("no"))
			count++;
		if (details.getQ3().equals("no"))
			count++;
		if (details.getQ4().equals("no"))
			count++;
		if (details.getQ5().equals("no"))
			count++;

		String token2 = "Bearer " + jwtResponse.getAccessToken();
		log.info("--------date inside view-controller-----------");
		log.info(details.getDate().toString());

		Project project = new Project(details.getName(), details.getManager(), details.getOwner());
		AuditDetails auditDetails = new AuditDetails(details.getType(), count, details.getDate(), project);
		auditDetails.setToken(token2);


		AuditResponse aresponse = getResponse(token2, auditDetails);

		theModel.addAttribute("status", aresponse);

		return "success";
	}
	
	public String statusFallback(FormDetails details, Model theModel) {
		AuditResponse arFallback =  new AuditResponse("Cannot determine", "--", null);
		theModel.addAttribute("status", arFallback);
		return "success";
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
