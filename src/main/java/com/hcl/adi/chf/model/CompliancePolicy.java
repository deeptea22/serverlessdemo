package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Compliance Policy
 *
 * @author SandeepSingh
 */
public final class CompliancePolicy {
	private Integer pkId;
	private Integer institutionId;
	private Integer compliancePeriod;
	private String status;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;

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
		result = prime * result + ((pkId == null) ? 0 : pkId.hashCode());
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
		CompliancePolicy other = (CompliancePolicy) obj;
		if (institutionId == null) {
			if (other.institutionId != null) {
				return false;
			}
		} else if (!institutionId.equals(other.institutionId)) {
			return false;
		}
		if (pkId == null) {
			if (other.pkId != null) {
				return false;
			}
		} else if (!pkId.equals(other.pkId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CompliancePolicy [pkId=" + pkId + ", institutionId=" + institutionId + ", compliancePeriod="
				+ compliancePeriod + ", status=" + status + ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
}