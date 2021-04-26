package com.auditmanagementportal.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditDetails {

	private String type;

	private int count;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	
	private Project project;
	
	private String token;
	


	public AuditDetails(String type, int count, LocalDate date, Project project) {
		super();
		this.type = type;
		this.count = count;
		this.date = date;
		this.project = project;
	}

}
