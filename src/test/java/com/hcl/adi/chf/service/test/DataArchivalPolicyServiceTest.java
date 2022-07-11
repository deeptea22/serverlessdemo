package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DataArchivalPolicy;
import com.hcl.adi.chf.service.DataArchivalPolicyService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class DataArchivalPolicyServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(DataArchivalPolicyServiceTest.class.getName());
	private static DataArchivalPolicy dataArchiPoli = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		dataArchiPoli = new DataArchivalPolicy();
		dataArchiPoli.setArchivalPeriodInMonths(12);
		dataArchiPoli.setInstitutionId(1);
		dataArchiPoli.setAutoLogOffTimeInMinutes(20);
		dataArchiPoli.setAutoArchivalFrequency("20");
		dataArchiPoli.setCreatedBy("shivendra@hcl.com");
		dataArchiPoli.setCreatedTimestamp(new Date());
		dataArchiPoli.setDataArchivalPolicyStatus("ACTIVE");
		dataArchiPoli.setDeleteMarker("N");
		dataArchiPoli.setDurationToStoreAuditLogsInMonths(20);
		dataArchiPoli.setUpdatedBy("shivendra@hcl.com");
		dataArchiPoli.setUpdatedTimestamp(new Date());
	}

	@Test
	public void fetchDataArchivalPolicyDetailBasedOnInstitutionID() {
		DataArchivalPolicyService archivalPolicyService = new DataArchivalPolicyService();
		CustomResponse customResponse = archivalPolicyService.updateDataArchivalPolicy(dataArchiPoli);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void fetchDataArchivalPolicyDetailBasedOnInstitutionIDNull() {
		DataArchivalPolicyService archivalPolicyService = new DataArchivalPolicyService();
		CustomResponse customResponse = archivalPolicyService.updateDataArchivalPolicy(null);
		LOGGER.info(customResponse);
	}
}