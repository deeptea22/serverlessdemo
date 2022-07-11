package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Master Details
 *
 * @author Shivendra
 */
public final class MasterDetails {
	private Integer id;
	private String masterTypeCode;
	private String keyCode;
	private String keyValue;
	private String createdBy;
	private String deleteMarker;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMasterTypeCode() {
		return masterTypeCode;
	}

	public void setMasterTypeCode(String masterTypeCode) {
		this.masterTypeCode = masterTypeCode;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeleteMarker() {
		return deleteMarker;
	}

	public void setDeleteMarker(String deleteMarker) {
		this.deleteMarker = deleteMarker;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keyCode == null) ? 0 : keyCode.hashCode());
		result = prime * result + ((keyValue == null) ? 0 : keyValue.hashCode());
		result = prime * result + ((masterTypeCode == null) ? 0 : masterTypeCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MasterDetails other = (MasterDetails) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keyCode == null) {
			if (other.keyCode != null)
				return false;
		} else if (!keyCode.equals(other.keyCode))
			return false;
		if (keyValue == null) {
			if (other.keyValue != null)
				return false;
		} else if (!keyValue.equals(other.keyValue))
			return false;
		if (masterTypeCode == null) {
			if (other.masterTypeCode != null)
				return false;
		} else if (!masterTypeCode.equals(other.masterTypeCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MasterDetails [id=" + id + ", masterTypeCode=" + masterTypeCode + ", keyCode=" + keyCode + ", keyValue="
				+ keyValue + ", createdBy=" + createdBy + ", deleteMarker=" + deleteMarker + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}

}
