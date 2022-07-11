package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.authorizer.util.AuthPolicy;
import com.hcl.adi.chf.authorizer.util.TokenAuthorizerContext;
import com.hcl.adi.chf.lambda.LambdaCustomAuthorizer;

public class LambdaCustomAuthorizerTest {
	private static final Logger LOGGER = LogManager.getLogger(LambdaCustomAuthorizerTest.class.getName());
	private static TokenAuthorizerContext input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new TokenAuthorizerContext();
		input.setAuthorizationToken(
				"eyJraWQiOiJtWGVnNVZOcStRXC9DUXNMQ3loZ0lHenJweTNxZXBBUXNsUWM1bk0yXC9aQ289IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJiYThmMTc3ZC03NzM1LTRjZjYtYjU4Ny0zYjQ4YmJiM2YyOTgiLCJjb2duaXRvOmdyb3VwcyI6WyJzdXBlcl9hZG1pbiJdLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMS5hbWF6b25hd3MuY29tXC91cy1lYXN0LTFfdHN3VElRMGdIIiwiY29nbml0bzp1c2VybmFtZSI6ImRpbmVzaC1rdW1hQGhjbC5jb20iLCJjb2duaXRvOnJvbGVzIjpbImFybjphd3M6aWFtOjoxNDU0NzM1NjEyMzc6cm9sZVwvVGVzdFJvbGUiXSwiYXVkIjoiNXBzM3Y3NjliZG8wNXVlY2EzcGY1NWhjZnAiLCJldmVudF9pZCI6IjNlZDRkMmQ0LTA5NzYtNGY3YS04MzZlLTQ1YjMyNGYwMDQwMSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNTYwNTA3MTExLCJleHAiOjE1NjA1MTA3MTEsImlhdCI6MTU2MDUwNzExMSwiZW1haWwiOiJkaW5lc2gta3VtYUBoY2wuY29tIn0.FjupRMaaw14pQr5fHFf171OmG3DuxE_MP_7yK2QdVsrnlvumGVVIW3UMRg9CyAg9r8siGhpWVf38M4cDwElRSCRuwvd25Qpk1pc3fjgXKCFysl9sJrDrpyVpkQBo-o5vN2MMHJgC12v-eif-RYugSIz6lR6Vf2UMW5FRqVepEkwzO7YeOklPiWmtuLwR4qd4x22L3JBTHL0JEMMR692QC5tb3j3wpse_mDvbJqTwVXgkKZFme9eEUYmAgLxeEEyC476khhUvmDZsbmcKo1X9vBU_uS81RIw1PIIHEdWhM-arEJXrW9_Fkun4do9ff8G2_yGgmd0cVgxeN29maSc91w");
		input.setMethodArn("arn:aws:execute-api:us-east-1:145473561237:y1e8a0m6zk/dev/POST/createSuperAdmin");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testLambdaCustomAuthorizer() {
		LambdaCustomAuthorizer handler = new LambdaCustomAuthorizer();
		Context ctx = createContext();

		AuthPolicy authPolicy = handler.handleRequest(input, ctx);
		LOGGER.info("Principal Id: " + authPolicy.getPrincipalId());
		LOGGER.info(authPolicy.getPolicyDocument().entrySet());
		LOGGER.info(authPolicy.getPolicyDocument().values());

		// validate output here.
		Assert.assertNotNull(authPolicy);
	}
}