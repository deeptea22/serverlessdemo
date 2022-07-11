package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateAdmin;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;

public class UpdateAdminTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateAdminTest.class.getName());
	private static Admins admin;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		admin = new Admins();
		admin.setAdminId(10);
		admin.setInstitutionId(0);
		admin.setEmailId("dinesh-kuma@hcl.com");
		admin.setFirstName("Dinesh");
		admin.setLastName("Kumar");
		admin.setEmployeeId("517324");
		admin.setPoolId("us-east-1_tswTIQ0gH");
		admin.setStatus("A");
		admin.setType("SA");
		admin.setIsDefault("Y");
		admin.setPortalAccess("WEB");
		admin.setLocation("US");
		admin.setDeleteMarker("N");
		admin.setUpdatedBy("karthick@gmail.com");
		admin.setAction("ACTIVATE");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateAdmin() {
		UpdateAdmin handler = new UpdateAdmin();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(admin, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}