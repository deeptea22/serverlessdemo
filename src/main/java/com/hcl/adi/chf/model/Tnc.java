package com.hcl.adi.chf.model;

/**
 * Model class to return tnc details only instead of maximum details of an user
 *
 * @author Shivendra
 */
public final class Tnc {
	private String emailId;
	private String userType;
	private String isTncAccepted;
	private String tncText;
	private String isTncUpdated;
	private boolean isFirstLogin;
	private boolean isPwdUpdated;

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
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the isTncAccepted
	 */
	public String getIsTncAccepted() {
		return isTncAccepted;
	}

	/**
	 * @param isTncAccepted
	 *            the isTncAccepted to set
	 */
	public void setIsTncAccepted(String isTncAccepted) {
		this.isTncAccepted = isTncAccepted;
	}

	/**
	 * @return the tncText
	 */
	public String getTncText() {
		return tncText;
	}

	/**
	 * @param tncText
	 *            the tncText to set
	 */
	public void setTncText(String tncText) {
		this.tncText = tncText;
	}

	/**
	 * @return the isTncUpdated
	 */
	public String getIsTncUpdated() {
		return isTncUpdated;
	}

	/**
	 * @param isTncUpdated
	 *            the isTncUpdated to set
	 */
	public void setIsTncUpdated(String isTncUpdated) {
		this.isTncUpdated = isTncUpdated;
	}

	/**
	 * @return the isFirstLogin
	 */
	public boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	/**
	 * @param isFirstLogin
	 *            the isFirstLogin to set
	 */
	public void setIsFirstLogin(boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	/**
	 * @return the isPwdUpdated
	 */
	public boolean getIsPwdUpdated() {
		return isPwdUpdated;
	}

	/**
	 * @param isPwdUpdated
	 *            the isPwdUpdated to set
	 */
	public void setIsPwdUpdated(boolean isPwdUpdated) {
		this.isPwdUpdated = isPwdUpdated;
	}

	@Override
	public String toString() {
		return "Tnc [emailId=" + emailId + ", userType=" + userType + ", isTncAccepted=" + isTncAccepted + ", tncText="
				+ tncText + ", isTncUpdated=" + isTncUpdated + ", isFirstLogin=" + isFirstLogin + ", isPwdUpdated="
				+ isPwdUpdated + "]";
	}
}