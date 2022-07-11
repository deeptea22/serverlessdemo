package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.ListInstitutionAdmins;
import com.hcl.adi.chf.model.Admins;

public class ListInstitutionAdminsTest {
	private static final Logger LOGGER = LogManager.getLogger(ListInstitutionAdminsTest.class.getName());
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
	public void testListInstitutionAdmins() {
		ListInstitutionAdmins handler = new ListInstitutionAdmins();
		Context ctx = createContext();

		List<Admins> institutionAdminList = handler.handleRequest(input, ctx);
		LOGGER.info(institutionAdminList);

		// validate output here.
		Assert.assertNotNull(institutionAdminList);
	}
}