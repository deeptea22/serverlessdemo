package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.SQSReaderToSendEmail;

public class SQSReaderToSendEmailTest {
	private static final Logger LOGGER = LogManager.getLogger(SQSReaderToSendEmailTest.class.getName());
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
	public void testSQSReaderToSendEmail() {
		SQSReaderToSendEmail handler = new SQSReaderToSendEmail();
		Context ctx = createContext();

		String output = handler.handleRequest(input, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("Success", output);
	}
}