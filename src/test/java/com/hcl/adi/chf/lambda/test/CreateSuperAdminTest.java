package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.CreateSuperAdmin;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;

public class CreateSuperAdminTest {
	private static final Logger LOGGER = LogManager.getLogger(CreateSuperAdminTest.class.getName());
	private static Admins admin;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		admin = new Admins();
		admin.setEmailId("karthick123@gmail.com");
		admin.setFirstName("Karthick");
		admin.setLastName("NG");
		admin.setEmployeeId("517518");
		admin.setCreatedBy("karthick@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testCreateSuperAdmin() {
		CreateSuperAdmin handler = new CreateSuperAdmin();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(admin, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}