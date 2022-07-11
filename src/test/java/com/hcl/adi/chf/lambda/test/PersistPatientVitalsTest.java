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
import com.hcl.adi.chf.lambda.PersistPatientVitals;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.model.PatientVitals.CustomVitals;

public class PersistPatientVitalsTest {
	private static final Logger LOGGER = LogManager.getLogger(PersistPatientVitalsTest.class.getName());
	private static PatientVitals patientVitals;
	private static CustomVitals customVitals;

	@BeforeClass
	public static void createInput() throws IOException {
		customVitals = new CustomVitals();
		customVitals.setLabel("distance");
		customVitals.setValue("25 m");
		
		List vitalL = new ArrayList<CustomVitals>();
		vitalL.add(customVitals);
		
		patientVitals = new PatientVitals();
		patientVitals.setPatientId(4);
		patientVitals.setSystolicBP(90.6F);
		patientVitals.setDiastolicBP(100.7F);
		patientVitals.setWeight(68.5F);
		patientVitals.setTemperature(95.5F);
		patientVitals.setCustomVitalsList(vitalL);
		patientVitals.setCreatedBy("divyaag@hcl.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// TODO: customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testPersistPatientVitals() {
		PersistPatientVitals handler = new PersistPatientVitals();
		Context ctx = createContext();

		PatientVitals patientVitalsResponse = handler.handleRequest(patientVitals, ctx);
		LOGGER.info(patientVitalsResponse);

		// validate output here.
		Assert.assertNotNull(patientVitalsResponse);
	}
}