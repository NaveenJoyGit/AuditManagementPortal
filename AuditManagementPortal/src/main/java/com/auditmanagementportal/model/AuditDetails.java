package com.auditmanagementportal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditDetails {

	private String type;

	private int count;

	private String date;
	
	private Project project;
	
	private String token;
	


	public AuditDetails(String type, int count, String date, Project project) {
		super();
		this.type = type;
		this.count = count;
		this.date = date;
		this.project = project;
	}

}
