package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.PersistPHIData;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.model.PatientPhi.PatientAttributes;

public class PersistPHIDataTest {
	private static final Logger LOGGER = LogManager.getLogger(PersistPHIDataTest.class.getName());
	private static PatientPhi patientPhi;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		
		List<PatientAttributes> patientAttributes = new ArrayList<PatientPhi.PatientAttributes>();
		PatientAttributes attributes = new PatientAttributes();
		attributes.setLabel("First 3 chars of first name");
		attributes.setValue("JOE");
		patientAttributes.add(attributes);
		patientPhi = new PatientPhi();
		patientPhi.setPatientId(5);
		patientPhi.setChfPatientId("John-Deo-24111985");
		patientPhi.setInstitutionId(1);
		patientPhi.setMrNumber("retr56");
		patientPhi.setPatientAttributesList(patientAttributes);
		patientPhi.setCreatedBy("gtestg@ymail.com");
		patientPhi.setCreatedTimestamp(new Date(1556529931260l));
		patientPhi.setUpdatedBy("gtestg@ymail.com");
		patientPhi.setUpdatedTimestamp(new Date(1556529931260l));
		patientPhi.setAddress("noida");
		patientPhi.setContactNo("7397297922");
		patientPhi.setDeleteMarker("Y");
		patientPhi.setDeviceLabelId("21");
		patientPhi.setDoB("29-11-94");
		patientPhi.setFirstName("John");
		patientPhi.setGender("m");
		patientPhi.setIsActionOpen(false);
		
		patientPhi.setLastName("bill");
		patientPhi.setSsn("ss");
		patientPhi.setSystemId("s1");
		patientPhi.setZip("898291");
		patientPhi.setPatientDetailsJson("'label:' 'l1'");
		
		
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testPersistPHIData() {
		PersistPHIData handler = new PersistPHIData();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(patientPhi, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}