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
import com.hcl.adi.chf.lambda.GetPatientPhiDetailByChfPatientId;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.util.Constants;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetPatientPhiDetailByChfPatientIdTest {
	private static final Logger LOGGER = LogManager
			.getLogger(GetPatientPhiDetailByChfPatientIdTest.class.getName());
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_CHF_PATIENT_ID, "c5");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetPatientPhiDetailsBasedOnChfPatientId() {
		GetPatientPhiDetailByChfPatientId handler = new GetPatientPhiDetailByChfPatientId();
		Context ctx = createContext();

		PatientPhi patientPhi = handler.handleRequest(input, ctx);

		LOGGER.info(patientPhi);

		// validate output here.
		Assert.assertNotNull(patientPhi);
	}
}