package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UnblockAdminsAfterCoolingPeriod;
import com.hcl.adi.chf.model.CustomResponse;

public class UnblockAdminsAfterCoolingPeriodTest {
	private static final Logger LOGGER = LogManager.getLogger(UnblockAdminsAfterCoolingPeriodTest.class.getName());
	private static Object input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = null;
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUnblockAdminsAfterCoolingPeriod() {
		UnblockAdminsAfterCoolingPeriod handler = new UnblockAdminsAfterCoolingPeriod();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(input, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}