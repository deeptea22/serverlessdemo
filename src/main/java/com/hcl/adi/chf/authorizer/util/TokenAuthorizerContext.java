package com.hcl.adi.chf.authorizer.util;

/**
 * Object representation of input for implementation of an API Gateway custom
 * authorizer of type TOKEN
 *
 * @author AyushRa
 */
public final class TokenAuthorizerContext {
	private String type;
	private String authorizationToken;
	private String methodArn;

	/**
	 * JSON input is deserialized into this object representation
	 *
	 * @param type (Static value) - TOKEN
	 * @param authorizationToken - Incoming bearer token sent by a client
	 * @param methodArn - The API Gateway method ARN that a client requested
	 */
	public TokenAuthorizerContext(String type, String authorizationToken, String methodArn) {
		this.type = type;
		this.authorizationToken = authorizationToken;
		this.methodArn = methodArn;
	}

	public TokenAuthorizerContext() {
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type - the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the authorizationToken
	 */
	public String getAuthorizationToken() {
		return authorizationToken;
	}

	/**
	 * @param authorizationToken - the authorizationToken to set
	 */
	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}

	/**
	 * @return the methodArn
	 */
	public String getMethodArn() {
		return methodArn;
	}

	/**
	 * @param methodArn - the methodArn to set
	 */
	public void setMethodArn(String methodArn) {
		this.methodArn = methodArn;
	}
}