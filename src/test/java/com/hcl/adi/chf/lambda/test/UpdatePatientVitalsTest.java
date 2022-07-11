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
import com.hcl.adi.chf.lambda.UpdatePatientVitals;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.model.PatientVitals.CustomVitals;

public class UpdatePatientVitalsTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientVitalsTest.class.getName());
	private static PatientVitals patientVitals;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		CustomVitals customVitals = new CustomVitals();
		customVitals.setLabel("meter");
		customVitals.setValue("25");
		List customVitalL = new ArrayList<CustomVitals>();
		customVitalL.add(customVitals);
		patientVitals = new PatientVitals();
		patientVitals.setPatientVitalsId(1);
		patientVitals.setPatientId(1);
		patientVitals.setSystolicBP(110F);
		patientVitals.setDiastolicBP(120F);
		patientVitals.setWeight(25f);
		patientVitals.setTemperature(29.9f);
		patientVitals.setCustomVitals("false");
		patientVitals.setCustomVitalsList(customVitalL);
		patientVitals.setReadingDate(new Date());
		patientVitals.setCreatedBy("vv");
		patientVitals.setCreatedTimeStamp(new Date());
		patientVitals.setUpdatedBy("vv");
		patientVitals.setUpdatedTimeStamp(new Date());
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateCustomVitalsByPatientId() {
		UpdatePatientVitals handler = new UpdatePatientVitals();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(patientVitals, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}