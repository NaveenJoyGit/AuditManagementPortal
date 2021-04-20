package com.auditmanagementportal.model;

public class AuditDetails {

	private int id;

	private String type;

	private int count;

	private String date;
	
	private Project project;
	


	public AuditDetails(String type, int count, String date, Project project) {
		super();
		this.type = type;
		this.count = count;
		this.date = date;
		this.project = project;
	}

	public AuditDetails() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


}
