package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.PersistInstitution;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Institution;

public class PersistInstitutionTest {
	private static final Logger LOGGER = LogManager.getLogger(PersistInstitutionTest.class.getName());
	private static Institution institution;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		institution = new Institution();
		institution.setInstitutionName("R.M.L Hospital");
		institution.setAddress("Delhi");
		institution.setContactPerson("John");
		institution.setContactNumber("+919722454678");
		institution.setCreatedBy("karthick@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testPersistInstitution() {
		PersistInstitution handler = new PersistInstitution();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(institution, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}