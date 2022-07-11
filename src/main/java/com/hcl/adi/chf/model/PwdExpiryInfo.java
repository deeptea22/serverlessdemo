package com.hcl.adi.chf.model;

import java.sql.Date;

/**
 * Model class for SendPwdExpiryInfoListToSQS & SQSReaderToSendEmail lambdas
 *
 * @author AyushRa
 */
public final class PwdExpiryInfo {
	private String emailId;
	private String firstName;
	private String lastName;
	private Date pwdUpdatedDate;
	private Integer pwdExpireInDays;
	private Integer pwdExpiryNotificationStartInDays;
	private Date pwdExpiryDate;
	private Date pwdExpiryNotificationStartDate;
	private String showNotification;
	private String pwdExpired;
	private Integer daysLeftInPwdExpiration;

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the pwdUpdatedDate
	 */
	public Date getPwdUpdatedDate() {
		return pwdUpdatedDate;
	}

	/**
	 * @param pwdUpdatedDate
	 *            the pwdUpdatedDate to set
	 */
	public void setPwdUpdatedDate(Date pwdUpdatedDate) {
		this.pwdUpdatedDate = pwdUpdatedDate;
	}

	/**
	 * @return the pwdExpireInDays
	 */
	public Integer getPwdExpireInDays() {
		return pwdExpireInDays;
	}

	/**
	 * @param pwdExpireInDays
	 *            the pwdExpireInDays to set
	 */
	public void setPwdExpireInDays(Integer pwdExpireInDays) {
		this.pwdExpireInDays = pwdExpireInDays;
	}

	/**
	 * @return the pwdExpiryNotificationStartInDays
	 */
	public Integer getPwdExpiryNotificationStartInDays() {
		return pwdExpiryNotificationStartInDays;
	}

	/**
	 * @param pwdExpiryNotificationStartInDays
	 *            the pwdExpiryNotificationStartInDays to set
	 */
	public void setPwdExpiryNotificationStartInDays(Integer pwdExpiryNotificationStartInDays) {
		this.pwdExpiryNotificationStartInDays = pwdExpiryNotificationStartInDays;
	}

	/**
	 * @return the pwdExpiryDate
	 */
	public Date getPwdExpiryDate() {
		return pwdExpiryDate;
	}

	/**
	 * @param pwdExpiryDate
	 *            the pwdExpiryDate to set
	 */
	public void setPwdExpiryDate(Date pwdExpiryDate) {
		this.pwdExpiryDate = pwdExpiryDate;
	}

	/**
	 * @return the pwdExpiryNotificationStartDate
	 */
	public Date getPwdExpiryNotificationStartDate() {
		return pwdExpiryNotificationStartDate;
	}

	/**
	 * @param pwdExpiryNotificationStartDate
	 *            the pwdExpiryNotificationStartDate to set
	 */
	public void setPwdExpiryNotificationStartDate(Date pwdExpiryNotificationStartDate) {
		this.pwdExpiryNotificationStartDate = pwdExpiryNotificationStartDate;
	}

	/**
	 * @return the showNotification
	 */
	public String getShowNotification() {
		return showNotification;
	}

	/**
	 * @param showNotification
	 *            the showNotification to set
	 */
	public void setShowNotification(String showNotification) {
		this.showNotification = showNotification;
	}

	/**
	 * @return the pwdExpired
	 */
	public String getPwdExpired() {
		return pwdExpired;
	}

	/**
	 * @param pwdExpired
	 *            the pwdExpired to set
	 */
	public void setPwdExpired(String pwdExpired) {
		this.pwdExpired = pwdExpired;
	}

	/**
	 * @return the daysLeftInPwdExpiration
	 */
	public Integer getDaysLeftInPwdExpiration() {
		return daysLeftInPwdExpiration;
	}

	/**
	 * @param daysLeftInPwdExpiration
	 *            the daysLeftInPwdExpiration to set
	 */
	public void setDaysLeftInPwdExpiration(Integer daysLeftInPwdExpiration) {
		this.daysLeftInPwdExpiration = daysLeftInPwdExpiration;
	}

	@Override
	public String toString() {
		return "PwdExpiryInfo [emailId=" + emailId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", pwdUpdatedDate=" + pwdUpdatedDate + ", pwdExpireInDays=" + pwdExpireInDays
				+ ", pwdExpiryNotificationStartInDays=" + pwdExpiryNotificationStartInDays + ", pwdExpiryDate="
				+ pwdExpiryDate + ", pwdExpiryNotificationStartDate=" + pwdExpiryNotificationStartDate
				+ ", showNotification=" + showNotification + ", pwdExpired=" + pwdExpired + ", daysLeftInPwdExpiration="
				+ daysLeftInPwdExpiration + "]";
	}
}