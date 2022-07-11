package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.authorizer.util.AuthPolicy;
import com.hcl.adi.chf.authorizer.util.AwsCognitoJwtValidatorUtil;
import com.hcl.adi.chf.authorizer.util.JwtClaims;
import com.hcl.adi.chf.authorizer.util.TokenAuthorizerContext;
import com.hcl.adi.chf.authorizer.util.ValidLambdaListByGroup;
import com.hcl.adi.chf.util.Constants;
import com.nimbusds.jwt.JWTClaimsSet;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Lambda custom authorizer for API Gateway
 *
 * @author AyushRa
 */
public final class LambdaCustomAuthorizer implements RequestHandler<TokenAuthorizerContext, AuthPolicy> {
	private static final Logger LOGGER = LogManager.getLogger(LambdaCustomAuthorizer.class.getName());
	private AuthPolicy authPolicy = null;
	private JWTClaimsSet jwtClaimsSet = null;
	private String principalId = null;
	private boolean returnAllowPolicy = false;

	@Override
	public AuthPolicy handleRequest(final TokenAuthorizerContext input, final Context context) {
		LOGGER.info(":::::::Request start for lambda custom authorizer:::::::");

		String token = input.getAuthorizationToken();
		String methodArn = input.getMethodArn();

		LOGGER.info("Input Token: " + token);
		LOGGER.info("Method Arn: " + methodArn);

		String[] methodArnPartials = methodArn.split(":");

		String region = methodArnPartials[3];
		String awsAccountId = methodArnPartials[4];
		String apiGatewayArn = methodArnPartials[5];

		LOGGER.info("Region: " + region);
		LOGGER.info("AWS Account Id: " + awsAccountId);
		LOGGER.info("API Gateway ARN: " + apiGatewayArn);

		String[] apiGatewayArnPartials = apiGatewayArn.split("/");

		String restApiId = apiGatewayArnPartials[0];
		String stage = apiGatewayArnPartials[1];
		String httpMethod = apiGatewayArnPartials[2];
		String resource = ""; // root resource
		if (apiGatewayArnPartials.length == 4) {
			resource = apiGatewayArnPartials[3];
		}

		LOGGER.info("Rest API Id: " + restApiId);
		LOGGER.info("Stage: " + stage);
		LOGGER.info("HTTP Method: " + httpMethod);
		LOGGER.info("Resource: " + resource);

		try {
			/*
			 * validate the incoming token. If the token is valid, a policy
			 * should be generated which will allow or deny access to the client
			 */
			jwtClaimsSet = AwsCognitoJwtValidatorUtil.validateAWSJwtToken(token);
			JSONObject jwtClaimsSetJsonObject = jwtClaimsSet.toJSONObject();

			LOGGER.info("JWT Claim Set Json: " + jwtClaimsSetJsonObject);

			JSONArray cognitoGroupJsonArray = (JSONArray) jwtClaimsSetJsonObject.get(JwtClaims.COGNITO_GROUPS);

			for (int i = 0; i < cognitoGroupJsonArray.size(); i++) {
				LOGGER.info("Cognito group in incoming token: " + cognitoGroupJsonArray.get(i));
			}

			if (cognitoGroupJsonArray.contains(Constants.COGNITO_POOL_SUPER_ADMIN_GROUP)
					&& ValidLambdaListByGroup.SUPER_ADMIN.contains(resource)) {
				returnAllowPolicy = true;

			} else if (cognitoGroupJsonArray.contains(Constants.COGNITO_POOL_INSTITUTION_ADMIN_GROUP)
					&& ValidLambdaListByGroup.INSTITUTION_ADMIN.contains(resource)) {
				returnAllowPolicy = true;

			} else if (cognitoGroupJsonArray.contains(Constants.COGNITO_POOL_WEB_CLINICIAN_GROUP)
					&& ValidLambdaListByGroup.WEB_CLINICIAN.contains(resource)) {
				returnAllowPolicy = true;

			} else if (cognitoGroupJsonArray.contains(Constants.COGNITO_POOL_MOBILE_CLINICIAN_GROUP)
					&& ValidLambdaListByGroup.MOBILE_USER.contains(resource)) {
				returnAllowPolicy = true;

			} else {
				returnAllowPolicy = false;
			}

			// Both "aud" claim of id token & "client_id" claim of access token
			// contains the app client ID created in the cognito user pool

			if ("id".equals((String) jwtClaimsSetJsonObject.get(JwtClaims.TOKEN_USE))) {
				principalId = (String) jwtClaimsSetJsonObject.get(JwtClaims.AUD);

			} else if ("access".equals((String) jwtClaimsSetJsonObject.get(JwtClaims.TOKEN_USE))) {
				principalId = (String) jwtClaimsSetJsonObject.get(JwtClaims.CLIENT_ID);
			}

			if (returnAllowPolicy) {
				LOGGER.info(":::::::Returning allow policy:::::::");

				authPolicy = getAllowPolicy(principalId, region, awsAccountId, restApiId, stage,
						AuthPolicy.HttpMethod.ALL, resource);

			} else {
				LOGGER.info(":::::::Returning deny policy:::::::");

				authPolicy = getDenyPolicy(principalId, region, awsAccountId, restApiId, stage,
						AuthPolicy.HttpMethod.ALL, resource);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			authPolicy = getDenyPolicy(principalId, region, awsAccountId, restApiId, stage, AuthPolicy.HttpMethod.ALL,
					resource);
		}

		LOGGER.info("Returned Auth Policy: " + authPolicy);
		LOGGER.info(":::::::Request completed for lambda custom authorizer:::::::");

		return authPolicy;
	}

	private AuthPolicy getAllowPolicy(final String policyPrincipalId, final String region, final String awsAccountId,
			final String restApiId, final String stage, final AuthPolicy.HttpMethod httpMethod, final String resource) {
		return new AuthPolicy(policyPrincipalId, AuthPolicy.PolicyDocument.getAllowOnePolicy(region, awsAccountId,
				restApiId, stage, httpMethod, resource));
	}

	private AuthPolicy getDenyPolicy(final String policyPrincipalId, final String region, final String awsAccountId,
			final String restApiId, final String stage, final AuthPolicy.HttpMethod httpMethod, final String resource) {
		return new AuthPolicy(policyPrincipalId, AuthPolicy.PolicyDocument.getDenyOnePolicy(region, awsAccountId,
				restApiId, stage, httpMethod, resource));
	}
}