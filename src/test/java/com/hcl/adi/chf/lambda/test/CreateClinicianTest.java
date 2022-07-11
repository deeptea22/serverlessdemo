package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.CreateClinician;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;

public class CreateClinicianTest {
	private static final Logger LOGGER = LogManager.getLogger(CreateClinicianTest.class.getName());
	private static Clinician clinician;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		clinician = new Clinician();
		clinician.setInstitutionId(1);
		clinician.setEmailId("clinician1@gmail.com");
		clinician.setFirstName("Clinician1");
		clinician.setLastName("Clinician1");
		clinician.setEmployeeId("589216");
		clinician.setPortalAccess("WEB");
		clinician.setCreatedBy("raivariayush@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testCreateClinician() {
		CreateClinician handler = new CreateClinician();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(clinician, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}