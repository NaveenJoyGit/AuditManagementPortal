package com.auditmanagementportal.model;

public class Project {

	private String name;
		
	private String manager;
	
	private String owner;
	
	public Project(String name, String manager, String owner) {
		super();
		this.name = name;
		this.manager = manager;
		this.owner = owner;
	}

	public Project() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
}
