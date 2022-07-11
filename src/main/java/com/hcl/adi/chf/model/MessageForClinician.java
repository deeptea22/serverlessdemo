package com.hcl.adi.chf.model;

/**
 * Model class to send message for clinician into SQS
 *
 * @author AyushRa
 */
public class MessageForClinician {
	private Integer institutionId;
	private String updatedBy;
	private String action;

	/**
	 * @return the institutionId
	 */
	public Integer getInstitutionId() {
		return institutionId;
	}

	/**
	 * @param institutionId
	 *            the institutionId to set
	 */
	public void setInstitutionId(Integer institutionId) {
		this.institutionId = institutionId;
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
	public String toString() {
		return "MessageForClinician [institutionId=" + institutionId + ", updatedBy=" + updatedBy + ", action=" + action
				+ "]";
	}
}