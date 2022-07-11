package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Data Archival Policy Mgmt
 *
 * @author AyushRa
 */
public final class DataArchivalPolicy {
	private Integer dataArchivalPolicyId;
	private Integer institutionId;
	private Integer organizationId;
	private Integer archivalPeriodInMonths;
	private String autoArchivalFrequency;
	private Integer autoLogOffTimeInMinutes;
	private Integer durationToStoreAuditLogsInMonths;
	private String dataArchivalPolicyStatus;
	private String deleteMarker;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private transient Integer pkId;
	private transient Integer compliancePeriod;

	/**
	 * @return the dataArchivalPolicyId
	 */
	public Integer getDataArchivalPolicyId() {
		return dataArchivalPolicyId;
	}

	/**
	 * @param dataArchivalPolicyId
	 *            the dataArchivalPolicyId to set
	 */
	public void setDataArchivalPolicyId(Integer dataArchivalPolicyId) {
		this.dataArchivalPolicyId = dataArchivalPolicyId;
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
	 * @return the archivalPeriodInMonths
	 */
	public Integer getArchivalPeriodInMonths() {
		return archivalPeriodInMonths;
	}

	/**
	 * @param archivalPeriodInMonths
	 *            the archivalPeriodInMonths to set
	 */
	public void setArchivalPeriodInMonths(Integer archivalPeriodInMonths) {
		this.archivalPeriodInMonths = archivalPeriodInMonths;
	}

	/**
	 * @return the autoArchivalFrequency
	 */
	public String getAutoArchivalFrequency() {
		return autoArchivalFrequency;
	}

	/**
	 * @param autoArchivalFrequency
	 *            the autoArchivalFrequency to set
	 */
	public void setAutoArchivalFrequency(String autoArchivalFrequency) {
		this.autoArchivalFrequency = autoArchivalFrequency;
	}

	/**
	 * @return the autoLogOffTimeInMinutes
	 */
	public Integer getAutoLogOffTimeInMinutes() {
		return autoLogOffTimeInMinutes;
	}

	/**
	 * @param autoLogOffTimeInMinutes
	 *            the autoLogOffTimeInMinutes to set
	 */
	public void setAutoLogOffTimeInMinutes(Integer autoLogOffTimeInMinutes) {
		this.autoLogOffTimeInMinutes = autoLogOffTimeInMinutes;
	}

	/**
	 * @return the durationToStoreAuditLogsInMonths
	 */
	public Integer getDurationToStoreAuditLogsInMonths() {
		return durationToStoreAuditLogsInMonths;
	}

	/**
	 * @param durationToStoreAuditLogsInMonths
	 *            the durationToStoreAuditLogsInMonths to set
	 */
	public void setDurationToStoreAuditLogsInMonths(Integer durationToStoreAuditLogsInMonths) {
		this.durationToStoreAuditLogsInMonths = durationToStoreAuditLogsInMonths;
	}

	/**
	 * @return the dataArchivalPolicyStatus
	 */
	public String getDataArchivalPolicyStatus() {
		return dataArchivalPolicyStatus;
	}

	/**
	 * @param dataArchivalPolicyStatus
	 *            the dataArchivalPolicyStatus to set
	 */
	public void setDataArchivalPolicyStatus(String dataArchivalPolicyStatus) {
		this.dataArchivalPolicyStatus = dataArchivalPolicyStatus;
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

	/**
	 * @return the pkId
	 */
	public Integer getPkId() {
		return pkId;
	}

	/**
	 * @param pkId
	 *            the pkId to set
	 */
	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}

	/**
	 * @return the compliancePeriod
	 */
	public Integer getCompliancePeriod() {
		return compliancePeriod;
	}

	/**
	 * @param compliancePeriod
	 *            the compliancePeriod to set
	 */
	public void setCompliancePeriod(Integer compliancePeriod) {
		this.compliancePeriod = compliancePeriod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataArchivalPolicyId == null) ? 0 : dataArchivalPolicyId.hashCode());
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
		DataArchivalPolicy other = (DataArchivalPolicy) obj;
		if (dataArchivalPolicyId == null) {
			if (other.dataArchivalPolicyId != null) {
				return false;
			}
		} else if (!dataArchivalPolicyId.equals(other.dataArchivalPolicyId)) {
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
		return "DataArchivalPolicy [dataArchivalPolicyId=" + dataArchivalPolicyId + ", institutionId=" + institutionId
				+ ", organizationId=" + organizationId + ", archivalPeriodInMonths=" + archivalPeriodInMonths
				+ ", autoArchivalFrequency=" + autoArchivalFrequency + ", autoLogOffTimeInMinutes="
				+ autoLogOffTimeInMinutes + ", durationToStoreAuditLogsInMonths=" + durationToStoreAuditLogsInMonths
				+ ", dataArchivalPolicyStatus=" + dataArchivalPolicyStatus + ", deleteMarker=" + deleteMarker
				+ ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + ", pkId=" + pkId + ", compliancePeriod=" + compliancePeriod
				+ ", getDataArchivalPolicyId()=" + getDataArchivalPolicyId() + ", getInstitutionId()="
				+ getInstitutionId() + ", getOrganizationId()=" + getOrganizationId() + ", getArchivalPeriodInMonths()="
				+ getArchivalPeriodInMonths() + ", getAutoArchivalFrequency()=" + getAutoArchivalFrequency()
				+ ", getAutoLogOffTimeInMinutes()=" + getAutoLogOffTimeInMinutes()
				+ ", getDurationToStoreAuditLogsInMonths()=" + getDurationToStoreAuditLogsInMonths()
				+ ", getDataArchivalPolicyStatus()=" + getDataArchivalPolicyStatus() + ", getDeleteMarker()="
				+ getDeleteMarker() + ", getCreatedBy()=" + getCreatedBy() + ", getCreatedTimestamp()="
				+ getCreatedTimestamp() + ", getUpdatedBy()=" + getUpdatedBy() + ", getUpdatedTimestamp()="
				+ getUpdatedTimestamp() + ", getPkId()=" + getPkId() + ", getCompliancePeriod()="
				+ getCompliancePeriod() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

	
}