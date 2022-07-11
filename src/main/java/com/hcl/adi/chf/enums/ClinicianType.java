package com.hcl.adi.chf.enums;

/**
 * Enumeration to define clinician type
 *
 * @author AyushRa
 */
public enum ClinicianType {
	CL("Clinician"), PY("Physician");

	private String clinicianType;

	ClinicianType(final String type) {
		this.clinicianType = type;
	}

	/**
	 * @return the clinicianType
	 */
	public String getClinicianType() {
		return clinicianType;
	}
}