package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetIADashboardDataByInstitutionId;
import com.hcl.adi.chf.model.IADashboardResponse;
import com.hcl.adi.chf.util.Constants;

public class GetIADashboardDataByInstitutionIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetIADashboardDataByInstitutionIdTest.class.getName());
	private static HashMap<String, Integer> input = null;

	@BeforeClass
	public static void createInput() throws IOException {
		input = new HashMap<String, Integer>();
		input.put(Constants.QUERY_PARAM_INSTITUTION_ID, 1);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// TODO: customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetIADashboardDataByInstitutionId() {
		GetIADashboardDataByInstitutionId handler = new GetIADashboardDataByInstitutionId();
		Context ctx = createContext();

		IADashboardResponse iaDashboardResponse = handler.handleRequest(input, ctx);
		LOGGER.info(iaDashboardResponse);

		// validate output here.
		Assert.assertNotNull(iaDashboardResponse);
	}
}