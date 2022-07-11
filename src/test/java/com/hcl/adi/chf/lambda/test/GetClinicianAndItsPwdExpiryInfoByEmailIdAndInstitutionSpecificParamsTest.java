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
import com.hcl.adi.chf.lambda.GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParams;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.util.Constants;

public class GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParamsTest {
	private static final Logger LOGGER = LogManager.getLogger(GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParamsTest.class.getName());
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_EMAIL_ID, "clinician1@gmail.com");
		input.put(Constants.QUERY_PARAM_PWD_EXPIRE_IN_DAYS, "30");
		input.put(Constants.QUERY_PARAM_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS, "7");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParams() {
		GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParams handler = new GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParams();
		Context ctx = createContext();

		UserDetails userDetails = handler.handleRequest(input, ctx);
		LOGGER.info(userDetails);

		// validate output here.
		Assert.assertNotNull(userDetails);
	}
}