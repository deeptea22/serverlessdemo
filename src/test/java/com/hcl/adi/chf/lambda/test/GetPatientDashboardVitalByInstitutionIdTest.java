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
import com.hcl.adi.chf.lambda.GetPatientDashboardVitalByInstitutionId;
import com.hcl.adi.chf.model.DashboardVitals;
import com.hcl.adi.chf.util.Constants;

public class GetPatientDashboardVitalByInstitutionIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientDashboardVitalByInstitutionIdTest.class.getName());
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_INSTITUTION_ID, "1");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetPatientDashboardVitalByInstitutionId() {
		GetPatientDashboardVitalByInstitutionId handler = new GetPatientDashboardVitalByInstitutionId();
		Context ctx = createContext();

		DashboardVitals dashboardVitals = handler.handleRequest(input, ctx);
		LOGGER.info(dashboardVitals);

		// validate output here.
		Assert.assertNotNull(dashboardVitals);
	}
}