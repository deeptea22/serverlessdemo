package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateTermsAndConditions;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.TermsAndConditions;

public class UpdateTermsAndConditionsTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateTermsAndConditionsTest.class.getName());
	private static TermsAndConditions tnc;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		tnc = new TermsAndConditions();
		tnc.setTncId(1);
		tnc.setUpdatedBy("raivariayush@gmail.com");
		tnc.setTncStatus("A");
		tnc.setDeleteMarker("N");
		tnc.setTncText("Sample TNC");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateTermsAndConditions() {
		UpdateTermsAndConditions handler = new UpdateTermsAndConditions();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(tnc, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}