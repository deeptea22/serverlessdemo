package com.hcl.adi.chf.service.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.test.PwdPolicyDaoTest;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.service.PasswordPolicyService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class PasswordPolicyServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(PwdPolicyDaoTest.class.getName());

	private static PasswordPolicy updatePasswordPolicy = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		updatePasswordPolicy = new PasswordPolicy();
		updatePasswordPolicy.setPwdPolicyId(1);
		updatePasswordPolicy.setInstitutionId(1);
		updatePasswordPolicy.setCreatedBy("gtestg@ymail.com");
		updatePasswordPolicy.setUpdatedBy("gtestg@ymail.com");
		updatePasswordPolicy.setIsCapsAllowed("Y");
		updatePasswordPolicy.setIsLowerAllowed("Y");
		updatePasswordPolicy.setIsNumericAllowed("Y");
		updatePasswordPolicy.setIsSplCharAllowed("Y");
		updatePasswordPolicy.setPwdPolicyStatus("A");
		updatePasswordPolicy.setProhibitedPasswords("Y");
		updatePasswordPolicy.setPwdMaxLength(10);
		updatePasswordPolicy.setPwdMinLength(5);
		updatePasswordPolicy.setPwdRotationInDays(30);
	}

	@Test
	public void updatePasswordPolicy() {
		PasswordPolicyService passwordPolicyService = new PasswordPolicyService();
		CustomResponse customResponse = passwordPolicyService.updatePasswordPolicy(updatePasswordPolicy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePasswordPolicyException() {
		PasswordPolicyService passwordPolicyService = new PasswordPolicyService();
		CustomResponse customResponse = passwordPolicyService.updatePasswordPolicy(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void getPwdPolicyInfoByInstitutionId() {
		PasswordPolicyService passwordPolicyService = new PasswordPolicyService();
		PasswordPolicy passwordPolicie = passwordPolicyService
				.getPwdPolicyInfoByInstitutionId(updatePasswordPolicy.getInstitutionId());
		LOGGER.info(passwordPolicie);
		Assert.assertNotNull(passwordPolicie);
	}
}