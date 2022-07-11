package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetPatientActions;
import com.hcl.adi.chf.model.PatientActions;
import com.hcl.adi.chf.util.Constants;

public class GetPatientActionsTest {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientActionsTest.class.getName());
	private static HashMap<String, Integer> input = null;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, Integer>();
		input.put(Constants.QUERY_PARAM_PATIENT_ID, 1);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetIADashboardDataByInstitutionId() {
		GetPatientActions handler = new GetPatientActions();
		Context ctx = createContext();

		PatientActions patientActions = handler.handleRequest(input, ctx);
		LOGGER.info(patientActions);

		// validate output here.
		Assert.assertNotNull(patientActions.getPatientActions());
	}
}