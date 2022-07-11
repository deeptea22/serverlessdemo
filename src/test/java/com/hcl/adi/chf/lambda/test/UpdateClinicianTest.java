package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateClinician;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;

public class UpdateClinicianTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateClinicianTest.class.getName());
	private static Clinician clinician;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		clinician = new Clinician();
		clinician.setEmailId("clinician3@gmail.com");
		clinician.setFirstName("Karthick");
		clinician.setLastName("NG");
		clinician.setEmployeeId("517518");
		clinician.setCreatedBy("karthick@gmail.com");
		clinician.setInstitutionId(1);
		clinician.setClinicianId(13);
		clinician.setType("CL");
		clinician.setPortalAccess("WEB");
		clinician.setPoolId("us-east-1_tswTIQ0gH");
		clinician.setDeleteMarker("N");
		clinician.setStatus("A");
		clinician.setAction("profileupdated");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateClinician() {
		UpdateClinician handler = new UpdateClinician();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(clinician, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}