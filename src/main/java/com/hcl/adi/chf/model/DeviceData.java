package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;

/**
 * Model class for Device Data
 *
 * @author DivyaAg
 */
public final class DeviceData {
	private Integer pkId;
	private Integer measurementId;
	private Integer institutionId;
	private Integer patientId;
	private Integer deviceId;
	private Ecg ecg; 
	private String status;
	private String impedance;
	private String temperature;
	private String heartSoundUrl;
	private Date readingDate;
	private boolean isBaseLineReading;
	private transient String severity;
	/**
	 * @return the pkId
	 */
	public Integer getPkId() {
		return pkId;
	}
	/**
	 * @param pkId the pkId to set
	 */
	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}

	/**
	 * @return the measurementId
	 */
	public Integer getMeasurementId() {
		return measurementId;
	}
	/**
	 * @param measurementId the measurementId to set
	 */
	public void setMeasurementId(Integer measurementId) {
		this.measurementId = measurementId;
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
	 * @return the deviceId
	 */
	public Integer getDeviceId() {
		return deviceId;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}



	/**
	 * @return the ecg
	 */
	public Ecg getEcg() {
		return ecg;
	}
	/**
	 * @param ecg the ecg to set
	 */
	public void setEcg(Ecg ecg) {
		this.ecg = ecg;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the impedance
	 */
	public String getImpedance() {
		return impedance;
	}
	/**
	 * @param impedance the impedance to set
	 */
	public void setImpedance(String impedance) {
		this.impedance = impedance;
	}
	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	/**
	 * @return the heartSoundUrl
	 */
	public String getHeartSoundUrl() {
		return heartSoundUrl;
	}
	/**
	 * @param heartSoundUrl the heartSoundUrl to set
	 */
	public void setHeartSoundUrl(String heartSoundUrl) {
		this.heartSoundUrl = heartSoundUrl;
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
	/**
	 * @return the isBaseLineReading
	 */
	public boolean isBaseLineReading() {
		return isBaseLineReading;
	}
	/**
	 * @param isBaseLineReading the isBaseLineReading to set
	 */
	public void setBaseLineReading(boolean isBaseLineReading) {
		this.isBaseLineReading = isBaseLineReading;
	}
	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}



	/*
	 * Static class to store ecg as json array
	 * 
	 */
	public static final class Ecg {

		private float startTime;
		private float interval;
		private float endTime;
		private List<Float> ecgValue;
		/**
		 * @return the startTime
		 */
		public float getStartTime() {
			return startTime;
		}
		/**
		 * @param startTime the startTime to set
		 */
		public void setStartTime(float startTime) {
			this.startTime = startTime;
		}
		/**
		 * @return the interval
		 */
		public float getInterval() {
			return interval;
		}
		/**
		 * @param interval the interval to set
		 */
		public void setInterval(float interval) {
			this.interval = interval;
		}
		/**
		 * @return the endTime
		 */
		public float getEndTime() {
			return endTime;
		}
		/**
		 * @param endTime the endTime to set
		 */
		public void setEndTime(float endTime) {
			this.endTime = endTime;
		}
		/**
		 * @return the ecgValue
		 */
		public List<Float> getEcgValue() {
			return ecgValue;
		}
		/**
		 * @param ecgValue the ecgValue to set
		 */
		public void setEcgValue(List<Float> ecgValue) {
			this.ecgValue = ecgValue;
		}

		@Override
		public String toString() {
			return "Ecg [startTime=" + startTime + ", interval=" + interval + ", endTime=" + endTime + ", ecgValue="
					+ ecgValue + "]";
		}

	}


}