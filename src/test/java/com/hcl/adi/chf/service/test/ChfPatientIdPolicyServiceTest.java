package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.ChfPatientIdPolicy.Policy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.model.PatientReadingResponse;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.service.ChfPatientIdPolicyService;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;


public class ChfPatientIdPolicyServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(DataArchivalPolicyServiceTest.class.getName());
	private static ChfPatientIdPolicy chfPatientIdPolicy = null;
	private static List<Policy> policyList;
	private static Integer institutionId;
	
	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();
		institutionId =1;
		chfPatientIdPolicy = new ChfPatientIdPolicy();
		policyList = new ArrayList<ChfPatientIdPolicy.Policy>();

		chfPatientIdPolicy.setInstitutionId(1);
		chfPatientIdPolicy.setIsEMREHREnabled("N");
		chfPatientIdPolicy.setCreatedBy("ds1326@hcl.com");

		ChfPatientIdPolicy.Policy policy1 = new ChfPatientIdPolicy.Policy();
		policy1.setLabel("First Name");
		policy1.setLabelRule("Only first initial");
		policy1.setLabelSeparator("None");
		policy1.setLabelSequence((short) 1);

		ChfPatientIdPolicy.Policy policy2 = new ChfPatientIdPolicy.Policy();
		policy2.setLabel("Last Name");
		policy2.setLabelRule("Last four letters");
		policy2.setLabelSeparator("Hyphen");
		policy2.setLabelSequence((short) 2);

		ChfPatientIdPolicy.Policy policy3 = new ChfPatientIdPolicy.Policy();
		policy3.setLabel("Date of Birth");
		policy3.setLabelRule("MMDDYYYY");
		policy3.setLabelSeparator("None");
		policy3.setLabelSequence((short) 3);

		policyList.add(policy1);
		policyList.add(policy2);
		policyList.add(policy3);

		chfPatientIdPolicy.setPolicyList(policyList);
	}
	
	@Test
	public void persistChfPatientIdPolicy() {
		ChfPatientIdPolicyService chfPatientIdPolicyService = new ChfPatientIdPolicyService();
		CustomResponse customResponse = chfPatientIdPolicyService.persistChfPatientIdPolicy(chfPatientIdPolicy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void persistChfPatientIdPolicyWithoutData() {
		ChfPatientIdPolicyService chfPatientIdPolicyService = new ChfPatientIdPolicyService();
		CustomResponse customResponse = chfPatientIdPolicyService.persistChfPatientIdPolicy(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void getChfPatientIdPolicyByInstitutionId() {
		ChfPatientIdPolicyService chfPatientIdPolicyService = new ChfPatientIdPolicyService();
		ChfPatientIdPolicy output = chfPatientIdPolicyService.getChfPatientIdPolicyByInstitutionId(institutionId);
		LOGGER.info(output);
		Assert.assertNotNull(output);
	}
	
}
