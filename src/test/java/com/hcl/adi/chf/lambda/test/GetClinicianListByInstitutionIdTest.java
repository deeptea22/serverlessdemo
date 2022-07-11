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
import com.hcl.adi.chf.lambda.GetClinicianListByInstitutionId;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.util.Constants;

public class GetClinicianListByInstitutionIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetClinicianListByInstitutionIdTest.class.getName());
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
	public void testGetClinicianListByInstitutionId() {
		GetClinicianListByInstitutionId handler = new GetClinicianListByInstitutionId();
		Context ctx = createContext();

		List<Clinician> clinicianList = handler.handleRequest(input, ctx);

		LOGGER.info(clinicianList);

		// validate output here.
		Assert.assertNotNull(clinicianList);
	}
}