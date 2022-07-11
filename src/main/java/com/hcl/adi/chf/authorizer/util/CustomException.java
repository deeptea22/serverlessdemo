package com.hcl.adi.chf.authorizer.util;

import javax.ws.rs.core.Response.Status;

/**
 * Custom exception class for lambda custom authorizer
 *
 * @author AyushRa
 */
public final class CustomException extends Exception {
	private static final long serialVersionUID = 1L;
	private Status httpStatus;
	private String errorReason;
	private String message;

	/**
	 * @param httpStatus
	 * @param errorReason
	 * @param message
	 */
	public CustomException(Status httpStatus, String errorReason, String message) {
		super(errorReason + message);
		this.httpStatus = httpStatus;
		this.errorReason = errorReason;
		this.message = message;
	}

	/**
	 * @param httpStatus
	 * @param message
	 */
	public CustomException(Status httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.message = message;
	}

	/**
	 * @return the httpStatus
	 */
	public Status getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(Status httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the errorReason
	 */
	public String getErrorReason() {
		return errorReason;
	}

	/**
	 * @param errorReason the errorReason to set
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}