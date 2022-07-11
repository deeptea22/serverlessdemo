package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdatePatientMasterOtherMapping;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientMasterMapping;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.model.PatientThreshold.Controls;
import com.hcl.adi.chf.model.PatientThreshold.Threshold;

public class UpdatePatientMasterOtherMappingTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientMasterOtherMappingTest.class.getName());
	private static PatientMasterMapping patientMasterOtherMapping;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		patientMasterOtherMapping = new PatientMasterMapping();
		
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdatePatientMasterOtherMapping() {
		UpdatePatientMasterOtherMapping handler = new UpdatePatientMasterOtherMapping();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(patientMasterOtherMapping, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}