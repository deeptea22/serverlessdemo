package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdatePwdPolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PasswordPolicy;

public class UpdatePwdPolicyTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePwdPolicyTest.class.getName());
	private static PasswordPolicy passwordPolicy;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		passwordPolicy = new PasswordPolicy();
		passwordPolicy.setPwdPolicyId(1);
		passwordPolicy.setInstitutionId(1);
		passwordPolicy.setPwdRotationInDays(90);
		passwordPolicy.setPwdMinLength(7);
		passwordPolicy.setPwdMaxLength(90);
		passwordPolicy.setIsCapsAllowed("Y");
		passwordPolicy.setIsLowerAllowed("Y");
		passwordPolicy.setIsNumericAllowed("Y");
		passwordPolicy.setIsSplCharAllowed("Y");
		passwordPolicy.setRetryLoginAttemptsAllowed(3);
		passwordPolicy.setPwdHistory(3);
		passwordPolicy.setProhibitedPasswords("test123");
		passwordPolicy.setPwdPolicyStatus("A");
		passwordPolicy.setDeleteMarker("N");
		passwordPolicy.setUpdatedBy("raivariayush@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdatePwdPolicy() {
		UpdatePwdPolicy handler = new UpdatePwdPolicy();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(passwordPolicy, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}