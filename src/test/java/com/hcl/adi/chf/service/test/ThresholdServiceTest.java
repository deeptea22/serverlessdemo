package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.model.ThresholdPolicy.Controls;
import com.hcl.adi.chf.model.ThresholdPolicy.Threshold;
import com.hcl.adi.chf.model.ThresholdPolicy.Validations;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.service.ThresholdService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class ThresholdServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(ThresholdServiceTest.class.getName());
	private static ThresholdPolicy thresholdPolicy = null;
	private static int institutionId;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		institutionId = 1;
		Validations validations = new Validations();
		validations.setMax("10");
		validations.setMin("2");
		validations.setUnit("km");
		Controls control = new Controls();
		control.setUnit("meter");
		control.setUnitDesc("meter");
		control.setUnitName("meter");
		control.setUnitValue("53");
		Threshold threshold = new Threshold();
		List controlL = new ArrayList<Controls>();
		controlL.add(control);
		threshold.setControls(controlL);
		threshold.setParamDesc("km");
		threshold.setParamName("da");
		threshold.setValidations(validations);
		
		thresholdPolicy = new ThresholdPolicy();

		thresholdPolicy.setThresholdPolicyId(1);
		thresholdPolicy.setInstitutionId(1);
		thresholdPolicy.setAction("Toggle");
		thresholdPolicy.setStatus("A");
		ArrayList<Threshold> TL = new ArrayList<ThresholdPolicy.Threshold>();
		TL.add(threshold);
		thresholdPolicy.setThreshold(TL);
		thresholdPolicy.setCreatedBy("admin@hcl.com");
		thresholdPolicy.setCreatedTimestamp(new Date());
		thresholdPolicy.setUpdatedBy("clinician@hcl.com");
		thresholdPolicy.setUpdatedTimestamp(new Date());
	}
	
	@Test
	public void fetchThresholdPolicyDetailBasedOnInstitutionID() {
		ThresholdService obj = new ThresholdService();
		ThresholdPolicy thresholdPolicy = obj.getThresholdPolicyByInstitutionId(institutionId);
		LOGGER.info(thresholdPolicy);
		Assert.assertNotNull(thresholdPolicy);
	}
	@Test
	public void updateThresholdPolicyByInstitutionId() {
		ThresholdService obj = new ThresholdService();
		CustomResponse customResponse = obj.updateThresholdPolicyByInstitutionId(thresholdPolicy);
		LOGGER.info(customResponse);
	}
	
	@Test
	public void coverTryResource() {
		ThresholdService obj = new ThresholdService();
		obj.getThresholdPolicyByInstitutionId(0);
		obj.updateThresholdPolicyByInstitutionId(null);
	}
}