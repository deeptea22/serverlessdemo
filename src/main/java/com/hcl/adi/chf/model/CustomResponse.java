package com.hcl.adi.chf.model;

/**
 * Model class to return response with code and description
 *
 * @author AyushRa
 */
public class CustomResponse {
	private int statusCode;
	private String description;

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CustomResponse [statusCode=" + statusCode + ", description=" + description + "]";
	}
}