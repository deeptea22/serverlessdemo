package com.hcl.adi.chf.dao.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.PwdPolicyDao;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class PwdPolicyDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(PwdPolicyDaoTest.class.getName());

	private static PasswordPolicy createPasswordPolicy = null;
	private static PasswordPolicy updatePasswordPolicy = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();


		updatePasswordPolicy = new PasswordPolicy();
		updatePasswordPolicy.setPwdPolicyId(1);
		updatePasswordPolicy.setInstitutionId(2);
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
		updatePasswordPolicy.setRetryLoginAttemptsAllowed(1);
		updatePasswordPolicy.setPwdHistory(1);
		updatePasswordPolicy.setProhibitedPasswords("Y");
		updatePasswordPolicy.setPwdPolicyStatus("Y");
		updatePasswordPolicy.setDeleteMarker("Y");
		updatePasswordPolicy.setPwdPolicyId(1);
	}

	
	@Test
	public void fetchPwdPolicyDetailBasedOnInstitutionID() {
		PasswordPolicy passwordPolicy = PwdPolicyDao.fetchPwdPolicyDetailBasedOnInstitutionID(2);
		LOGGER.info(passwordPolicy);
		Assert.assertNotNull(passwordPolicy);
	}

	@Test
	public void fetchPwdPolicyDetailBasedOnInstitutionIDExceptionCover() {
		PasswordPolicy passwordPolicy = PwdPolicyDao
				.fetchPwdPolicyDetailBasedOnInstitutionID(createPasswordPolicy.getInstitutionId());
		LOGGER.info(passwordPolicy);
		Assert.assertNotNull(passwordPolicy);
	}

	@Test
	public void updateInstitutionPwdPolicy() {
		updatePasswordPolicy.setInstitutionId(1);
		updatePasswordPolicy.setPwdPolicyId(1);
		CustomResponse customResponse = PwdPolicyDao.updatePwdPolicy(updatePasswordPolicy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateInstitutionPwdPolicyExceptionCoverage() {
		updatePasswordPolicy.setInstitutionId(null);
		CustomResponse customResponse = PwdPolicyDao.updatePwdPolicy(updatePasswordPolicy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void tryResourceExceptionCover() {
		try {
		SetupInMemoryDBUtil.resetConnection(true);
		PwdPolicyDao.updatePwdPolicy(updatePasswordPolicy);
		PwdPolicyDao.fetchPwdPolicyDetailBasedOnInstitutionID(createPasswordPolicy.getInstitutionId());
		
		} catch (Exception e) {
			LOGGER.info(e);

		}
		SetupInMemoryDBUtil.resetConnection(false);
	}
}
