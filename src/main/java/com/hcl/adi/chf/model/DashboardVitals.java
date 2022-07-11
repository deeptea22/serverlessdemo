package com.hcl.adi.chf.model;

/**
 * Model class to render values on top widget of clinician's landing page
 *
 * @author Sandeep Singh
 */
public final class DashboardVitals {
	private String patientId;
	private String institutionId;
	private Properties patientWatchList;
	private Properties patientThresholds;
	private Properties patientActions;
	private Properties newReading;
	private int compliancePeriod;

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
	 * @return the patientWatchList
	 */
	public Properties getPatientWatchList() {
		return patientWatchList;
	}

	/**
	 * @param patientWatchList the patientWatchList to set
	 */
	public void setPatientWatchList(Properties patientWatchList) {
		this.patientWatchList = patientWatchList;
	}

	/**
	 * @return the patientThresholds
	 */
	public Properties getPatientThresholds() {
		return patientThresholds;
	}

	/**
	 * @param patientThresholds the patientThresholds to set
	 */
	public void setPatientThresholds(Properties patientThresholds) {
		this.patientThresholds = patientThresholds;
	}

	/**
	 * @return the patientActions
	 */
	public Properties getPatientActions() {
		return patientActions;
	}

	/**
	 * @param patientActions the patientActions to set
	 */
	public void setPatientActions(Properties patientActions) {
		this.patientActions = patientActions;
	}

	/**
	 * @return the newReading
	 */
	public Properties getNewReading() {
		return newReading;
	}

	/**
	 * @param newReading the newReading to set
	 */
	public void setNewReading(Properties newReading) {
		this.newReading = newReading;
	}

	@Override
	public String toString() {
		return "PatientVital [patientId=" + patientId + ", institutionId=" + institutionId + ", patientWatchList="
				+ patientWatchList + ", patientThresholds=" + patientThresholds + ", patientActions=" + patientActions
				+ ", newReading=" + newReading + "]";
	}

	/**
	 * @return the compliancePeriod
	 */
	public int getCompliancePeriod() {
		return compliancePeriod;
	}

	/**
	 * @param compliancePeriod the compliancePeriod to set
	 */
	public void setCompliancePeriod(int compliancePeriod) {
		this.compliancePeriod = compliancePeriod;
	}
}