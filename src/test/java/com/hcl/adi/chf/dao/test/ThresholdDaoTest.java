package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.ClinicianDao;
import com.hcl.adi.chf.dao.ThresholdDao;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.model.ThresholdPolicy.Controls;
import com.hcl.adi.chf.model.ThresholdPolicy.Threshold;
import com.hcl.adi.chf.model.ThresholdPolicy.Validations;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class ThresholdDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(ThresholdDaoTest.class.getName());

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
		ThresholdPolicy thresholdPolicy = ThresholdDao.fetchThresholdPolicyDetailBasedOnInstitutionID(institutionId);
		LOGGER.info(thresholdPolicy);
		Assert.assertNotNull(thresholdPolicy);
	}

	@Test
	public void updateThresholdPolicyByInstitutionId() {
		
		CustomResponse customResponse = ThresholdDao.updateThresholdPolicyByInstitutionId(thresholdPolicy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateThresholdPolicyByInstitutionIdException() {
		thresholdPolicy.setAction("Policy");
		CustomResponse customResponse = ThresholdDao.updateThresholdPolicyByInstitutionId(thresholdPolicy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void exceptionTryWithResource() {
		SetupInMemoryDBUtil.resetConnection(true);
		ThresholdDao.fetchThresholdPolicyDetailBasedOnInstitutionID(0);
		ThresholdDao.updateThresholdPolicyByInstitutionId(null);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}