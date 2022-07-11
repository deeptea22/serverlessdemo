package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.InsertPatientComment;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientComments;

public class InsertPatientCommentTest {
	private static final Logger LOGGER = LogManager.getLogger(InsertPatientCommentTest.class.getName());
	private static PatientComments bean;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		bean = new PatientComments();
		
		bean.setPatientId(1);
		bean.setComment("Test");
		bean.setCreatedBy("abc@hcl.com");
		
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testCreateClinician() {
		InsertPatientComment handler = new InsertPatientComment();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(bean, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}