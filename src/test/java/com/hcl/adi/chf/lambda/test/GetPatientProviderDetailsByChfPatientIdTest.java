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
import com.hcl.adi.chf.lambda.GetPatientProviderDetailsByChfPatientId;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.util.Constants;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetPatientProviderDetailsByChfPatientIdTest  {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientProviderDetailsByChfPatientIdTest.class.getName());
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
    public void testGetPatientProviderDetailsByChfPatientId() {
        GetPatientProviderDetailsByChfPatientId handler = new GetPatientProviderDetailsByChfPatientId();
        Context ctx = createContext();

        PatientProvider patientProvider = handler.handleRequest(input, ctx);

        LOGGER.info(patientProvider);

		// validate output here.
		Assert.assertNotNull(patientProvider);
    }
}
