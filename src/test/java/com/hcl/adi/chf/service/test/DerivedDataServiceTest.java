package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.PatientRecordResponse;
import com.hcl.adi.chf.service.DerivedDataService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class DerivedDataServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(AuditLogServiceTest.class.getName());
	private static Map<String, String> input;
	private static Date startDate;
	private static Date endDate;
	private static int patientId;

	@BeforeClass
	public static void createInput() throws IOException {
		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_PATIENT_ID, "1");
		input.put(Constants.QUERY_PARAM_CLINICIAN_ID, "4");
		input.put(Constants.QUERY_PARAM_START_DATE, "2019-04-20");
		input.put(Constants.QUERY_PARAM_END_DATE, "2019-04-23");
		startDate = new Date();
		endDate = new Date();
		patientId = 1;
	}

	@Test
	public void getDerivedDataByPatientId() {
		DerivedDataService dataService = new DerivedDataService();
		PatientRecordResponse patientRecordResponse = dataService.getDerivedDataByPatientId(patientId, startDate,
				endDate);
		LOGGER.info(patientRecordResponse);
		Assert.assertNotNull(patientRecordResponse);
	}
}