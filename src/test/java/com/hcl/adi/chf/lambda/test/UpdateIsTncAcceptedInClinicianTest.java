package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateIsTncAcceptedInClinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;

public class UpdateIsTncAcceptedInClinicianTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateIsTncAcceptedInClinicianTest.class.getName());
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_EMAIL_ID, "clinician1@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateIsTncAcceptedInClinician() {
		UpdateIsTncAcceptedInClinician handler = new UpdateIsTncAcceptedInClinician();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(input, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}