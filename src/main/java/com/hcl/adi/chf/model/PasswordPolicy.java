package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Pwd Policy Mgmt
 *
 * @author DivyaAg
 */
public final class PasswordPolicy {
	private Integer pwdPolicyId;
	private Integer institutionId;
	private Integer organizationId;
	private Integer pwdRotationInDays;
	private Integer pwdMinLength;
	private Integer pwdMaxLength;
	private String isCapsAllowed;
	private String isLowerAllowed;
	private String isNumericAllowed;
	private String isSplCharAllowed;
	private Integer retryLoginAttemptsAllowed;
	private Integer pwdHistory;
	private String prohibitedPasswords;
	private String pwdPolicyStatus;
	private String deleteMarker;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;

	/**
	 * @return the pwdPolicyId
	 */
	public Integer getPwdPolicyId() {
		return pwdPolicyId;
	}

	/**
	 * @param pwdPolicyId
	 *            the pwdPolicyId to set
	 */
	public void setPwdPolicyId(Integer pwdPolicyId) {
		this.pwdPolicyId = pwdPolicyId;
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
	 * @return the organizationId
	 */
	public Integer getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	
	/**
	 * @return the pwdRotationInDays
	 */
	public Integer getPwdRotationInDays() {
		return pwdRotationInDays;
	}

	/**
	 * @param pwdRotationInDays
	 *            the pwdRotationInDays to set
	 */
	public void setPwdRotationInDays(Integer pwdRotationInDays) {
		this.pwdRotationInDays = pwdRotationInDays;
	}

	/**
	 * @return the pwdMinLength
	 */
	public Integer getPwdMinLength() {
		return pwdMinLength;
	}

	/**
	 * @param pwdMinLength
	 *            the pwdMinLength to set
	 */
	public void setPwdMinLength(Integer pwdMinLength) {
		this.pwdMinLength = pwdMinLength;
	}

	/**
	 * @return the pwdMaxLength
	 */
	public Integer getPwdMaxLength() {
		return pwdMaxLength;
	}

	/**
	 * @param pwdMaxLength
	 *            the pwdMaxLength to set
	 */
	public void setPwdMaxLength(Integer pwdMaxLength) {
		this.pwdMaxLength = pwdMaxLength;
	}

	/**
	 * @return the isCapsAllowed
	 */
	public String getIsCapsAllowed() {
		return isCapsAllowed;
	}

	/**
	 * @param isCapsAllowed
	 *            the isCapsAllowed to set
	 */
	public void setIsCapsAllowed(String isCapsAllowed) {
		this.isCapsAllowed = isCapsAllowed;
	}

	/**
	 * @return the isLowerAllowed
	 */
	public String getIsLowerAllowed() {
		return isLowerAllowed;
	}

	/**
	 * @param isLowerAllowed
	 *            the isLowerAllowed to set
	 */
	public void setIsLowerAllowed(String isLowerAllowed) {
		this.isLowerAllowed = isLowerAllowed;
	}

	/**
	 * @return the isNumericAllowed
	 */
	public String getIsNumericAllowed() {
		return isNumericAllowed;
	}

	/**
	 * @param isNumericAllowed
	 *            the isNumericAllowed to set
	 */
	public void setIsNumericAllowed(String isNumericAllowed) {
		this.isNumericAllowed = isNumericAllowed;
	}

	/**
	 * @return the isSplCharAllowed
	 */
	public String getIsSplCharAllowed() {
		return isSplCharAllowed;
	}

	/**
	 * @param isSplCharAllowed
	 *            the isSplCharAllowed to set
	 */
	public void setIsSplCharAllowed(String isSplCharAllowed) {
		this.isSplCharAllowed = isSplCharAllowed;
	}

	/**
	 * @return the retryLoginAttemptsAllowed
	 */
	public Integer getRetryLoginAttemptsAllowed() {
		return retryLoginAttemptsAllowed;
	}

	/**
	 * @param retryLoginAttemptsAllowed
	 *            the retryLoginAttemptsAllowed to set
	 */
	public void setRetryLoginAttemptsAllowed(Integer retryLoginAttemptsAllowed) {
		this.retryLoginAttemptsAllowed = retryLoginAttemptsAllowed;
	}

	/**
	 * @return the pwdHistory
	 */
	public Integer getPwdHistory() {
		return pwdHistory;
	}

	/**
	 * @param pwdHistory
	 *            the pwdHistory to set
	 */
	public void setPwdHistory(Integer pwdHistory) {
		this.pwdHistory = pwdHistory;
	}

	/**
	 * @return the prohibitedPasswords
	 */
	public String getProhibitedPasswords() {
		return prohibitedPasswords;
	}

	/**
	 * @param prohibitedPasswords
	 *            the prohibitedPasswords to set
	 */
	public void setProhibitedPasswords(String prohibitedPasswords) {
		this.prohibitedPasswords = prohibitedPasswords;
	}

	/**
	 * @return the pwdPolicyStatus
	 */
	public String getPwdPolicyStatus() {
		return pwdPolicyStatus;
	}

	/**
	 * @param pwdPolicyStatus
	 *            the pwdPolicyStatus to set
	 */
	public void setPwdPolicyStatus(String pwdPolicyStatus) {
		this.pwdPolicyStatus = pwdPolicyStatus;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((institutionId == null) ? 0 : institutionId.hashCode());
		result = prime * result + ((pwdPolicyId == null) ? 0 : pwdPolicyId.hashCode());
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
		PasswordPolicy other = (PasswordPolicy) obj;
		if (institutionId == null) {
			if (other.institutionId != null) {
				return false;
			}
		} else if (!institutionId.equals(other.institutionId)) {
			return false;
		}
		if (pwdPolicyId == null) {
			if (other.pwdPolicyId != null) {
				return false;
			}
		} else if (!pwdPolicyId.equals(other.pwdPolicyId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PasswordPolicy [pwdPolicyId=" + pwdPolicyId + ", institutionId=" + institutionId + ", organizationId="
				+ organizationId + ", pwdRotationInDays=" + pwdRotationInDays + ", pwdMinLength=" + pwdMinLength
				+ ", pwdMaxLength=" + pwdMaxLength + ", isCapsAllowed=" + isCapsAllowed + ", isLowerAllowed="
				+ isLowerAllowed + ", isNumericAllowed=" + isNumericAllowed + ", isSplCharAllowed=" + isSplCharAllowed
				+ ", retryLoginAttemptsAllowed=" + retryLoginAttemptsAllowed + ", pwdHistory=" + pwdHistory
				+ ", prohibitedPasswords=" + prohibitedPasswords + ", pwdPolicyStatus=" + pwdPolicyStatus
				+ ", deleteMarker=" + deleteMarker + ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp
				+ ", getPwdPolicyId()=" + getPwdPolicyId() + ", getInstitutionId()=" + getInstitutionId()
				+ ", getPwdRotationInDays()=" + getPwdRotationInDays() + ", getPwdMinLength()=" + getPwdMinLength()
				+ ", getPwdMaxLength()=" + getPwdMaxLength() + ", getIsCapsAllowed()=" + getIsCapsAllowed()
				+ ", getIsLowerAllowed()=" + getIsLowerAllowed() + ", getIsNumericAllowed()=" + getIsNumericAllowed()
				+ ", getIsSplCharAllowed()=" + getIsSplCharAllowed() + ", getRetryLoginAttemptsAllowed()="
				+ getRetryLoginAttemptsAllowed() + ", getPwdHistory()=" + getPwdHistory()
				+ ", getProhibitedPasswords()=" + getProhibitedPasswords() + ", getPwdPolicyStatus()="
				+ getPwdPolicyStatus() + ", getDeleteMarker()=" + getDeleteMarker() + ", getCreatedBy()="
				+ getCreatedBy() + ", getCreatedTimestamp()=" + getCreatedTimestamp() + ", getUpdatedBy()="
				+ getUpdatedBy() + ", getUpdatedTimestamp()=" + getUpdatedTimestamp() + ", hashCode()=" + hashCode()
				+ ", getOrganizationId()=" + getOrganizationId() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

	
}