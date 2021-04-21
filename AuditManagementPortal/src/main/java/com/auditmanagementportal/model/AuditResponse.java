package com.auditmanagementportal.model;

public class AuditResponse {

	private int id;
			
	private String status;
	
	private String rem_duration;
	
	private AuditDetails detail;




	public AuditResponse(String status, String rem_duration, AuditDetails detail) {
		super();
		this.status = status;
		this.rem_duration = rem_duration;
		this.detail = detail;
	}
	
	

	public AuditResponse() {
		super();
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRem_duration() {
		return rem_duration;
	}

	public void setRem_duration(String rem_duration) {
		this.rem_duration = rem_duration;
	}

	public AuditDetails getDetail() {
		return detail;
	}
	
	
	
	public void setDetail(AuditDetails detail) {
		this.detail = detail;
	}
	
}
