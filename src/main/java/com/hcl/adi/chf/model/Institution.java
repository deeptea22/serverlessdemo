package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Institution
 *
 * @author AyushRa
 */
public final class Institution {
	private Integer institutionId;
	private String institutionName;
	private String address;
	private String contactPerson;
	private String contactNumber;
	private String institutionStatus;
	private String deleteMarker;
	private Integer pwdExpireInDays;
	private Integer pwdExpiryNotificationStartInDays;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private PasswordPolicy pwdPolicy;
	private TermsAndConditions tnc;
	private DataArchivalPolicy dataArchivalPolicy;
	private transient String action;

	// For the convenience of activate/deactivate toggle on UI
	private transient boolean statusFlag;

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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson
	 *            the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the institutionStatus
	 */
	public String getInstitutionStatus() {
		return institutionStatus;
	}

	/**
	 * @param institutionStatus
	 *            the institutionStatus to set
	 */
	public void setInstitutionStatus(String institutionStatus) {
		this.institutionStatus = institutionStatus;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((institutionId == null) ? 0 : institutionId.hashCode());
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
		Institution other = (Institution) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (institutionId == null) {
			if (other.institutionId != null) {
				return false;
			}
		} else if (!institutionId.equals(other.institutionId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Institution [institutionId=" + institutionId + ", institutionName=" + institutionName + ", address="
				+ address + ", contactPerson=" + contactPerson + ", contactNumber=" + contactNumber
				+ ", institutionStatus=" + institutionStatus + ", deleteMarker=" + deleteMarker + ", pwdExpireInDays="
				+ pwdExpireInDays + ", pwdExpiryNotificationStartInDays=" + pwdExpiryNotificationStartInDays
				+ ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + ", pwdPolicy=" + pwdPolicy + ", tnc=" + tnc
				+ ", dataArchivalPolicy=" + dataArchivalPolicy + ", action=" + action + ", statusFlag=" + statusFlag
				+ "]";
	}
}