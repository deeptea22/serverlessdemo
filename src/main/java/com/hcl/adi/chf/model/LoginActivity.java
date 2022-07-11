package com.hcl.adi.chf.model;

/**
 * Model class for login activity
 *
 * @author DivyaAg
 */
public class LoginActivity {
	private String emailId;
	private String type;
	private String loginAttempt;

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the loginAttempt
	 */
	public String getLoginAttempt() {
		return loginAttempt;
	}

	/**
	 * @param loginAttempt
	 *            the loginAttempt to set
	 */
	public void setLoginAttempt(String loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

	@Override
	public String toString() {
		return "LoginActivity [emailId=" + emailId + ", type=" + type + ", loginAttempt=" + loginAttempt + "]";
	}
}