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
import com.hcl.adi.chf.lambda.GetInstitutionDetailsByInstitutionId;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.util.Constants;

public class GetInstitutionDetailsByInstitutionIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetInstitutionDetailsByInstitutionIdTest.class.getName());
	private static Map<String, Integer> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, Integer>();
		input.put(Constants.QUERY_PARAM_INSTITUTION_ID, 1);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetInstitutionDetailsByInstitutionId() {
		GetInstitutionDetailsByInstitutionId handler = new GetInstitutionDetailsByInstitutionId();
		Context ctx = createContext();

		Institution institution = handler.handleRequest(input, ctx);
		LOGGER.info(institution);

		// validate output here.
		Assert.assertNotNull(institution);
	}
}