package com.auditmanagementportal.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FormDetails {

	private String name;
	private String manager;
	private String owner;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	private String type;
//	private List<String> question;
	private String q1;
	private String q2;
	private String q3;
	private String q4;
	private String q5;
	
		
}
