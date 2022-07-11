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
import com.hcl.adi.chf.lambda.GetCPMTrends;
import com.hcl.adi.chf.model.PatientRecord;
import com.hcl.adi.chf.util.Constants;

public class GetCPMTrendsTest {
	private static final Logger LOGGER = LogManager.getLogger(GetCPMTrendsTest.class.getName());
	private static Map<String, String> input;
	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_PATIENT_ID, "1");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetCPMTrends() {
		GetCPMTrends handler = new GetCPMTrends();
		Context ctx = createContext();

		PatientRecord bean = handler.handleRequest(input, ctx);
		LOGGER.info(bean);

		// validate output here.
		Assert.assertNotNull(bean);
	}
}