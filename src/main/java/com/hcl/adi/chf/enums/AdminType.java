package com.hcl.adi.chf.enums;

/**
 * Enumeration to define admin's type
 *
 * @author AyushRa
 */
public enum AdminType {
	SA("Super Admin"), IA("Institution Admin");

	private String adminType;

	AdminType(final String type) {
		this.adminType = type;
	}

	/**
	 * @return the adminType
	 */
	public String getAdminType() {
		return adminType;
	}
}