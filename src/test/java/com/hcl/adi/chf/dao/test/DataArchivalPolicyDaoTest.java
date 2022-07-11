package com.hcl.adi.chf.dao.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.DataArchivalPolicyDao;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DataArchivalPolicy;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class DataArchivalPolicyDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(DataArchivalPolicyDaoTest.class.getName());
	private static DataArchivalPolicy dataArchiPoli = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

	
	}

	@Test
	public void fetchDataArchivalPolicyDetailBasedOnInstitutionID() {
		DataArchivalPolicy dataArchivalPolicy = DataArchivalPolicyDao
				.fetchDataArchivalPolicyDetailBasedOnInstitutionID(dataArchiPoli.getInstitutionId());
		LOGGER.info(dataArchivalPolicy);
		Assert.assertNotNull(dataArchivalPolicy);
	}

	@Test
	public void updateDataArchivalPolicy() {
		CustomResponse customResponse = DataArchivalPolicyDao.updateDataArchivalPolicy(dataArchiPoli);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	
	
}
