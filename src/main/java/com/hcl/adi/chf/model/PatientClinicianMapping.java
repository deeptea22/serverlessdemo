package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class for Patient Clinician Mapping
 *
 * @author AyushRa
 */
public final class PatientClinicianMapping {
	private Integer mapId;
	private Integer patientId;
	private Integer clinicianId;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	private transient String action;

	/**
	 * @return the mapId
	 */
	public Integer getMapId() {
		return mapId;
	}

	/**
	 * @param mapId
	 *            the mapId to set
	 */
	public void setMapId(Integer mapId) {
		this.mapId = mapId;
	}

	/**
	 * @return the patientId
	 */
	public Integer getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId
	 *            the patientId to set
	 */
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clinicianId == null) ? 0 : clinicianId.hashCode());
		result = prime * result + ((mapId == null) ? 0 : mapId.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
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
		PatientClinicianMapping other = (PatientClinicianMapping) obj;
		if (clinicianId == null) {
			if (other.clinicianId != null) {
				return false;
			}
		} else if (!clinicianId.equals(other.clinicianId)) {
			return false;
		}
		if (mapId == null) {
			if (other.mapId != null) {
				return false;
			}
		} else if (!mapId.equals(other.mapId)) {
			return false;
		}
		if (patientId == null) {
			if (other.patientId != null) {
				return false;
			}
		} else if (!patientId.equals(other.patientId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PatientClinicianMapping [mapId=" + mapId + ", patientId=" + patientId + ", clinicianId=" + clinicianId
				+ ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + ", action=" + action + "]";
	}
}