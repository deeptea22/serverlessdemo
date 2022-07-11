package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdatePatientAction;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientActions;

public class UpdatePatientActionTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientActionTest.class.getName());
	private static PatientActions patientActions;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		patientActions = new PatientActions();
		patientActions.setUpdatedBy("maga123@gmail.com");
		patientActions.setActionId(1);
		patientActions.setPatientId(1);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateLoginAttempt() {
		UpdatePatientAction handler = new UpdatePatientAction();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(patientActions, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}