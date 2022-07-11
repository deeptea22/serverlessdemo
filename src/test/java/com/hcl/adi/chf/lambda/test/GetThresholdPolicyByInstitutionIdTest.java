package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetThresholdPolicyByInstitutionId;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.util.Constants;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetThresholdPolicyByInstitutionIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetThresholdPolicyByInstitutionIdTest.class.getName());
	private static Map<String, Integer> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
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
	public void testGetThresholdPolicyByInstitutionId() {
		GetThresholdPolicyByInstitutionId handler = new GetThresholdPolicyByInstitutionId();
		Context ctx = createContext();

		ThresholdPolicy thresholdPolicy = handler.handleRequest(input, ctx);

		LOGGER.info(thresholdPolicy);

		// validate output here.
		Assert.assertNotNull(thresholdPolicy);
	}
}
