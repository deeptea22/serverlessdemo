package com.hcl.adi.chf.model;

import java.util.Date;

/**
 * Model class to provide data for help center page
 *
 * @author Sandeep Singh
 */
public final class HelpCenterPageText {
	private Integer helpId;
	private String helpTopic;
	private String helpDesc;
	private String helpUrl;
	private String helpType;
	private String createdBy;
	private Date createdTimestamp;
	private String updatedBy;
	private Date updatedTimestamp;

	/**
	 * @return the helpId
	 */
	public Integer getHelpId() {
		return helpId;
	}

	/**
	 * @param helpId the helpId to set
	 */
	public void setHelpId(Integer helpId) {
		this.helpId = helpId;
	}

	/**
	 * @return the helpTopic
	 */
	public String getHelpTopic() {
		return helpTopic;
	}

	/**
	 * @param helpTopic the helpTopic to set
	 */
	public void setHelpTopic(String helpTopic) {
		this.helpTopic = helpTopic;
	}

	/**
	 * @return the helpDesc
	 */
	public String getHelpDesc() {
		return helpDesc;
	}

	/**
	 * @param helpDesc the helpDesc to set
	 */
	public void setHelpDesc(String helpDesc) {
		this.helpDesc = helpDesc;
	}

	/**
	 * @return the helpUrl
	 */
	public String getHelpUrl() {
		return helpUrl;
	}

	/**
	 * @param helpUrl the helpUrl to set
	 */
	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}

	/**
	 * @return the helpType
	 */
	public String getHelpType() {
		return helpType;
	}

	/**
	 * @param helpType the helpType to set
	 */
	public void setHelpType(String helpType) {
		this.helpType = helpType;
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

	@Override
	public String toString() {
		return "HelpCenter [helpId=" + helpId + ", helpTopic=" + helpTopic + ", helpDesc=" + helpDesc + ", helpUrl="
				+ helpUrl + ", helpType=" + helpType + ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", updatedBy=" + updatedBy + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
}