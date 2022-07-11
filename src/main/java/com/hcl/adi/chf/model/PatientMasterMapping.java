package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;

/**
 * Model class for Patient Master Mapping and also use for creating json response
 *
 * @author Shivendra
 */
public final class PatientMasterMapping {
	private String id;
	private Integer masterId;
	private Integer patientId;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;

	private transient Integer institutionId;
	private transient int recordCount;
	private transient List<PatientMasterOtherMappings> medication;
	private transient List<PatientMasterOtherMappings> comorbidities;
	private transient List<PatientMasterOtherMappings> implants;
	private transient List<PatientMasterOtherMappings> procedures;
	private transient List<PatientMasterOtherMappings> admissions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMasterId() {
		return masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Integer getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Integer institutionId) {
		this.institutionId = institutionId;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public List<PatientMasterOtherMappings> getMedication() {
		return medication;
	}

	public void setMedication(List<PatientMasterOtherMappings> medication) {
		this.medication = medication;
	}

	public List<PatientMasterOtherMappings> getComorbidities() {
		return comorbidities;
	}

	public void setComorbidities(List<PatientMasterOtherMappings> comorbidities) {
		this.comorbidities = comorbidities;
	}

	public List<PatientMasterOtherMappings> getImplants() {
		return implants;
	}

	public void setImplants(List<PatientMasterOtherMappings> implants) {
		this.implants = implants;
	}

	public List<PatientMasterOtherMappings> getProcedures() {
		return procedures;
	}

	public void setProcedures(List<PatientMasterOtherMappings> procedures) {
		this.procedures = procedures;
	}

	public List<PatientMasterOtherMappings> getAdmissions() {
		return admissions;
	}

	public void setAdmissions(List<PatientMasterOtherMappings> admissions) {
		this.admissions = admissions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((masterId == null) ? 0 : masterId.hashCode());
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
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
		PatientMasterMapping other = (PatientMasterMapping) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (masterId == null) {
			if (other.masterId != null)
				return false;
		} else if (!masterId.equals(other.masterId))
			return false;
		if (patientId == null) {
			if (other.patientId != null)
				return false;
		} else if (!patientId.equals(other.patientId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatientMasterMapping [id=" + id + ", masterId=" + masterId + ", patientId=" + patientId + ", createdBy="
				+ createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy
				+ ", updatedTimestamp=" + updatedTimestamp + "]";
	}

}
