package com.hcl.adi.chf.model;

/**
 * Model class to render values on bottom widget of clinician's landing page
 *
 * @author Sandeep Singh
 */
public final class PatientRecord {
	private String institutionId;
	private String patientId;
	private String chfPatientId;
	private String lastRead;
	private String heartRateTrend;
	private String heartRateTrendValue;
	private String qtTrend;
	private String qtWidth;
	private String qrsTrend;
	private String qrsWidth;
	private String respirationRateTrend;
	private String respirationRateTrendValue;
	private String s3Trend;
	private String tidalVolumeTrend;
	private String thoracicImpedanceTrend;
	private String readingCompliance;
	private String dailyCompliance;
	private String percentage;
	private String systemId;
	private String measurementId;
	private String thresholdAlarms;
	private String abnormalRhythm;

	/**
	 * @return the institutionId
	 */
	public String getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	/**
	 * @return the patientId
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * @return the lastRead
	 */
	public String getLastRead() {
		return lastRead;
	}

	/**
	 * @param lastRead the lastRead to set
	 */
	public void setLastRead(String lastRead) {
		this.lastRead = lastRead;
	}

	/**
	 * @return the heartRateTrend
	 */
	public String getHeartRateTrend() {
		return heartRateTrend;
	}

	/**
	 * @param heartRateTrend the heartRateTrend to set
	 */
	public void setHeartRateTrend(String heartRateTrend) {
		this.heartRateTrend = heartRateTrend;
	}

	/**
	 * @return the heartRateTrendValue
	 */
	public String getHeartRateTrendValue() {
		return heartRateTrendValue;
	}

	/**
	 * @param heartRateTrendValue the heartRateTrendValue to set
	 */
	public void setHeartRateTrendValue(String heartRateTrendValue) {
		this.heartRateTrendValue = heartRateTrendValue;
	}

	/**
	 * @return the respirationRateTrend
	 */
	public String getRespirationRateTrend() {
		return respirationRateTrend;
	}

	/**
	 * @param respirationRateTrend the respirationRateTrend to set
	 */
	public void setRespirationRateTrend(String respirationRateTrend) {
		this.respirationRateTrend = respirationRateTrend;
	}

	/**
	 * @return the respirationRateTrendValue
	 */
	public String getRespirationRateTrendValue() {
		return respirationRateTrendValue;
	}

	/**
	 * @param respirationRateTrendValue the respirationRateTrendValue to set
	 */
	public void setRespirationRateTrendValue(String respirationRateTrendValue) {
		this.respirationRateTrendValue = respirationRateTrendValue;
	}

	/**
	 * @return the s3Trend
	 */
	public String getS3Trend() {
		return s3Trend;
	}

	/**
	 * @param s3Trend the s3Trend to set
	 */
	public void setS3Trend(String s3Trend) {
		this.s3Trend = s3Trend;
	}

	/**
	 * @return the tidalVolumeTrend
	 */
	public String getTidalVolumeTrend() {
		return tidalVolumeTrend;
	}

	/**
	 * @param tidalVolumeTrend the tidalVolumeTrend to set
	 */
	public void setTidalVolumeTrend(String tidalVolumeTrend) {
		this.tidalVolumeTrend = tidalVolumeTrend;
	}

	/**
	 * @return the thoracicImpedanceTrend
	 */
	public String getThoracicImpedanceTrend() {
		return thoracicImpedanceTrend;
	}

	/**
	 * @param thoracicImpedanceTrend the thoracicImpedanceTrend to set
	 */
	public void setThoracicImpedanceTrend(String thoracicImpedanceTrend) {
		this.thoracicImpedanceTrend = thoracicImpedanceTrend;
	}

	/**
	 * @return the readingCompliance
	 */
	public String getReadingCompliance() {
		return readingCompliance;
	}

	/**
	 * @param readingCompliance the readingCompliance to set
	 */
	public void setReadingCompliance(String readingCompliance) {
		this.readingCompliance = readingCompliance;
	}

	/**
	 * @return the thresholdAlarms
	 */
	public String getThresholdAlarms() {
		return thresholdAlarms;
	}

	/**
	 * @param thresholdAlarms the thresholdAlarms to set
	 */
	public void setThresholdAlarms(String thresholdAlarms) {
		this.thresholdAlarms = thresholdAlarms;
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
	 * @return the qtTrend
	 */
	public String getQtTrend() {
		return qtTrend;
	}

	/**
	 * @param qtTrend the qtTrend to set
	 */
	public void setQtTrend(String qtTrend) {
		this.qtTrend = qtTrend;
	}

	/**
	 * @return the qtWidth
	 */
	public String getQtWidth() {
		return qtWidth;
	}

	/**
	 * @param qtWidth the qtWidth to set
	 */
	public void setQtWidth(String qtWidth) {
		this.qtWidth = qtWidth;
	}

	/**
	 * @return the qrsTrend
	 */
	public String getQrsTrend() {
		return qrsTrend;
	}

	/**
	 * @param qrsTrend the qrsTrend to set
	 */
	public void setQrsTrend(String qrsTrend) {
		this.qrsTrend = qrsTrend;
	}

	/**
	 * @return the qrsWidth
	 */
	public String getQrsWidth() {
		return qrsWidth;
	}

	/**
	 * @param qrsWidth the qrsWidth to set
	 */
	public void setQrsWidth(String qrsWidth) {
		this.qrsWidth = qrsWidth;
	}

	/**
	 * @return the dailyCompliance
	 */
	public String getDailyCompliance() {
		return dailyCompliance;
	}

	/**
	 * @param dailyCompliance the dailyCompliance to set
	 */
	public void setDailyCompliance(String dailyCompliance) {
		this.dailyCompliance = dailyCompliance;
	}

	/**
	 * @return the percentage
	 */
	public String getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(String percentage) {
		this.percentage = percentage;
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
	 * @return the measurementId
	 */
	public String getMeasurementId() {
		return measurementId;
	}

	/**
	 * @param measurementId the measurementId to set
	 */
	public void setMeasurementId(String measurementId) {
		this.measurementId = measurementId;
	}

	
	/**
	 * @return the abnormalRhythm
	 */
	public String getAbnormalRhythm() {
		return abnormalRhythm;
	}

	/**
	 * @param abnormalRhythm the abnormalRhythm to set
	 */
	public void setAbnormalRhythm(String abnormalRhythm) {
		this.abnormalRhythm = abnormalRhythm;
	}

	@Override
	public String toString() {
		return "PatientRecord [institutionId=" + institutionId + ", patientId=" + patientId + ", chfPatientId="
				+ chfPatientId + ", lastRead=" + lastRead + ", heartRateTrend=" + heartRateTrend
				+ ", heartRateTrendValue=" + heartRateTrendValue + ", qtTrend=" + qtTrend + ", qtWidth=" + qtWidth
				+ ", qrsTrend=" + qrsTrend + ", qrsWidth=" + qrsWidth + ", respirationRateTrend=" + respirationRateTrend
				+ ", respirationRateTrendValue=" + respirationRateTrendValue + ", s3Trend=" + s3Trend
				+ ", tidalVolumeTrend=" + tidalVolumeTrend + ", thoracicImpedanceTrend=" + thoracicImpedanceTrend
				+ ", readingCompliance=" + readingCompliance + ", dailyCompliance=" + dailyCompliance + ", percentage="
				+ percentage + ", systemId=" + systemId + ", measurementId=" + measurementId + ", thresholdAlarms="
				+ thresholdAlarms + ", abnormalRhythm=" + abnormalRhythm + "]";
	}

	
}