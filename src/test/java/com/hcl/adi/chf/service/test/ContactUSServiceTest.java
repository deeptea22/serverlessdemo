package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.model.PatientReadingResponse;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.model.PatientVitals.CustomVitals;
import com.hcl.adi.chf.model.ThresholdBreachRequest;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.service.ContactUSService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;


public class ContactUSServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(DataArchivalPolicyServiceTest.class.getName());
	private static ContactUS contactUS = null;
	
	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();
		contactUS = new ContactUS();
		contactUS.setRequestId(137);
		contactUS.setRequesterName("Karthick");
		contactUS.setFromEmail("vv@gmail.com");
		contactUS.setToEmail("vj@gmail.com");
		contactUS.setCcEmail("sv@gmail.com");		
		contactUS.setSubject("NG");
		contactUS.setMessage("msg");
		contactUS.setIsCopyRequested(true);
		contactUS.setSesMsgId("DEACTIVATE");
		contactUS.setCreatedBy("VV");
		contactUS.setInstitutionId(0);
		contactUS.setUserType("SA");
	}
	
	@Test
	public void performContactUSRequest() {
		ContactUSService contactUSService = new ContactUSService();
		CustomResponse response = contactUSService.performContactUSRequest(contactUS);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}
	
	
}
