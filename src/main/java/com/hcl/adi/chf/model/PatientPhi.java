package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Model class for patient_phi table
 *
 * @author AyushRa
 */
public final class PatientPhi {
    private Integer patientId;
    private String chfPatientId;
    private Integer institutionId;
    private String patientDetailsJson;
    private String firstName;
    private String lastName;
    private String doB;
    private String gender;
    private String mrNumber;
    private Boolean isActionOpen;
    private String deleteMarker;
    private String createdBy;
    private Date createdTimestamp;
    private String updatedBy;
    private Date updatedTimestamp;
    private List<PatientAttributes> patientAttributesList;
    private transient String deviceLabelId;
    private transient String systemId;
    private String contactNo;
    private String address;
    private String zip;
    private String ssn;
    private String birth_place;
	private String geo_code;
	private String who_id;
	private String mother_maiden_name;
	private String unique_alphanumeric_no;
	private String enterprise_specific_number;
	private String practice_specific;
	private String loc_specific_number;
	private Map<String,String> patientDetailsJsonMap;
    /**
     * @return the patientId
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * @param patientId the patientId to set
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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
     * @return the institutionId
     */
    public Integer getInstitutionId() {
        return institutionId;
    }

    /**
     * @param institutionId the institutionId to set
     */
    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    /**
     * @return the patientDetailsJson
     */
    public String getPatientDetailsJson() {
        return patientDetailsJson;
    }

    /**
     * @param patientDetailsJson the patientDetailsJson to set
     */
    public void setPatientDetailsJson(String patientDetailsJson) {
        this.patientDetailsJson = patientDetailsJson;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the doB
     */
    public String getDoB() {
        return doB;
    }

    /**
     * @param doB the doB to set
     */
    public void setDoB(String doB) {
        this.doB = doB;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the mrNumber
     */
    public String getMrNumber() {
        return mrNumber;
    }

    /**
     * @param mrNumber the mrNumber to set
     */
    public void setMrNumber(String mrNumber) {
        this.mrNumber = mrNumber;
    }

    

    /**
     * @return the isActionOpen
     */
    public Boolean getIsActionOpen() {
        return isActionOpen;
    }

    /**
     * @param isActionOpen the isActionOpen to set
     */
    public void setIsActionOpen(Boolean isActionOpen) {
        this.isActionOpen = isActionOpen;
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
     * @return the patientAttributesList
     */
    public List<PatientAttributes> getPatientAttributesList() {
        return patientAttributesList;
    }

    /**
     * @param patientAttributesList the patientAttributesList to set
     */
    public void setPatientAttributesList(List<PatientAttributes> patientAttributesList) {
        this.patientAttributesList = patientAttributesList;
    }

    /**
     * @return the deviceLabelId
     */
    public String getDeviceLabelId() {
        return deviceLabelId;
    }

    /**
     * @param systemId the deviceLabelId to set
     */
    public void setDeviceLabelId(String deviceLabelId) {
        this.deviceLabelId = deviceLabelId;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getBirth_place() {
		return birth_place;
	}

	public void setBirth_place(String birth_place) {
		this.birth_place = birth_place;
	}

	public String getGeo_code() {
		return geo_code;
	}

	public void setGeo_code(String geo_code) {
		this.geo_code = geo_code;
	}

	public String getWho_id() {
		return who_id;
	}

	public void setWho_id(String who_id) {
		this.who_id = who_id;
	}

	public String getMother_maiden_name() {
		return mother_maiden_name;
	}

	public void setMother_maiden_name(String mother_maiden_name) {
		this.mother_maiden_name = mother_maiden_name;
	}

	public String getUnique_alphanumeric_no() {
		return unique_alphanumeric_no;
	}

	public void setUnique_alphanumeric_no(String unique_alphanumeric_no) {
		this.unique_alphanumeric_no = unique_alphanumeric_no;
	}

	public String getEnterprise_specific_number() {
		return enterprise_specific_number;
	}

	public void setEnterprise_specific_number(String enterprise_specific_number) {
		this.enterprise_specific_number = enterprise_specific_number;
	}

	public String getPractice_specific() {
		return practice_specific;
	}

	public void setPractice_specific(String practice_specific) {
		this.practice_specific = practice_specific;
	}

	public String getLoc_specific_number() {
		return loc_specific_number;
	}

	public void setLoc_specific_number(String loc_specific_number) {
		this.loc_specific_number = loc_specific_number;
	}

	public Map<String, String> getPatientDetailsJsonMap() {
		return patientDetailsJsonMap;
	}

	public void setPatientDetailsJsonMap(Map<String, String> patientDetailsJsonMap) {
		this.patientDetailsJsonMap = patientDetailsJsonMap;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chfPatientId == null) ? 0 : chfPatientId.hashCode());
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
        PatientPhi other = (PatientPhi) obj;
        if (chfPatientId == null) {
            if (other.chfPatientId != null)
                return false;
        } else if (!chfPatientId.equals(other.chfPatientId))
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
        return "\n>>  PatientPhi [patientId=" + patientId + ", chfPatientId=" + chfPatientId + ", institutionId="
                + institutionId + ", patientDetailsJson=" + patientDetailsJson + ", firstName=" + firstName
                + ", lastName=" + lastName + ", doB=" + doB + ", gender=" + gender + ", mrNumber=" + mrNumber
                + ", isActionOpen=" + isActionOpen + ", deleteMarker=" + deleteMarker + ", createdBy=" + createdBy
                + ", createdTimestamp=" + createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp="
                + updatedTimestamp + ", patientAttributesList=" + patientAttributesList + ", contactNo=" + contactNo
                + ", address=" + address + ", zip=" + zip + ", ssn=" + ssn + "]";
    }
    /*
     * Static class to store patientdetailsJson as json array as mobile team
     * need the attributes in Json Array form
     */
    public static final class PatientAttributes {

        private String label;
        private String value;

        /**
         * @return the label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label
         *            the label to set
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }

        /**
         * @param value
         *            the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return " PatientAttributes [label=" + label + ", value=" + value + "]";
        }
    }
}
