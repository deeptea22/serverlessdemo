package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for maximum possible details of an user
 *
 * @author AyushRa
 */
public final class UserDetails {
	private Integer id;
	private String emailId;
	private String firstName;
	private String lastName;
	private String employeeId;
	private String poolId;
	private String status;
	private String type;
	private String portalAccess;
	private String location;
	private String deleteMarker;
	private String isTncAccepted;
	private Date pwdUpdatedDate;
	private Date lastLoginTimestamp;
	private String locale;
	private String timezone;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private Integer institutionId;
	private String institutionName;
	private String institutionAddress;
	private Integer pwdExpireInDays;
	private Integer pwdExpiryNotificationStartInDays;
	private Date pwdExpiryDate;
	private Date pwdExpiryNotificationStartDate;
	private String showNotification;
	private String pwdExpired;
	private Integer daysLeftInPwdExpiration;
	private PasswordPolicy pwdPolicy;
	private TermsAndConditions tnc;
	private DataArchivalPolicy dataArchivalPolicy;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

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
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the poolId
	 */
	public String getPoolId() {
		return poolId;
	}

	/**
	 * @param poolId
	 *            the poolId to set
	 */
	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the portalAccess
	 */
	public String getPortalAccess() {
		return portalAccess;
	}

	/**
	 * @param portalAccess
	 *            the portalAccess to set
	 */
	public void setPortalAccess(String portalAccess) {
		this.portalAccess = portalAccess;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the deleteMarker
	 */
	public String getDeleteMarker() {
		return deleteMarker;
	}

	/**
	 * @param deleteMarker
	 *            the deleteMarker to set
	 */
	public void setDeleteMarker(String deleteMarker) {
		this.deleteMarker = deleteMarker;
	}

	/**
	 * @return the isTncAccepted
	 */
	public String getIsTncAccepted() {
		return isTncAccepted;
	}

	/**
	 * @param isTncAccepted
	 *            the isTncAccepted to set
	 */
	public void setIsTncAccepted(String isTncAccepted) {
		this.isTncAccepted = isTncAccepted;
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
	 * @return the lastLoginTimestamp
	 */
	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	/**
	 * @param lastLoginTimestamp
	 *            the lastLoginTimestamp to set
	 */
	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
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
	 * @return the institutionName
	 */
	public String getInstitutionName() {
		return institutionName;
	}

	/**
	 * @param institutionName
	 *            the institutionName to set
	 */
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	/**
	 * @return the institutionAddress
	 */
	public String getInstitutionAddress() {
		return institutionAddress;
	}

	/**
	 * @param institutionAddress
	 *            the institutionAddress to set
	 */
	public void setInstitutionAddress(String institutionAddress) {
		this.institutionAddress = institutionAddress;
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

	/**
	 * @return the pwdPolicy
	 */
	public PasswordPolicy getPwdPolicy() {
		return pwdPolicy;
	}

	/**
	 * @param pwdPolicy
	 *            the pwdPolicy to set
	 */
	public void setPwdPolicy(PasswordPolicy pwdPolicy) {
		this.pwdPolicy = pwdPolicy;
	}

	/**
	 * @return the tnc
	 */
	public TermsAndConditions getTnc() {
		return tnc;
	}

	/**
	 * @param tnc
	 *            the tnc to set
	 */
	public void setTnc(TermsAndConditions tnc) {
		this.tnc = tnc;
	}

	/**
	 * @return the dataArchivalPolicy
	 */
	public DataArchivalPolicy getDataArchivalPolicy() {
		return dataArchivalPolicy;
	}

	/**
	 * @param dataArchivalPolicy
	 *            the dataArchivalPolicy to set
	 */
	public void setDataArchivalPolicy(DataArchivalPolicy dataArchivalPolicy) {
		this.dataArchivalPolicy = dataArchivalPolicy;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", emailId=" + emailId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", employeeId=" + employeeId + ", poolId=" + poolId + ", status=" + status + ", type=" + type
				+ ", portalAccess=" + portalAccess + ", location=" + location + ", deleteMarker=" + deleteMarker
				+ ", isTncAccepted=" + isTncAccepted + ", pwdUpdatedDate=" + pwdUpdatedDate + ", lastLoginTimestamp="
				+ lastLoginTimestamp + ", locale=" + locale + ", timezone=" + timezone + ", createdBy=" + createdBy
				+ ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp="
				+ updatedTimestamp + ", institutionId=" + institutionId + ", institutionName=" + institutionName
				+ ", institutionAddress=" + institutionAddress + ", pwdExpireInDays=" + pwdExpireInDays
				+ ", pwdExpiryNotificationStartInDays=" + pwdExpiryNotificationStartInDays + ", pwdExpiryDate="
				+ pwdExpiryDate + ", pwdExpiryNotificationStartDate=" + pwdExpiryNotificationStartDate
				+ ", showNotification=" + showNotification + ", pwdExpired=" + pwdExpired + ", daysLeftInPwdExpiration="
				+ daysLeftInPwdExpiration + ", pwdPolicy=" + pwdPolicy + ", tnc=" + tnc + ", dataArchivalPolicy="
				+ dataArchivalPolicy + "]";
	}
}