package com.hcl.adi.chf.service.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.test.TncDaoTest;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.service.TncService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class TncServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(TncDaoTest.class.getName());

	private static TermsAndConditions updateTermsAndConditions = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		updateTermsAndConditions = new TermsAndConditions();
		updateTermsAndConditions.setCreatedBy("gtestg@ymail.com");
		updateTermsAndConditions.setUpdatedBy("gtestg@ymail.com");
		updateTermsAndConditions.setInstitutionId(1);
		updateTermsAndConditions.setTncId(0);
		updateTermsAndConditions.setInstitutionId(0);
		updateTermsAndConditions.setTncStatus("A");
		updateTermsAndConditions.setTncText("T&C Text");
	}

	@Test
	public void updateTermsNConditions() {
		TncService tncService = new TncService();
		CustomResponse customResponse = tncService.updateTermsNConditions(updateTermsAndConditions);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateTermsNConditionsException() {
		TncService tncService = new TncService();
		CustomResponse customResponse = tncService.updateTermsNConditions(null);
		LOGGER.info(customResponse);
	}
}