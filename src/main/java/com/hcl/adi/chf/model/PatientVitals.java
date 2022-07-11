package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;

/**
 * Model class for Patient Vitals
 *
 * @author DivyaAg
 */
public final class PatientVitals extends CustomResponse {
	private Integer patientVitalsId;
	private Integer patientId;
	private Float systolicBP;
	private Float diastolicBP;
	private Float weight;
	private Float temperature;
	private String customVitals;
	private List<CustomVitals> customVitalsList; 
	private String createdBy;
	private Date createdTimeStamp;
	private String updatedBy;
	private Date updatedTimeStamp;
	private transient Date readingDate;



	/**
	 * @return the patientVitalsId
	 */
	public Integer getPatientVitalsId() {
		return patientVitalsId;
	}

	/**
	 * @param patientVitalsId the patientVitalsId to set
	 */
	public void setPatientVitalsId(Integer patientVitalsId) {
		this.patientVitalsId = patientVitalsId;
	}

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
	 * @return the systolicBP
	 */
	public Float getSystolicBP() {
		return systolicBP;
	}

	/**
	 * @param systolicBP the systolicBP to set
	 */
	public void setSystolicBP(Float systolicBP) {
		this.systolicBP = systolicBP;
	}

	/**
	 * @return the diastolicBP
	 */
	public Float getDiastolicBP() {
		return diastolicBP;
	}

	/**
	 * @param diastolicBP the diastolicBP to set
	 */
	public void setDiastolicBP(Float diastolicBP) {
		this.diastolicBP = diastolicBP;
	}

	/**
	 * @return the weight
	 */
	public Float getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Float weight) {
		this.weight = weight;
	}

	/**
	 * @return the temperature
	 */
	public Float getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the customVitals
	 */
	public String getCustomVitals() {
		return customVitals;
	}

	/**
	 * @param customVitals the customVitals to set
	 */
	public void setCustomVitals(String customVitals) {
		this.customVitals = customVitals;
	}

	/**
	 * @return the customVitalsList
	 */
	public List<CustomVitals> getCustomVitalsList() {
		return customVitalsList;
	}

	/**
	 * @param customVitalsList the customVitalsList to set
	 */
	public void setCustomVitalsList(List<CustomVitals> customVitalsList) {
		this.customVitalsList = customVitalsList;
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
	 * @return the createdTimeStamp
	 */
	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	/**
	 * @param createdTimeStamp the createdTimeStamp to set
	 */
	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
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
	 * @return the updatedTimeStamp
	 */
	public Date getUpdatedTimeStamp() {
		return updatedTimeStamp;
	}

	/**
	 * @param updatedTimeStamp the updatedTimeStamp to set
	 */
	public void setUpdatedTimeStamp(Date updatedTimeStamp) {
		this.updatedTimeStamp = updatedTimeStamp;
	}

	/**
	 * @return the readingDate
	 */
	public Date getReadingDate() {
		return readingDate;
	}

	/**
	 * @param readingDate the readingDate to set
	 */
	public void setReadingDate(Date readingDate) {
		this.readingDate = readingDate;
	}

	

	@Override
	public String toString() {
		return "PatientVitals [patientVitalsId=" + patientVitalsId + ", patientId=" + patientId + ", systolicBP="
				+ systolicBP + ", diastolicBP=" + diastolicBP + ", weight=" + weight + ", temperature=" + temperature
				+ ", customVitals=" + customVitals + ", customVitalsList=" + customVitalsList + ", createdBy="
				+ createdBy + ", createdTimeStamp=" + createdTimeStamp + ", updatedBy=" + updatedBy
				+ ", updatedTimeStamp=" + updatedTimeStamp + ", readingDate=" + readingDate + "]";
	}



	/*
	 * Static class to store custom vitals as json array
	 * 
	 */
	public static final class CustomVitals {

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
			return "CustomVitals [label=" + label + ", value=" + value + "]";
		}
	}

}