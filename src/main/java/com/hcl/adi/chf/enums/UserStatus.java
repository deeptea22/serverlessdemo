package com.hcl.adi.chf.enums;

/**
 * Enumeration to define status of admins and clinicians
 *
 * @author AyushRa
 */
public enum UserStatus {
	A("Active"), I("Inactive"), II("Institution Inactive"), LI("Login Inactive");

	private String statusDescription;

	UserStatus(final String description) {
		this.statusDescription = description;
	}

	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}
}