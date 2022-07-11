package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateDataArchivalPolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DataArchivalPolicy;

public class UpdateDataArchivalPolicyTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateDataArchivalPolicyTest.class.getName());
	private static DataArchivalPolicy dataArchivalPolicy;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		dataArchivalPolicy = new DataArchivalPolicy();
		dataArchivalPolicy.setDataArchivalPolicyId(1);
		dataArchivalPolicy.setInstitutionId(0);
		dataArchivalPolicy.setArchivalPeriodInMonths(12);
		dataArchivalPolicy.setAutoArchivalFrequency("ONCE_IN_A_YEAR");
		dataArchivalPolicy.setAutoLogOffTimeInMinutes(15);
		dataArchivalPolicy.setDurationToStoreAuditLogsInMonths(3);
		dataArchivalPolicy.setDataArchivalPolicyStatus("A");
		dataArchivalPolicy.setDeleteMarker("N");
		dataArchivalPolicy.setUpdatedBy("karthick@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateDataArchivalPolicy() {
		UpdateDataArchivalPolicy handler = new UpdateDataArchivalPolicy();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(dataArchivalPolicy, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}