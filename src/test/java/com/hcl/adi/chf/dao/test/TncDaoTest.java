package com.hcl.adi.chf.dao.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.TncDao;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class TncDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(TncDaoTest.class.getName());

	private static TermsAndConditions createTermsAndConditions = null;
	private static TermsAndConditions updateTermsAndConditions = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		createTermsAndConditions = new TermsAndConditions();
		createTermsAndConditions.setInstitutionId(4);
		createTermsAndConditions.setCreatedBy("gtestg@ymail.com");
		createTermsAndConditions.setUpdatedBy("gtestg@ymail.com");
		createTermsAndConditions.setInstitutionId(1);
		createTermsAndConditions.setDeleteMarker("Y");
		createTermsAndConditions.setTncStatus("A");

		updateTermsAndConditions = new TermsAndConditions();
		updateTermsAndConditions.setCreatedBy("gtestg@ymail.com");
		updateTermsAndConditions.setUpdatedBy("gtestg@ymail.com");
		updateTermsAndConditions.setInstitutionId(1);
		updateTermsAndConditions.setTncId(2);
		updateTermsAndConditions.setDeleteMarker("Y");
		updateTermsAndConditions.setTncStatus("A");
		updateTermsAndConditions.setTncText("T&C Text");

	}

	@Test
	public void updateTermsNConditions() {
		updateTermsAndConditions.setInstitutionId(1);
		updateTermsAndConditions.setTncId(2);
		updateTermsAndConditions.setDeleteMarker("Y");
		CustomResponse customResponse = TncDao.updateTermsNConditions(updateTermsAndConditions);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateTermsNConditionsNotFound() {
		updateTermsAndConditions.setInstitutionId(1);
		updateTermsAndConditions.setTncId(0);
		updateTermsAndConditions.setUpdatedBy("gtestg@ymail.com");
		CustomResponse customResponse = TncDao.updateTermsNConditions(updateTermsAndConditions);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateTermsNConditionsException() {
		updateTermsAndConditions.setUpdatedBy(
				"gtestgsasasasasasasasasasasasasassasasasasasasasasasasasasasasasasasasasasasasas@ymail.com");
		CustomResponse customResponse = TncDao.updateTermsNConditions(updateTermsAndConditions);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void coverTryWithResource() {
		SetupInMemoryDBUtil.resetConnection(true);
		TncDao.updateTermsNConditions(updateTermsAndConditions);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}