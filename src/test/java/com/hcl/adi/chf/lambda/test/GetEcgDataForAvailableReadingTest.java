package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetEcgDataForAvailableReading;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.util.Constants;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetEcgDataForAvailableReadingTest {
	private static final Logger LOGGER = LogManager.getLogger(GetEcgDataForAvailableReadingTest.class.getName());
	private static Map<String, Integer> input;

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
	public void testGetEcgDataForAvailableReading() {
		GetEcgDataForAvailableReading handler = new GetEcgDataForAvailableReading();
		Context ctx = createContext();

		List<DeviceData>  ecgList = handler.handleRequest(input, ctx);
		LOGGER.info(ecgList);

		// validate output here.
		Assert.assertNotNull(ecgList);
	}
}