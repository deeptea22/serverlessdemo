package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.AddActionComment;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientActionComments;

public class AddActionCommentTest {
	private static final Logger LOGGER = LogManager.getLogger(AddActionCommentTest.class.getName());
	private static PatientActionComments patientActionComments;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		patientActionComments = new PatientActionComments();
		patientActionComments.setActionId(10);
		patientActionComments.setComments("Test Comment");
		patientActionComments.setCreatedBy("shi@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetClinicianListByInstitutionId() {
		AddActionComment handler = new AddActionComment();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(patientActionComments, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}