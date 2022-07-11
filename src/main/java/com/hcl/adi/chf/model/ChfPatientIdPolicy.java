package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;

/**
 * Model class for chf_patient_id_policy
 *
 * @author AyushRa
 */
public final class ChfPatientIdPolicy {
	private Integer pkId;
	private Integer institutionId;
	private String isEMREHREnabled;
	private String createdBy;
	private Date createdTimestamp;
	private List<Policy> policyList;

	/**
	 * @return the pkId
	 */
	public Integer getPkId() {
		return pkId;
	}

	/**
	 * @param pkId
	 *            the pkId to set
	 */
	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}

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
	 * @return the isEMREHREnabled
	 */
	public String getIsEMREHREnabled() {
		return isEMREHREnabled;
	}

	/**
	 * @param isEMREHREnabled
	 *            the isEMREHREnabled to set
	 */
	public void setIsEMREHREnabled(String isEMREHREnabled) {
		this.isEMREHREnabled = isEMREHREnabled;
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
	 * @return the policyList
	 */
	public List<Policy> getPolicyList() {
		return policyList;
	}

	/**
	 * @param policyList
	 *            the policyList to set
	 */
	public void setPolicyList(List<Policy> policyList) {
		this.policyList = policyList;
	}

	@Override
	public String toString() {
		return "ChfPatientIdPolicy [pkId=" + pkId + ", institutionId=" + institutionId + ", isEMREHREnabled="
				+ isEMREHREnabled + ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp
				+ ", policyList=" + policyList + "]";
	}

	/**
	 * static inner class to store policy details as json array
	 */
	public static final class Policy {
		private String label;
		private String labelRule;
		private String labelSeparator;
		private Short labelSequence;

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
		 * @return the labelRule
		 */
		public String getLabelRule() {
			return labelRule;
		}

		/**
		 * @param labelRule
		 *            the labelRule to set
		 */
		public void setLabelRule(String labelRule) {
			this.labelRule = labelRule;
		}

		/**
		 * @return the labelSeparator
		 */
		public String getLabelSeparator() {
			return labelSeparator;
		}

		/**
		 * @param labelSeparator
		 *            the labelSeparator to set
		 */
		public void setLabelSeparator(String labelSeparator) {
			this.labelSeparator = labelSeparator;
		}

		/**
		 * @return the labelSequence
		 */
		public Short getLabelSequence() {
			return labelSequence;
		}

		/**
		 * @param labelSequence
		 *            the labelSequence to set
		 */
		public void setLabelSequence(Short labelSequence) {
			this.labelSequence = labelSequence;
		}

		@Override
		public String toString() {
			return "Policy [label=" + label + ", labelRule=" + labelRule + ", labelSeparator=" + labelSeparator
					+ ", labelSequence=" + labelSequence + "]";
		}
	}
}