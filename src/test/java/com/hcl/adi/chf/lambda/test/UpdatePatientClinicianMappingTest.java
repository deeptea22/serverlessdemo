package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.enums.WatchlistAction;
import com.hcl.adi.chf.lambda.UpdatePatientClinicianMapping;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientClinicianMapping;

public class UpdatePatientClinicianMappingTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientClinicianMappingTest.class.getName());
	private static PatientClinicianMapping pcm;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		pcm = new PatientClinicianMapping();
		pcm.setAction(WatchlistAction.ADD.name());
		pcm.setClinicianId(16);
		pcm.setCreatedBy("vartika@hcl.com");
		pcm.setCreatedTimestamp(new Date());
		pcm.setMapId(6);
		pcm.setPatientId(6);
		pcm.setUpdatedBy("vartika@hcl.com");
		pcm.setUpdatedTimestamp(new Date());
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdatePatientClinicianMapping1() {
		UpdatePatientClinicianMapping handler = new UpdatePatientClinicianMapping();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(pcm, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}