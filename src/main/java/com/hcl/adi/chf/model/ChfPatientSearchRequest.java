package com.hcl.adi.chf.model;

public class ChfPatientSearchRequest {

	private int institutionId;
	private String policyField;
	private String policyValue;
	private int pageStartIndex;
	private int pageCount;

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

	/**
	 * @return the policyField
	 */
	public String getPolicyField() {
		return policyField;
	}

	/**
	 * @param policyField the policyField to set
	 */
	public void setPolicyField(String policyField) {
		this.policyField = policyField;
	}

	/**
	 * @return the policyValue
	 */
	public String getPolicyValue() {
		return policyValue;
	}

	/**
	 * @param policyValue the policyValue to set
	 */
	public void setPolicyValue(String policyValue) {
		this.policyValue = policyValue;
	}

	/**
	 * @return the pageStartIndex
	 */
	public int getPageStartIndex() {
		return pageStartIndex;
	}

	/**
	 * @param pageStartIndex the pageStartIndex to set
	 */
	public void setPageStartIndex(int pageStartIndex) {
		this.pageStartIndex = pageStartIndex;
	}

	/**
	 * @return the pageCount
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + institutionId;
		result = prime * result + ((policyField == null) ? 0 : policyField.hashCode());
		result = prime * result + ((policyValue == null) ? 0 : policyValue.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChfPatientSearchRequest other = (ChfPatientSearchRequest) obj;
		if (institutionId != other.institutionId)
			return false;
		if (policyField == null) {
			if (other.policyField != null)
				return false;
		} else if (!policyField.equals(other.policyField))
			return false;
		if (policyValue == null) {
			if (other.policyValue != null)
				return false;
		} else if (!policyValue.equals(other.policyValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChfPatientSearchRequest [institutionId=" + institutionId + ", policyField=" + policyField
				+ ", policyValue=" + policyValue + ", pageStartIndex=" + pageStartIndex + ", pageCount=" + pageCount
				+ "]";
	}

}
