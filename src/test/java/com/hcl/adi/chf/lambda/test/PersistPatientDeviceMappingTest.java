package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.PersistPatientDeviceMapping;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientDeviceMapping;

public class PersistPatientDeviceMappingTest {
	private static final Logger LOGGER = LogManager.getLogger(PersistPatientDeviceMappingTest.class.getName());
	private static PatientDeviceMapping pdm;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		pdm = new PatientDeviceMapping();
		pdm.setChfPatientId("c4");
		pdm.setKitId(4);
		pdm.setDeviceLabelId("ADI-1004");
		pdm.setCreatedBy("vv");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateDevicePatientMapping() {
		PersistPatientDeviceMapping handler = new PersistPatientDeviceMapping();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(pdm, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertNotNull(output);
	}
}