package com.hcl.adi.chf.dummy;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hcl.adi.chf.authorizer.util.AuthPolicy;
import com.hcl.adi.chf.authorizer.util.TokenAuthorizerContext;

/**
 * Lambda custom authorizer for API Gateway
 *
 * @author AyushRa
 */
public final class LambdaCustomAuthorizerTest implements RequestHandler<TokenAuthorizerContext, AuthPolicy> {
	private static final Logger LOGGER = LogManager.getLogger(LambdaCustomAuthorizerTest.class.getName());
	private static final int PAYLOAD = 1;
	private static final int JWT_PARTS = 3;
	private static final String ISS = "iss";
	private static String principalId = null;
	private static String region = null;
	private static String awsAccountId = null;

	@Override
	public AuthPolicy handleRequest(final TokenAuthorizerContext input, final Context context) {
		LOGGER.info(":::::::Request start for lambda custom authorizer:::::::");
		LOGGER.info("Input: " + input);

		String token = input.getAuthorizationToken();

		String methodArn = input.getMethodArn();
		System.out.println("Method Arn: " + methodArn);

		String[] methodArnPartials = methodArn.split(":");

		region = methodArnPartials[3];
		awsAccountId = methodArnPartials[4];
		System.out.println("Region: " + region);
		System.out.println("Account ID: " + awsAccountId);

		String apiGatewayArn = methodArnPartials[5];
		String[] apiGatewayArnPartials = apiGatewayArn.split("/");

		String restApiId = apiGatewayArnPartials[0];
		String stage = apiGatewayArnPartials[1];
		String httpMethod = apiGatewayArnPartials[2];
		String resource = ""; // root resource
		if (apiGatewayArnPartials.length == 4) {
			resource = apiGatewayArnPartials[3];
		}

		System.out.println("Rest API Id: " + restApiId);
		System.out.println("Stage: " + stage);
		System.out.println("HTTP Method: " + httpMethod);
		System.out.println("Resource: " + resource);

		principalId = "5ps3v769bdo05ueca3pf55hcfp";

		if (isValidJWT(token)) {
			// return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, "*", "*"));
			return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowOnePolicy(region, awsAccountId, restApiId, stage, AuthPolicy.HttpMethod.ALL, resource));
		} else {
			// return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getDenyAllPolicy(region, awsAccountId, "*", "*"));
			return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getDenyOnePolicy(region, awsAccountId, restApiId, stage, AuthPolicy.HttpMethod.ALL, resource));
		}
	}	

	/**
	 * Checks if token is a valid JSON Web Token
	 *
	 * @param jwt
	 * @return
	 */
	public static boolean isValidJWT(String jwt) {
		// Check if the the JWT has the three parts
		final String[] jwtParts = jwt.split("\\.");

		if (jwtParts.length != JWT_PARTS) {
			return false;

		} else {
			final String payload = jwt.split("\\.")[PAYLOAD];
			final byte[] payloadBytes = Base64.getUrlDecoder().decode(payload);
			String payloadString;
			try {
				payloadString = new String(payloadBytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return false;
			}

			JsonParser jsonParser = new JsonParser();
			JsonObject payloadJson = (JsonObject) jsonParser.parse(payloadString);

			JsonElement expJsonElement = payloadJson.get("exp");
			if (Objects.isNull(expJsonElement)) {
				return false;
			}

			String expString = expJsonElement.getAsString();

			LOGGER.info(System.currentTimeMillis());
			LOGGER.info(expString);

			Date expDate = new Date(Long.valueOf(expString));
			LOGGER.info("Token Expiry Date is :" + expDate);

			JsonElement issJsonElement = payloadJson.get(ISS);
			if (Objects.isNull(issJsonElement)) {
				return false;
			}

			String issString = issJsonElement.getAsString();
			String userPoolName = getUserPoolFromPayload(issString);

			if (!userPoolName.equals("us-east-1_tswTIQ0gH")) {
				return false;
			}

			if (payloadJson.get("cognito:groups").toString().indexOf("super_admin") != -1) {
				System.out.println("Group: " + payloadJson.get("cognito:groups"));
			} else {
				System.out.println("Hello");
			}

			/*
			 * if(!payloadJson.get("aud").equals("5ps3v769bdo05ueca3pf55hcfp"))
			 * { return false; }
			 */

			return true;
		}
	}

	/**
	 * Get the user pool from the iss url.
	 * 
	 * @param issUrl
	 * @return ISS - token issuer URL.
	 */
	public static String getUserPoolFromPayload(String issUrl) {
		String[] issArray = issUrl.split("amazonaws.com/");
		return issArray[1];
	}

	public static void main(String[] args) {
		TokenAuthorizerContext input = new TokenAuthorizerContext();
		input.setAuthorizationToken("eyJraWQiOiJtWGVnNVZOcStRXC9DUXNMQ3loZ0lHenJweTNxZXBBUXNsUWM1bk0yXC9aQ289IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJiYThmMTc3ZC03NzM1LTRjZjYtYjU4Ny0zYjQ4YmJiM2YyOTgiLCJjb2duaXRvOmdyb3VwcyI6WyJzdXBlcl9hZG1pbiJdLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfdHN3VElRMGdIIiwiY29nbml0bzp1c2VybmFtZSI6ImRpbmVzaC1rdW1hQGhjbC5jb20iLCJjb2duaXRvOnJvbGVzIjpbImFybjphd3M6aWFtOjoxNDU0NzM1NjEyMzc6cm9sZVwvVGVzdFJvbGUiXSwiYXVkIjoiNXBzM3Y3NjliZG8wNXVlY2EzcGY1NWhjZnAiLCJldmVudF9pZCI6IjNlZDRkMmQ0LTA5NzYtNGY3YS04MzZlLTQ1YjMyNGYwMDQwMSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTYwNTA3MTExLCJleHAiOjE1NjA1MTA3MTEsImlhdCI6MTU2MDUwNzExMSwiZW1haWwiOiJkaW5lc2gta3VtYUBoY2wuY29tIn0.FjupRMaaw14pQr5fHFf171OmG3DuxE_MP_7yK2QdVsrnlvumGVVIW3UMRg9CyAg9r8siGhpWVf38M4cDwElRSCRuwvd25Qpk1pc3fjgXKCFysl9sJrDrpyVpkQBo-o5vN2MMHJgC12v-eif-RYugSIz6lR6Vf2UMW5FRqVepEkwzO7YeOklPiWmtuLwR4qd4x22L3JBTHL0JEMMR692QC5tb3j3wpse_mDvbJqTwVXgkKZFme9eEUYmAgLxeEEyC476khhUvmDZsbmcKo1X9vBU_uS81RIw1PIIHEdWhM-arEJXrW9_Fkun4do9ff8G2_yGgmd0cVgxeN29maSc91w");
		input.setMethodArn("arn:aws:execute-api:us-east-1:145473561237:y1e8a0m6zk/dev/POST/createSuperAdmin");
		LambdaCustomAuthorizerTest handler = new LambdaCustomAuthorizerTest();
		handler.handleRequest(input, null);

		// isValidJWT("eyJraWQiOiJtWGVnNVZOcStRXC9DUXNMQ3loZ0lHenJweTNxZXBBUXNsUWM1bk0yXC9aQ289IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJiYThmMTc3ZC03NzM1LTRjZjYtYjU4Ny0zYjQ4YmJiM2YyOTgiLCJjb2duaXRvOmdyb3VwcyI6WyJzdXBlcl9hZG1pbiJdLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfdHN3VElRMGdIIiwiY29nbml0bzp1c2VybmFtZSI6ImRpbmVzaC1rdW1hQGhjbC5jb20iLCJjb2duaXRvOnJvbGVzIjpbImFybjphd3M6aWFtOjoxNDU0NzM1NjEyMzc6cm9sZVwvVGVzdFJvbGUiXSwiYXVkIjoiNXBzM3Y3NjliZG8wNXVlY2EzcGY1NWhjZnAiLCJldmVudF9pZCI6IjA4ZThhOTgyLWI0OTQtNGM0NC05MWI1LTViMjk2MDNlMTMwMiIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTYwNDkwMTcxLCJleHAiOjE1NjA0OTM3NzEsImlhdCI6MTU2MDQ5MDE3MSwiZW1haWwiOiJkaW5lc2gta3VtYUBoY2wuY29tIn0.OZVFsx1DQuzgLhXUnzCYoWDlSlqivNH3ABpwt7zjksGUzoM7lcbkkuhuGSGcn--KytC9YLSpjas3QPFbMfLwJ8QaMO57wLaA-xVJxOc_WB8H9k0UYWlsybBk_bqzJqSjuqiEF1COmUoY3gYzHysMyhKmLXDMHMnhoqGZv2eYx9gge6pR0mo5q1ICKZlbSMBuj-CBhNBUWIdMSjqSs290jMHcJa5RBbvaybY8geU9VAdRFl1wCWqV48qKmXPhS0mlUl0Vep_zapGGL7j6c_K0gUVKisaJhOuKh19wJxrh6I0v60TkOvUzxOtRLZ7fR8A97FKQg_416gZQOPzV96BDPg");
	}
}