package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Terms n Conditions
 *
 * @author AyushRa
 */
public final class TermsAndConditions {
	private Integer tncId;
	private Integer institutionId;
	private Integer organizationId;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private String tncStatus;
	private String deleteMarker;
	private String tncText;

	/**
	 * @return the tncId
	 */
	public Integer getTncId() {
		return tncId;
	}

	/**
	 * @param tncId
	 *            the tncId to set
	 */
	public void setTncId(Integer tncId) {
		this.tncId = tncId;
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
	 * @return the tncStatus
	 */
	public String getTncStatus() {
		return tncStatus;
	}

	/**
	 * @param tncStatus
	 *            the tncStatus to set
	 */
	public void setTncStatus(String tncStatus) {
		this.tncStatus = tncStatus;
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
	 * @return the tncText
	 */
	public String getTncText() {
		return tncText;
	}

	/**
	 * @param tncText
	 *            the tncText to set
	 */
	public void setTncText(String tncText) {
		this.tncText = tncText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((institutionId == null) ? 0 : institutionId.hashCode());
		result = prime * result + ((tncId == null) ? 0 : tncId.hashCode());
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
		TermsAndConditions other = (TermsAndConditions) obj;
		if (institutionId == null) {
			if (other.institutionId != null) {
				return false;
			}
		} else if (!institutionId.equals(other.institutionId)) {
			return false;
		}
		if (tncId == null) {
			if (other.tncId != null) {
				return false;
			}
		} else if (!tncId.equals(other.tncId)) {
			return false;
		}
		return true;
	}

	

	@Override
	public String toString() {
		return "TermsAndConditions [tncId=" + tncId + ", institutionId=" + institutionId + ", organizationId="
				+ organizationId + ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp
				+ ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + ", tncStatus=" + tncStatus
				+ ", deleteMarker=" + deleteMarker + ", tncText=" + tncText + ", getTncId()=" + getTncId()
				+ ", getInstitutionId()=" + getInstitutionId() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getCreatedTimestamp()=" + getCreatedTimestamp() + ", getUpdatedBy()=" + getUpdatedBy()
				+ ", getUpdatedTimestamp()=" + getUpdatedTimestamp() + ", getTncStatus()=" + getTncStatus()
				+ ", getDeleteMarker()=" + getDeleteMarker() + ", getTncText()=" + getTncText() + ", hashCode()="
				+ hashCode() + ", getOrganizationId()=" + getOrganizationId() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}

	
}