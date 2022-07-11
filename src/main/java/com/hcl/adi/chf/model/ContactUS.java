package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for contact_us
 *
 * @author Shivendra Singh
 */
public final class ContactUS {
	private Integer requestId;
	private String requesterName;
	private String fromEmail;
	private String toEmail;
	private String ccEmail;
	private String subject;
	private String message;
	private Boolean isCopyRequested;
	private String sesMsgId;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private transient Integer institutionId;;
	private transient String userType;

	/**
	 * @return the requestId
	 */
	public Integer getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId
	 *            the requestId to set
	 */
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the requesterName
	 */
	public String getRequesterName() {
		return requesterName;
	}

	/**
	 * @param requesterName
	 *            the requesterName to set
	 */
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	/**
	 * @return the fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail
	 *            the fromEmail to set
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	/**
	 * @return the toEmail
	 */
	public String getToEmail() {
		return toEmail;
	}

	/**
	 * @param toEmail
	 *            the toEmail to set
	 */
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	/**
	 * @return the ccEmail
	 */
	public String getCcEmail() {
		return ccEmail;
	}

	/**
	 * @param ccEmail
	 *            the ccEmail to set
	 */
	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the isCopyRequested
	 */
	public Boolean getIsCopyRequested() {
		return isCopyRequested;
	}

	/**
	 * @param isCopyRequested
	 *            the isCopyRequested to set
	 */
	public void setIsCopyRequested(Boolean isCopyRequested) {
		this.isCopyRequested = isCopyRequested;
	}

	/**
	 * @return the sesMsgId
	 */
	public String getSesMsgId() {
		return sesMsgId;
	}

	/**
	 * @param sesMsgId
	 *            the sesMsgId to set
	 */
	public void setSesMsgId(String sesMsgId) {
		this.sesMsgId = sesMsgId;
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

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedTimestamp
	 */
	public Date getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	/**
	 * @param updatedTimestamp
	 *            the updatedTimestamp to set
	 */
	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
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

	@Override
	public String toString() {
		return "ContactUS [requestId=" + requestId + ", requesterName=" + requesterName + ", fromEmail=" + fromEmail
				+ ", toEmail=" + toEmail + ", ccEmail=" + ccEmail + ", subject=" + subject + ", message=" + message
				+ ", isCopyRequested=" + isCopyRequested + ", sesMsgId=" + sesMsgId + ", createdBy=" + createdBy
				+ ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp="
				+ updatedTimestamp + ", institutionId=" + institutionId + ", userType=" + userType + "]";
	}
}