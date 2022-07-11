package com.hcl.adi.chf.dao.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.CompliancePolicyDao;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class CompliancePolicyDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(CompliancePolicyDaoTest.class.getName());


	private static Integer institutionId;
	private static CompliancePolicy updateCompliancePolicy = null;
	

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

	
		updateCompliancePolicy = new CompliancePolicy();
		updateCompliancePolicy = new CompliancePolicy();
		updateCompliancePolicy.setPkId(1);
		updateCompliancePolicy.setInstitutionId(0);
		updateCompliancePolicy.setCompliancePeriod(137);
		updateCompliancePolicy.setStatus("1");
		updateCompliancePolicy.setCreatedBy("abc@gmail.com");
		updateCompliancePolicy.setUpdatedBy("abc@gmail.com");

	}
	
	@Test
	public void fetchCompliancePolicyBasedOnInstitutionID() {
		CompliancePolicy compliancePolicy = CompliancePolicyDao.fetchCompliancePolicyBasedOnInstitutionID(institutionId);
		LOGGER.info(compliancePolicy);
		Assert.assertNotNull(compliancePolicy);
	}
	
	@Test
	public void updateCompliancePolicy() {
		CustomResponse customResponse = CompliancePolicyDao.updateCompliancePolicy(updateCompliancePolicy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	

}