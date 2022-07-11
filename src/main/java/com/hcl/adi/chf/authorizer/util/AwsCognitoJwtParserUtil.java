package com.hcl.adi.chf.authorizer.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Objects;

import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class will parse the AWS JWT token and will provide the methods to
 * validate the structure of the token and to get header, payload, signature,
 * claims etc. from the token. Also, this class contains a method to get the url
 * of jwks(json web key sets) which would be used to verify the signature of
 * Cognito JWT token
 *
 *
 * @author AyushRa
 */
public final class AwsCognitoJwtParserUtil {
	private static final String ISS = "iss";
	private static final String JWK_URL_SUFFIX = "/.well-known/jwks.json";

	private static final int HEADER = 0;
	private static final int PAYLOAD = 1;
	private static final int SIGNATURE = 2;
	private static final int JWT_PARTS = 3;

	private AwsCognitoJwtParserUtil() {
		// private constructor
	}

	/**
	 * This method will return the header of the JWT token as a JSON object
	 *
	 * @param jwt
	 * @return - AWS JWT header as a JsonObject
	 */
	public static JsonObject getHeader(final String jwt) throws CustomException {
		try {
			validateJWTStructure(jwt);
			String header = jwt.split("\\.")[HEADER];
			final byte[] headerBytes = Base64.getUrlDecoder().decode(header);
			final String headerString = new String(headerBytes, "UTF-8");
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(headerString);

			return jsonObject;

		} catch (UnsupportedEncodingException e) {
			throw new CustomException(Status.UNAUTHORIZED, AuthenticationError.NOT_VALID_JSON_WEB_TOKEN, jwt);
		}
	}

	/**
	 * This method will return the payload of the JWT token as a JSON object
	 *
	 * @param jwt
	 * @return - AWS JWT payload as a JsonObject
	 */
	public static JsonObject getPayload(final String jwt) throws CustomException {
		try {
			validateJWTStructure(jwt);
			final String payload = jwt.split("\\.")[PAYLOAD];
			final byte[] payloadBytes = Base64.getUrlDecoder().decode(payload);
			final String payloadString = new String(payloadBytes, "UTF-8");
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject) jsonParser.parse(payloadString);

			return jsonObject;

		} catch (UnsupportedEncodingException e) {
			throw new CustomException(Status.UNAUTHORIZED, AuthenticationError.NOT_VALID_JSON_WEB_TOKEN, jwt);
		}
	}

	/**
	 * This method will return the signature of the JWT token as a String
	 *
	 * @param jwt
	 * @return - AWS JWT signature as a String
	 */
	public static String getSignature(final String jwt) throws CustomException {
		try {
			validateJWTStructure(jwt);
			final String signature = jwt.split("\\.")[SIGNATURE];
			final byte[] signatureBytes = Base64.getUrlDecoder().decode(signature);

			return new String(signatureBytes, "UTF-8");

		} catch (final Exception e) {
			throw new CustomException(Status.UNAUTHORIZED, AuthenticationError.NOT_VALID_JSON_WEB_TOKEN, jwt);
		}
	}

	/**
	 * This method will return a claim from JWT token's payload as a String
	 *
	 * @param jwt
	 * @param claimName
	 * @return - claim from the JWT token's payload as a String
	 */
	public static String getClaim(final String jwt, final String claimName) throws CustomException {
		try {
			final JsonObject payload = getPayload(jwt);
			final Object claimValue = payload.get(claimName);

			if (claimValue != null) {
				return claimValue.toString();
			}

		} catch (final Exception e) {
			throw new CustomException(Status.UNAUTHORIZED, AuthenticationError.NOT_VALID_JSON_WEB_TOKEN, jwt);
		}

		return null;
	}

	/**
	 * This method will return Json Web Key URL which would be used to verify
	 * the signature of Cognito JWT token
	 *
	 * @param token
	 * @return - Json Web Key URL
	 * @throws CustomException
	 */
	public static String getJsonWebKeyURL(final String token) throws CustomException {
		JsonObject payload = getPayload(token);
		JsonElement issJsonElement = payload.get(ISS);

		if (Objects.isNull(issJsonElement)) {
			throw new CustomException(Status.UNAUTHORIZED, AuthenticationError.NOT_VALID_JSON_WEB_TOKEN,
					payload.toString());
		}

		String issString = issJsonElement.getAsString();
		String jwkURl = issString + JWK_URL_SUFFIX;

		return jwkURl;
	}

	/**
	 * This method will return the cognito pool id after extracting from iss url
	 *
	 * @param issUrl
	 * @return - cognito pool id from the token issuer URL
	 */
	public static String getUserPoolIdFromPayload(final String issUrl) {
		String[] issArray = issUrl.split("amazonaws.com/");
		return issArray[1];
	}

	/**
	 * This method will check whether JWT token is structurally valid or not
	 *
	 * @param jwt
	 * @throws CustomException
	 */
	public static void validateJWTStructure(final String jwt) throws CustomException {
		// Check if the JWT token has three parts or not
		final String[] jwtParts = jwt.split("\\.");

		if (jwtParts.length != JWT_PARTS) {
			throw new CustomException(Status.UNAUTHORIZED, AuthenticationError.NOT_VALID_JSON_WEB_TOKEN, jwt);
		}
	}
}