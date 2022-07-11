package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Alert Data
 *
 * @author SandeepSingh
 */
public final class AlertData {
	private String alertDataId;
	private String msgId;
	private String severity;
	private String msg;
	private String breachParam;
	private String breachText;
	private String isActive;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private transient Date readingDate;

	/**
	 * @return the alertDataId
	 */
	public String getAlertDataId() {
		return alertDataId;
	}

	/**
	 * @param alertDataId
	 *            the alertDataId to set
	 */
	public void setAlertDataId(String alertDataId) {
		this.alertDataId = alertDataId;
	}

	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId
	 *            the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the breachParam
	 */
	public String getBreachParam() {
		return breachParam;
	}

	/**
	 * @param breachParam
	 *            the breachParam to set
	 */
	public void setBreachParam(String breachParam) {
		this.breachParam = breachParam;
	}

	/**
	 * @return the breachText
	 */
	public String getBreachText() {
		return breachText;
	}

	/**
	 * @param breachText
	 *            the breachText to set
	 */
	public void setBreachText(String breachText) {
		this.breachText = breachText;
	}

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	 * @return the readingDate
	 */
	public Date getReadingDate() {
		return readingDate;
	}

	/**
	 * @param readingDate
	 *            the readingDate to set
	 */
	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}

	@Override
	public String toString() {
		return "AlertData [alertDataId=" + alertDataId + ", msgId=" + msgId + ", severity=" + severity + ", msg=" + msg
				+ ", breachParam=" + breachParam + ", breachText=" + breachText + ", isActive=" + isActive + ", createdBy="
				+ createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + ", readingDate=" + readingDate + "]";
	}
}