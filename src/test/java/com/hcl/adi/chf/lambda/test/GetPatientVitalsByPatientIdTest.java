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
import com.hcl.adi.chf.lambda.GetPatientVitalsByPatientId;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.util.Constants;

public class GetPatientVitalsByPatientIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientVitalsByPatientIdTest.class.getName());
	private static Map<String, Integer> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, Integer>();
		input.put(Constants.QUERY_PARAM_PATIENT_ID, 2);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetIADashboardDataByInstitutionId() {
		GetPatientVitalsByPatientId handler = new GetPatientVitalsByPatientId();
		Context ctx = createContext();

		PatientVitals patientVitals = handler.handleRequest(input, ctx);
		LOGGER.info(patientVitals);

		// validate output here.
		Assert.assertNotNull(patientVitals);
	}
}