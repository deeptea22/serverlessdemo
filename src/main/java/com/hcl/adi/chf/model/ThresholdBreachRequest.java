package com.hcl.adi.chf.model;

/**
 * Model class for request of clear threshold 
 * breach count 
 *
 * @author DivyaAg
 */
public final class ThresholdBreachRequest {

	private int patientId;
	private String emailId;
	private int institutionId;
	/**
	 * @return the patientId
	 */
	public int getPatientId() {
		return patientId;
	}
	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return the institutionId
	 */
	public int getInstitutionId() {
		return institutionId;
	}
	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}
	
	@Override
	public String toString() {
		return "ThresholdBreachRequest [patientId=" + patientId + ", emailId=" + emailId + ", institutionId="
				+ institutionId + "]";
	}
	
	

}
