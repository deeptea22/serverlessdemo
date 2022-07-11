package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Clinician
 *
 * @author AyushRa
 */
public final class Clinician {
	private Integer clinicianId;
	private Integer institutionId;
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
	private Integer retryLoginAttemptCounter;
	private Date lastLoginTimestamp;
	private String locale;
	private String timezone;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private transient String action;

	// For the convenience of activate/deactivate toggle on UI
	private transient boolean statusFlag;

	/**
	 * @return the clinicianId
	 */
	public Integer getClinicianId() {
		return clinicianId;
	}

	/**
	 * @param clinicianId
	 *            the clinicianId to set
	 */
	public void setClinicianId(Integer clinicianId) {
		this.clinicianId = clinicianId;
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
	 * @return the retryLoginAttemptCounter
	 */
	public Integer getRetryLoginAttemptCounter() {
		return retryLoginAttemptCounter;
	}

	/**
	 * @param retryLoginAttemptCounter
	 *            the retryLoginAttemptCounter to set
	 */
	public void setRetryLoginAttemptCounter(Integer retryLoginAttemptCounter) {
		this.retryLoginAttemptCounter = retryLoginAttemptCounter;
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
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the statusFlag
	 */
	public boolean isStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag
	 *            the statusFlag to set
	 */
	public void setStatusFlag(boolean statusFlag) {
		this.statusFlag = statusFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clinicianId == null) ? 0 : clinicianId.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Clinician other = (Clinician) obj;
		if (clinicianId == null) {
			if (other.clinicianId != null) {
				return false;
			}
		} else if (!clinicianId.equals(other.clinicianId)) {
			return false;
		}
		if (emailId == null) {
			if (other.emailId != null) {
				return false;
			}
		} else if (!emailId.equals(other.emailId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Clinician [clinicianId=" + clinicianId + ", institutionId=" + institutionId + ", emailId=" + emailId
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", employeeId=" + employeeId + ", poolId="
				+ poolId + ", status=" + status + ", type=" + type + ", portalAccess=" + portalAccess + ", location="
				+ location + ", deleteMarker=" + deleteMarker + ", isTncAccepted=" + isTncAccepted + ", pwdUpdatedDate="
				+ pwdUpdatedDate + ", retryLoginAttemptCounter=" + retryLoginAttemptCounter + ", lastLoginTimestamp="
				+ lastLoginTimestamp + ", locale=" + locale + ", timezone=" + timezone + ", createdBy=" + createdBy
				+ ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp="
				+ updatedTimestamp + ", action=" + action + ", statusFlag=" + statusFlag + "]";
	}
}