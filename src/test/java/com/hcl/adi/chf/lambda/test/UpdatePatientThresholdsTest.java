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
import com.hcl.adi.chf.lambda.UpdatePatientThresholds;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.model.PatientThreshold.Controls;
import com.hcl.adi.chf.model.PatientThreshold.Threshold;

public class UpdatePatientThresholdsTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientThresholdsTest.class.getName());
	private static PatientThreshold patientThreshold;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		Controls control = new Controls();
		control.setUnit("m");
		control.setUnitDesc("meter");
		control.setUnitName("meter");
		control.setUnitValue("25");
		
		Threshold threshold = new Threshold();
		List controlL = new ArrayList<Controls>();
		controlL.add(control);
		threshold.setControls(controlL);
		threshold.setParamDesc("distance");
		threshold.setParamName("distance");
		
		List thresholdL =new ArrayList<Threshold>();
		thresholdL.add(threshold);

		patientThreshold = new PatientThreshold();
		patientThreshold.setCreatedBy("shi@gmail.com");
		patientThreshold.setPatientId(1);
		patientThreshold.setCreatedTimestamp(new Date());
		patientThreshold.setDescription(null);
		patientThreshold.setStatusCode(0);
		patientThreshold.setThreshold(thresholdL);
		patientThreshold.setThresholdId(1);
		patientThreshold.setUpdatedBy("shi@gmail.com");
		patientThreshold.setUpdatedTimestamp(new Date());
		
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdatePatientThresholds() {
		UpdatePatientThresholds handler = new UpdatePatientThresholds();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(patientThreshold, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}