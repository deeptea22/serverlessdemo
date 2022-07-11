package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Audit Logs
 *
 * @author DivyaAg
 */
public final class AuditLog {
	private Integer logId;
	private Integer institutionId;
	private String userType;
	private String activity;
	private String logToDB;
	private String createdBy;
	private Date createdTimestamp;

	/**
	 * @return the logId
	 */
	public Integer getLogId() {
		return logId;
	}

	/**
	 * @param logId
	 *            the logId to set
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	/**
	 * @return the institutionId
	 */
	public Integer getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId
	 *            the institutionId to set
	 */
	public void setInstitutionId(Integer institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity
	 *            the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdTimestamp
	 */
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	/**
	 * @param createdTimestamp
	 *            the createdTimestamp to set
	 */
	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	@Override
	public String toString() {
		return "AuditLog [logId=" + logId + ", institutionId=" + institutionId + ", userType=" + userType
				+ ", activity=" + activity + ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp
				+ "]";
	}

	/**
	 * @return the logToDB
	 */
	public String getLogToDB() {
		return logToDB;
	}

	/**
	 * @param logToDB the logToDB to set
	 */
	public void setLogToDB(String logToDB) {
		this.logToDB = logToDB;
	}
}