package com.hcl.adi.chf.model;

import java.util.List;

/**
 * Model class for institution admin dashBoard data
 *
 * @author DivyaAg
 */
public final class IADashboardResponse {
	private Institution institution;
	private List<Admins> admins;
	private List<Clinician> clinician;

	/**
	 * @return the institution
	 */
	public Institution getInstitution() {
		return institution;
	}

	/**
	 * @param institution
	 *            the institution to set
	 */
	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	/**
	 * @return the admins
	 */
	public List<Admins> getAdmins() {
		return admins;
	}

	/**
	 * @param admins
	 *            the admins to set
	 */
	public void setAdmins(List<Admins> admins) {
		this.admins = admins;
	}

	/**
	 * @return the clinician
	 */
	public List<Clinician> getClinician() {
		return clinician;
	}

	/**
	 * @param clinician
	 *            the clinician to set
	 */
	public void setClinician(List<Clinician> clinician) {
		this.clinician = clinician;
	}

	@Override
	public String toString() {
		return "IADashboardResponse [institution=" + institution + ", admins=" + admins + ", clinician=" + clinician
				+ "]";
	}
}