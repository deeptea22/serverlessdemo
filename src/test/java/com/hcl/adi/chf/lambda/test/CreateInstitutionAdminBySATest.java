package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.CreateInstitutionAdminBySA;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;

public class CreateInstitutionAdminBySATest {
	private static final Logger LOGGER = LogManager.getLogger(CreateInstitutionAdminBySATest.class.getName());
	private static Admins admin;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		admin = new Admins();
		admin.setInstitutionId(1);
		admin.setEmailId("raivariayush@gmail.com");
		admin.setFirstName("Ayush");
		admin.setLastName("Raivari");
		admin.setEmployeeId("517218");
		admin.setCreatedBy("karthick123@gmail.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testCreateInstitutionAdminBySA() {
		CreateInstitutionAdminBySA handler = new CreateInstitutionAdminBySA();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(admin, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}