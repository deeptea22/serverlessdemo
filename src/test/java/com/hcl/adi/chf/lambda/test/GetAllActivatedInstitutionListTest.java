package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetAllActivatedInstitutionList;
import com.hcl.adi.chf.model.Institution;

public class GetAllActivatedInstitutionListTest {
	private static final Logger LOGGER = LogManager.getLogger(GetAllActivatedInstitutionListTest.class.getName());
	private static Object input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = null;
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetAllActivatedInstitutionList() {
		GetAllActivatedInstitutionList handler = new GetAllActivatedInstitutionList();
		Context ctx = createContext();

		List<Institution> institutionList = handler.handleRequest(input, ctx);
		LOGGER.info(institutionList);

		// validate output here.
		Assert.assertNotNull(institutionList);
	}
}