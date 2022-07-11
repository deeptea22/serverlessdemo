package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Patient Device Mapping
 *
 * @author DivyaAg
 */
public final class PatientDeviceMapping {
	private Integer mappingId;
	private String systemId;
	private Integer kitId;
	private String deviceLabelId;
	private String chfPatientId;
	private String mappingStatus;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	/**
	 * @return the mappingId
	 */
	public Integer getMappingId() {
		return mappingId;
	}
	/**
	 * @param mappingId the mappingId to set
	 */
	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}
	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}
	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	/**
	 * @return the kitId
	 */
	public Integer getKitId() {
		return kitId;
	}
	/**
	 * @param kitId the kitId to set
	 */
	public void setKitId(Integer kitId) {
		this.kitId = kitId;
	}
	/**
	 * @return the deviceLabelId
	 */
	public String getDeviceLabelId() {
		return deviceLabelId;
	}
	/**
	 * @param deviceLabelId the deviceLabelId to set
	 */
	public void setDeviceLabelId(String deviceLabelId) {
		this.deviceLabelId = deviceLabelId;
	}
	/**
	 * @return the chfPatientId
	 */
	public String getChfPatientId() {
		return chfPatientId;
	}
	/**
	 * @param chfPatientId the chfPatientId to set
	 */
	public void setChfPatientId(String chfPatientId) {
		this.chfPatientId = chfPatientId;
	}
	/**
	 * @return the mappingStatus
	 */
	public String getMappingStatus() {
		return mappingStatus;
	}
	/**
	 * @param mappingStatus the mappingStatus to set
	 */
	public void setMappingStatus(String mappingStatus) {
		this.mappingStatus = mappingStatus;
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
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chfPatientId == null) ? 0 : chfPatientId.hashCode());
		result = prime * result + ((systemId == null) ? 0 : systemId.hashCode());
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
		PatientDeviceMapping other = (PatientDeviceMapping) obj;
		if (chfPatientId == null) {
			if (other.chfPatientId != null)
				return false;
		} else if (!chfPatientId.equals(other.chfPatientId))
			return false;
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PatientDeviceMapping [mappingId=" + mappingId + ", systemId=" + systemId + ", kitId=" + kitId
				+ ", chfPatientId=" + chfPatientId + ", mappingStatus=" + mappingStatus + ", createdBy=" + createdBy
				+ ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp="
				+ updatedTimestamp + "]";
	}
	
	
}