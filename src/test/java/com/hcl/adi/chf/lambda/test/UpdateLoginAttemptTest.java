package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.enums.ClinicianType;
import com.hcl.adi.chf.lambda.UpdateLoginAttempt;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.util.Constants;

public class UpdateLoginAttemptTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateLoginAttemptTest.class.getName());
	private static LoginActivity loginActivity;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		loginActivity = new LoginActivity();
		loginActivity.setEmailId("clinician1@gmail.com");
		loginActivity.setType(ClinicianType.CL.name());
		loginActivity.setLoginAttempt(Constants.LOGIN_FAILURE);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateLoginAttempt() {
		UpdateLoginAttempt handler = new UpdateLoginAttempt();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(loginActivity, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}