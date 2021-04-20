package com.auditmanagementportal.model;

import java.util.ArrayList;
import java.util.List;

public class AuditType {

	private Long id;

	private String auditType;

	private List<Questions> questions = new ArrayList<>();

	public AuditType() {
	}

	public AuditType(String auditType) {
		super();
		this.auditType = auditType;
	}
	

	@Override
	public String toString() {
		return "AuditType [id=" + id + ", auditType=" + auditType + ", questions=" + questions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auditType == null) ? 0 : auditType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditType other = (AuditType) obj;
		if (auditType == null) {
			if (other.auditType != null)
				return false;
		} else if (!auditType.equals(other.auditType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public List<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}

}
