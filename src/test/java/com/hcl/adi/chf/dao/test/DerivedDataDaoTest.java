package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.DerivedDataDao;
import com.hcl.adi.chf.model.PatientRecordResponse;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class DerivedDataDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(DerivedDataDaoTest.class.getName());
	private static SimpleDateFormat tsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static Integer patientId = null;
	private static Date startDate = null;
	private static Date endDate = null;

	@BeforeClass
	public static void createInput() throws IOException, ParseException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();
		
		patientId = 1;
		startDate = tsdf.parse("2019-04-21 09:45:22");
		endDate = new Date();		
	}

	@Test
	public void testFetchDerivedDataByPatientId() {
		PatientRecordResponse response = DerivedDataDao.fetchDerivedDataByPatientId(patientId,startDate,endDate);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}		
	
}