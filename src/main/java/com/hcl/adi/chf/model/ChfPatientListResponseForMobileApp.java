package com.hcl.adi.chf.model;

import java.util.List;

/**
 * Model class to return patientIds, chfPatientIds, mrNumbers and total patient
 * count for clincian dashboard in mobile app
 * 
 * @author DivyaAg
 */
public final class ChfPatientListResponseForMobileApp {
	private List<ChfPatientId> chfPatientIdList;
	private int patientCount;

	/**
	 * @return the chfPatientIdList
	 */
	public List<ChfPatientId> getChfPatientIdList() {
		return chfPatientIdList;
	}

	/**
	 * @param chfPatientIdList the chfPatientIdList to set
	 */
	public void setChfPatientIdList(List<ChfPatientId> chfPatientIdList) {
		this.chfPatientIdList = chfPatientIdList;
	}

	/**
	 * @return the patientCount
	 */
	public int getPatientCount() {
		return patientCount;
	}

	/**
	 * @param patientCount the patientCount to set
	 */
	public void setPatientCount(int patientCount) {
		this.patientCount = patientCount;
	}

	@Override
	public String toString() {
		return "ChfPatientListResponseForMobileApp [chfPatientIdList=" + chfPatientIdList + ", patientCount="
				+ patientCount + "]";
	}

	/**
	 * static inner class to store patientIds, chfPatientIds & mrNumbers as json
	 * array
	 */
	public static final class ChfPatientId {
		private int patientId;
		private String chfPatientId;
		private String mrNumber;

		private String gender;
		private String firstName;
		private String lastName;
		private String contactNo;
		private String dob;
		private String address;
		private String zip;
		private String ssn;
		private String patientDetailsJson;

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

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getContactNo() {
			return contactNo;
		}

		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}

		public String getDob() {
			return dob;
		}

		public void setDob(String dob) {
			this.dob = dob;
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

		public String getPatientDetailsJson() {
			return patientDetailsJson;
		}

		public void setPatientDetailsJson(String patientDetailsJson) {
			this.patientDetailsJson = patientDetailsJson;
		}

		@Override
		public String toString() {
			return "ChfPatientId [patientId=" + patientId + ", chfPatientId=" + chfPatientId + ", mrNumber=" + mrNumber
					+ "]";
		}
	}
}