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

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.model.PatientReadingResponse;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.model.PatientVitals.CustomVitals;
import com.hcl.adi.chf.model.ThresholdBreachRequest;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;


public class ClinicianDashboardServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(DataArchivalPolicyServiceTest.class.getName());
	private static PatientVitals updatePatientVitals = null;
	private static PatientVitals createPatientVitals = null;
	private static ThresholdBreachRequest createThresholdRequest = null;
	private static Integer patientId = null;
	private static Integer packetId = null;
	private static String emailId = null;
	
	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

//		adminType = "SA";
		patientId = 1;
		packetId =1;
		emailId = "abc@abc.com";
		CustomVitals customVitals = new CustomVitals();
		customVitals.setLabel("meter");
		customVitals.setValue("25");
		List customVitalL = new ArrayList<CustomVitals>();
		customVitalL.add(customVitals);
		createPatientVitals = new PatientVitals();
		createPatientVitals.setPatientVitalsId(10);
		createPatientVitals.setPatientId(1);
		createPatientVitals.setSystolicBP(100F);
		createPatientVitals.setDiastolicBP(120F);
		createPatientVitals.setWeight(25f);
		createPatientVitals.setTemperature(29.9f);
		createPatientVitals.setCustomVitals("true");
		createPatientVitals.setCustomVitalsList(customVitalL);
		createPatientVitals.setReadingDate(new Date());
		createPatientVitals.setCreatedBy("vv");
		createPatientVitals.setCreatedTimeStamp(new Date());
		createPatientVitals.setUpdatedBy("vv");
		createPatientVitals.setUpdatedTimeStamp(new Date());

		updatePatientVitals = new PatientVitals();
		updatePatientVitals.setPatientVitalsId(1);
		updatePatientVitals.setPatientId(1);
		updatePatientVitals.setSystolicBP(110F);
		updatePatientVitals.setDiastolicBP(120F);
		updatePatientVitals.setWeight(25f);
		updatePatientVitals.setTemperature(29.9f);
		updatePatientVitals.setCustomVitals("false");
		updatePatientVitals.setCustomVitalsList(customVitalL);
		updatePatientVitals.setReadingDate(new Date());
		updatePatientVitals.setCreatedBy("vv");
		updatePatientVitals.setCreatedTimeStamp(new Date());
		updatePatientVitals.setUpdatedBy("vv");
		updatePatientVitals.setUpdatedTimeStamp(new Date());
		
		createThresholdRequest = new ThresholdBreachRequest();
		createThresholdRequest.setPatientId(2);
		createThresholdRequest.setInstitutionId(0);
		createThresholdRequest.setEmailId(emailId);
	}
	
	@Test
	public void getEcgDataByPatientId() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		List<DeviceData> deviceDataList = clinicianDashboardService.getEcgDataByPatientId(patientId);
		LOGGER.info(deviceDataList);
		Assert.assertNotNull(deviceDataList);
	}
	@Test
	public void persistPatientVitals() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		PatientVitals patientVitals = clinicianDashboardService.persistPatientVitals(createPatientVitals);
		LOGGER.info(patientVitals);
		Assert.assertNotNull(patientVitals);
	}
	
	@Test
	public void updatePatientVitalsOnPatientVitalsId() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		CustomResponse customResponse = clinicianDashboardService.updatePatientVitalsOnPatientVitalsId(updatePatientVitals);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void getDeviceDataDetailsByPacketId() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		DeviceData deviceData = clinicianDashboardService.getDeviceDataDetailsByPacketId(packetId);
		LOGGER.info(deviceData);
		Assert.assertNotNull(deviceData);
	}
	
	@Test
	public void updateDeviceReadingStatusByPacketId() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		CustomResponse customResponse = clinicianDashboardService.updateDeviceReadingParameter(packetId,"markAsBaseline",emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateDeviceReadingStatusByPacketIdException() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		CustomResponse customResponse = clinicianDashboardService.updateDeviceReadingParameter(null,null,null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateDeviceReadingStatusByPacketIdWithActivate() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		CustomResponse customResponse = clinicianDashboardService.updateDeviceReadingParameter(packetId,"ACTIVATE",emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateAlertStatusByPatientId() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		CustomResponse customResponse = clinicianDashboardService.updateAlertStatusByPatientId(createThresholdRequest);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateAlertStatusByPatientIdException() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		CustomResponse customResponse = clinicianDashboardService.updateAlertStatusByPatientId(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void getPatientVitalsByPatientId() {
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		PatientVitals patientVitals = clinicianDashboardService.getPatientVitalsByPatientId(patientId);
		LOGGER.info(patientVitals);
		Assert.assertNotNull(patientVitals);
	}
	
	@Test
	public void getDeviceDataByPatientId() {
		patientId = 2;
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		PatientReadingResponse patientReadingResponse = clinicianDashboardService.getDeviceDataByPatientId(patientId,1,1);
		LOGGER.info(patientReadingResponse);
		Assert.assertNotNull(patientReadingResponse);
	}
	
	@Test
	public void isBaseLineExistsByPatientId() {
		patientId =2;
		ClinicianDashboardService clinicianDashboardService = new ClinicianDashboardService();
		CustomResponse response = clinicianDashboardService.isBaseLineExistsByPatientId(patientId);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}
}
