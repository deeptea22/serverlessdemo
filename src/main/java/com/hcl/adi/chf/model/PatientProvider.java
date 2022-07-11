package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;

/**
 * Model class for healthcare_provider
 * and patient_provider_mapping table
 * @author DivyaAg
 */
public final class PatientProvider {

	
	private String chfPatientId;
	private Integer institutionId;
	private Integer providerId;
	private List<Provider> providers;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;
	
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
	 * @return the providerId
	 */
	public Integer getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return the providers
	 */
	public List<Provider> getProviders() {
		return providers;
	}

	/**
	 * @param providers the providers to set
	 */
	public void setProviders(List<Provider> providers) {
		this.providers = providers;
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

	
	
	public static final class Provider {
		private int providerId;
		private boolean isDefault;
		
		private String providerName;
		private String hospitalAffilation;
		private String doctorName;
		private String doctorContactNumber;
		private String doctorEmail;
		private String otherContactNumber;
		
		/**
		 * @return the providerId
		 */
		public int getProviderId() {
			return providerId;
		}
		/**
		 * @param providerId the providerId to set
		 */
		public void setProviderId(int providerId) {
			this.providerId = providerId;
		}
		/**
		 * @return the isDefault
		 */
		public boolean isDefault() {
			return isDefault;
		}
		/**
		 * @param isDefault the isDefault to set
		 */
		public void setDefault(boolean isDefault) {
			this.isDefault = isDefault;
		}
		
		
		
		/**
		 * @return the providerName
		 */
		public String getProviderName() {
			return providerName;
		}
		/**
		 * @param providerName the providerName to set
		 */
		public void setProviderName(String providerName) {
			this.providerName = providerName;
		}
		/**
		 * @return the hospitalAffilation
		 */
		public String getHospitalAffilation() {
			return hospitalAffilation;
		}
		/**
		 * @param hospitalAffilation the hospitalAffilation to set
		 */
		public void setHospitalAffilation(String hospitalAffilation) {
			this.hospitalAffilation = hospitalAffilation;
		}
		/**
		 * @return the doctorName
		 */
		public String getDoctorName() {
			return doctorName;
		}
		/**
		 * @param doctorName the doctorName to set
		 */
		public void setDoctorName(String doctorName) {
			this.doctorName = doctorName;
		}
		/**
		 * @return the doctorContactNumber
		 */
		public String getDoctorContactNumber() {
			return doctorContactNumber;
		}
		/**
		 * @param doctorContactNumber the doctorContactNumber to set
		 */
		public void setDoctorContactNumber(String doctorContactNumber) {
			this.doctorContactNumber = doctorContactNumber;
		}
		/**
		 * @return the doctorEmail
		 */
		public String getDoctorEmail() {
			return doctorEmail;
		}
		/**
		 * @param doctorEmail the doctorEmail to set
		 */
		public void setDoctorEmail(String doctorEmail) {
			this.doctorEmail = doctorEmail;
		}
		/**
		 * @return the otherContactNumber
		 */
		public String getOtherContactNumber() {
			return otherContactNumber;
		}
		/**
		 * @param otherContactNumber the otherContactNumber to set
		 */
		public void setOtherContactNumber(String otherContactNumber) {
			this.otherContactNumber = otherContactNumber;
		}
		
		
		@Override
		public String toString() {
			return "Provider [providerId=" + providerId + ", isDefault=" + isDefault + ", providerName=" + providerName
					+ ", hospitalAffilation=" + hospitalAffilation + ", doctorName=" + doctorName
					+ ", doctorContactNumber=" + doctorContactNumber + ", doctorEmail=" + doctorEmail
					+ ", otherContactNumber=" + otherContactNumber + "]";
		}
				
	}
	
	

	
	@Override
	public String toString() {
		return "PatientProvider [chfPatientId=" + chfPatientId + ", institutionId=" + institutionId + ", providerId="
				+ providerId + ", providers=" + providers + ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
		
		
	
	
}
