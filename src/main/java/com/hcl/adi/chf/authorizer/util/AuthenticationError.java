package com.hcl.adi.chf.authorizer.util;

/**
 * This class contains messages related to JWT token authentication error
 *
 * @author AyushRa
 */
public final class AuthenticationError {
	private AuthenticationError() {
		// private constructor
	}

	public static final String NOT_VALID_JSON_WEB_TOKEN = "Not a valid json web token";
	public static final String TOKEN_EXPIRED = " Aws Jwt Token has expired.";
}