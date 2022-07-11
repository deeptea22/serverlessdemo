package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.AddPatientAction;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientActions;

public class AddPatientActionTest {
	private static final Logger LOGGER = LogManager.getLogger(AddPatientActionTest.class.getName());
	private static PatientActions patientActions;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		patientActions = new PatientActions();
		patientActions.setDetails("HI");
		// patientActions.setDueDate(new Date(1564132319715l));
		patientActions.setPatientId(1);
		patientActions.setPriority("STANDARD");
		patientActions.setStatus("OPEN");
		patientActions.setSubject("Patient feeling good");
		patientActions.setCreatedBy("shi@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetClinicianListByInstitutionId() {
		AddPatientAction handler = new AddPatientAction();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(patientActions, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}