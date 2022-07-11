package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.ContactUSRequest;
import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.model.CustomResponse;

public class ContactUSRequestTest {
	private static final Logger LOGGER = LogManager.getLogger(ContactUSRequestTest.class.getName());
	private static ContactUS contactUS;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		contactUS = new ContactUS();
		contactUS.setRequesterName("Dinesh Kumar");
		contactUS.setCcEmail("dinesh-kuma@hcl.com");
		contactUS.setSubject("Need support on CHF device");
		contactUS.setMessage("Device is not functioning properly");
		contactUS.setIsCopyRequested(false);
		contactUS.setCreatedBy("dk1326@hcl1.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testContactUSRequest() {
		ContactUSRequest handler = new ContactUSRequest();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(contactUS, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals(200, output.getStatusCode());
	}
}