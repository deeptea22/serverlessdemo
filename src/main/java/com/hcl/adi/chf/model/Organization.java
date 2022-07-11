package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Institution
 *
 * @author AyushRa
 */
public final class Organization {
	private Integer organizationId;
	private String organizationName;
	private String address;
	private String contactPerson;
	private String contactNumber;
	private String organizationStatus;
	private String organizationType;
	private String organizationSubType;
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
	private transient String partnerOrProviderName;
	private transient String mapOrganizationType;

	// For the convenience of activate/deactivate toggle on UI
	private transient boolean statusFlag;


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
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
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
	 * @param contactPerson the contactPerson to set
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
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the organizationStatus
	 */
	public String getOrganizationStatus() {
		return organizationStatus;
	}

	/**
	 * @param organizationStatus the organizationStatus to set
	 */
	public void setOrganizationStatus(String organizationStatus) {
		this.organizationStatus = organizationStatus;
	}

	/**
	 * @return the organizationType
	 */
	public String getOrganizationType() {
		return organizationType;
	}

	/**
	 * @param organizationType the organizationType to set
	 */
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	/**
	 * @return the organizationSubType
	 */
	public String getOrganizationSubType() {
		return organizationSubType;
	}

	/**
	 * @param organizationSubType the organizationSubType to set
	 */
	public void setOrganizationSubType(String organizationSubType) {
		this.organizationSubType = organizationSubType;
	}

	/**
	 * @return the deleteMarker
	 */
	public String getDeleteMarker() {
		return deleteMarker;
	}

	/**
	 * @param deleteMarker the deleteMarker to set
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
	 * @param pwdExpireInDays the pwdExpireInDays to set
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
	 * @param pwdExpiryNotificationStartInDays the pwdExpiryNotificationStartInDays to set
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
	 * @param createdBy the createdBy to set
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
	 * @param createdTimestamp the createdTimestamp to set
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
	 * @param updatedBy the updatedBy to set
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
	 * @param updatedTimestamp the updatedTimestamp to set
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
	 * @param pwdPolicy the pwdPolicy to set
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
	 * @param tnc the tnc to set
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
	 * @param dataArchivalPolicy the dataArchivalPolicy to set
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
	 * @param action the action to set
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
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(boolean statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return the partnerOrProviderName
	 */
	public String getPartnerOrProviderName() {
		return partnerOrProviderName;
	}

	/**
	 * @param partnerOrProviderName the partnerOrProviderName to set
	 */
	public void setPartnerOrProviderName(String partnerOrProviderName) {
		this.partnerOrProviderName = partnerOrProviderName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((organizationId == null) ? 0 : organizationId.hashCode());
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
		Organization other = (Organization) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (organizationId == null) {
			if (other.organizationId != null) {
				return false;
			}
		} else if (!organizationId.equals(other.organizationId)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the mapOrganizationType
	 */
	public String getMapOrganizationType() {
		return mapOrganizationType;
	}

	/**
	 * @param mapOrganizationType the mapOrganizationType to set
	 */
	public void setMapOrganizationType(String mapOrganizationType) {
		this.mapOrganizationType = mapOrganizationType;
	}

	@Override
	public String toString() {
		return "Organization [organizationId=" + organizationId + ", organizationName=" + organizationName
				+ ", address=" + address + ", contactPerson=" + contactPerson + ", contactNumber=" + contactNumber
				+ ", organizationStatus=" + organizationStatus + ", organizationType=" + organizationType
				+ ", organizationSubType=" + organizationSubType + ", deleteMarker=" + deleteMarker
				+ ", pwdExpireInDays=" + pwdExpireInDays + ", pwdExpiryNotificationStartInDays="
				+ pwdExpiryNotificationStartInDays + ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp
				+ ", pwdPolicy=" + pwdPolicy + ", tnc=" + tnc + ", dataArchivalPolicy=" + dataArchivalPolicy
				+ ", action=" + action + ", partnerOrProviderName=" + partnerOrProviderName + ", mapOrganizationType="
				+ mapOrganizationType + ", statusFlag=" + statusFlag + "]";
	}

	
}