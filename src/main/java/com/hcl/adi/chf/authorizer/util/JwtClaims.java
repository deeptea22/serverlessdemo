package com.hcl.adi.chf.authorizer.util;

/**
 * This class will return the name of claims that we will get in the token's
 * payload. Since all required claims(iss, exp) would be verified internally by
 * Nimbus Jose library, so we are validating only cognito:groups claim for our
 * use case
 *
 * @author AyushRa
 */
public final class JwtClaims {
	private JwtClaims() {
		// private constructor
	}

	public static final String COGNITO_GROUPS = "cognito:groups";

	/*
	 * The issuer (iss) claim should match your user pool. For example, a user
	 * pool created in the us-east-1 region will have an iss value of:
	 * https://cognito-idp.us-east-1.amazonaws.com/<userpoolID>
	 */
	public static final String ISS = "iss";

	/*
	 * The audience (aud) claim will be available in the id token and it should
	 * match the app client ID created in the Amazon Cognito user pool
	 */
	public static final String AUD = "aud";

	/*
	 * The client_id claim will be available in the access token and it should
	 * match the app client ID created in the Amazon Cognito user pool
	 */
	public static final String CLIENT_ID = "client_id";

	/*
	 * If you are only accepting the access token in your web APIs, its value
	 * must be access. If you are only using the ID token, its value must be id
	 */
	public static final String TOKEN_USE = "token_use";

	public static final String AUTH_TIME = "auth_time";
	public static final String COGNITO_USERNAME = "cognito:username";
	public static final String EMAIL = "email";
	public static final String EMAIL_VERIFIED = "email_verified";
	public static final String EVENT_ID = "event_id";
	public static final String EXP = "exp";
	public static final String IAT = "iat";
	public static final String SUB = "sub";
}