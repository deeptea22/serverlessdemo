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

import com.hcl.adi.chf.enums.WatchlistAction;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.model.ChfPatientSearchRequest;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientActionComments;
import com.hcl.adi.chf.model.PatientClinicianMapping;
import com.hcl.adi.chf.model.PatientComments;
import com.hcl.adi.chf.model.PatientDeviceMapping;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.model.PatientProvider.Provider;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.model.PatientThreshold.Controls;
import com.hcl.adi.chf.model.PatientThreshold.Threshold;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class PatientServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(PatientServiceTest.class.getName());
	private static PatientProvider patientProvider = null;
	private static Provider provider = null;
	private static PatientThreshold patientThreshold = null;
	private static ChfPatientSearchRequest patientSearchReq = null;
	private static PatientDeviceMapping pdm = null;
	private static PatientActionComments pac = null;
	private static Clinician clincian = null;
	private static PatientPhi patientPhi = null; 
	private static PatientClinicianMapping patientClinicianMapping= null;
	private static Integer patientId;
	private static String chfPatientId =null;
	private static String updatedBy =null;
	

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();
		patientId =1;
		chfPatientId ="c5";
		updatedBy = "test@hcl.com";
		
		patientSearchReq = new ChfPatientSearchRequest();
		patientSearchReq.setInstitutionId(1);
		patientSearchReq.setPolicyField("First Name");
		patientSearchReq.setPolicyValue("john");
		patientSearchReq.setPageCount(1);
		patientSearchReq.setPageStartIndex(0);

		provider = new Provider();
		provider.setProviderId(0);
		provider.setProviderName("jj");
		provider.setDefault(true);
		provider.setDoctorContactNumber("7293797987");
		provider.setDoctorEmail("abc.gmail.com");
		provider.setHospitalAffilation("Apollo");
		provider.setOtherContactNumber("7968686868");
		List providerL = new ArrayList<Provider>();
		providerL.add(provider);
		patientProvider = new PatientProvider();
		patientProvider.setChfPatientId("c5");
		patientProvider.setInstitutionId(6);
		patientProvider.setProviderId(5);
		patientProvider.setProviders(providerL);
		patientProvider.setCreatedBy("vv");
		patientProvider.setCreatedTimestamp(new java.util.Date());
		patientProvider.setUpdatedBy("vv");
		patientProvider.setUpdatedTimestamp(new java.util.Date());
		
		pdm =new PatientDeviceMapping();
		pdm.setChfPatientId("c5");
		pdm.setKitId(5);
		pdm.setDeviceLabelId("ADI-1005");
		pdm.setCreatedBy("vv");
		
		pac = new PatientActionComments();
		pac.setActionId(11);
		pac.setComments("patient is fine.");
		pac.setCreatedBy("vv");
		
		Controls control = new Controls();
		control.setUnit("m");
		control.setUnitDesc("meter");
		control.setUnitName("meter");
		control.setUnitValue("25");
		
		Threshold threshold = new Threshold();
		List controlL = new ArrayList<Controls>();
		controlL.add(control);
		threshold.setControls(controlL);
		threshold.setParamDesc("distance");
		threshold.setParamName("distance");
		
		List thresholdL =new ArrayList<Threshold>();
		thresholdL.add(threshold);

		patientThreshold = new PatientThreshold();
		patientThreshold.setCreatedBy("shi@gmail.com");
		patientThreshold.setPatientId(1);
		patientThreshold.setCreatedTimestamp(new Date());
		patientThreshold.setDescription(null);
		patientThreshold.setStatusCode(0);
		patientThreshold.setThreshold(thresholdL);
		patientThreshold.setThresholdId(1);
		patientThreshold.setUpdatedBy("shi@gmail.com");
		patientThreshold.setUpdatedTimestamp(new Date());
		
		patientClinicianMapping = new PatientClinicianMapping();
		patientClinicianMapping.setClinicianId(16);
		patientClinicianMapping.setCreatedBy("vartika@hcl.com");
		patientClinicianMapping.setCreatedTimestamp(new Date());
		patientClinicianMapping.setMapId(6);
		patientClinicianMapping.setPatientId(6);
		patientClinicianMapping.setUpdatedBy("vartika@hcl.com");
		patientClinicianMapping.setUpdatedTimestamp(new Date());
	}
	@Test
	public void confirmPatient() {
		PatientService ps = new PatientService();
		CustomResponse customResponse = ps.confirmPatient(patientProvider);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);		
	}
	
	@Test
	public void confirmPatientForException() {
		PatientService ps = new PatientService();
		CustomResponse customResponse = ps.confirmPatient(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);		
	}
	
	@Test
	public void removePatientProvider() {
		PatientService ps = new PatientService();
		CustomResponse customResponse = ps.removePatientProvider(patientProvider);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);		
	}
	
	@Test
	public void removePatient() {
		PatientService ps = new PatientService();
		CustomResponse customResponse = ps.removePatientProvider(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);		
	}
	
	@Test
	public void persistPatientDeviceMapping() {
		PatientService ps = new PatientService();
		CustomResponse customResponse = ps.persistPatientDeviceMapping(pdm);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);		
	}
	
	@Test
	public void persistPatientDeviceMappingException() {
		PatientService ps = new PatientService();
		pdm.setDeviceLabelId("d2");
		CustomResponse customResponse = ps.persistPatientDeviceMapping(pdm);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);		
	}
	
	@Test
	public void addActionComment() {
		PatientService ps = new PatientService();
		PatientActionComments response = ps.addActionComment(pac);
		LOGGER.info(response);
		Assert.assertNotNull(response);		
	}
	
	@Test
	public void getPatientComments() {
		PatientService ps = new PatientService();
		List<PatientComments> patientCommentsList = ps.getPatientComments(patientId);
		LOGGER.info(patientCommentsList);
		Assert.assertNotNull(patientCommentsList);		
	}
	
	@Test
	public void checkPatientClinicianWatchListStatus() {
		PatientService ps = new PatientService();
		Boolean response = ps.checkPatientClinicianWatchListStatus( patientPhi.getPatientId(), clincian.getClinicianId());
		LOGGER.info(response);
		Assert.assertNotNull(response);		
	}
	
	@Test
	public void getPatientPhiDetailsBasedOnChfPatientId() {
		PatientService ps = new PatientService();
		PatientPhi patientPhi = ps.getPatientPhiDetailsBasedOnChfPatientId(chfPatientId);
		LOGGER.info(patientPhi);
		Assert.assertNotNull(patientPhi);		
	}
	
	@Test
	public void getPatientProviderDetailsByChfPatientId() {
		PatientService ps = new PatientService();
		PatientProvider patientProvider = ps.getPatientProviderDetailsByChfPatientId(chfPatientId);
		LOGGER.info(patientProvider);
		Assert.assertNotNull(patientProvider);		
	}
	
	@Test
	public void getPatientThresholds() {
		PatientService ps = new PatientService();
		PatientThreshold patientThreshold = ps.getPatientThresholds(patientId);
		LOGGER.info(patientThreshold);
		Assert.assertNotNull(patientThreshold);		
	}
	
	@Test
	public void restoreDefaultThresholdBasedOnPatientId() {
		PatientService ps = new PatientService();
		PatientThreshold patientThreshold = ps.restoreDefaultThresholdBasedOnPatientId(patientId);
		LOGGER.info(patientThreshold);
		Assert.assertNotNull(patientThreshold);		
	}
	
	@Test
	public void updatePatientThresholds() {
		PatientService ps = new PatientService();			
		CustomResponse response = ps.updatePatientThresholds(patientThreshold);
		LOGGER.info(response);
		Assert.assertNotNull(response);	
	}
	
	@Test
	public void deEnrollPatientBasedOnChfPatientId() {
		PatientService ps = new PatientService();			
		CustomResponse response = ps.deEnrollPatientBasedOnChfPatientId(chfPatientId, updatedBy);
		LOGGER.info(response);
		Assert.assertNotNull(response);	
	}
	
	@Test
	public void updatePatientClinicianMappingAdd() {
		patientClinicianMapping.setAction(WatchlistAction.ADD.toString());
		PatientService ps = new PatientService();
		CustomResponse customResponse = ps.updatePatientClinicianMapping(patientClinicianMapping);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updatePatientClinicianMappingDelete() {
		patientClinicianMapping.setAction(WatchlistAction.DELETE.toString());
		patientClinicianMapping.setClinicianId(15);
		patientClinicianMapping.setPatientId(5);
		PatientService ps = new PatientService();
		CustomResponse customResponse = ps.updatePatientClinicianMapping(patientClinicianMapping);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void getChfPatientIdListBySearchParamWithPagination() {
		PatientService ps = new PatientService();			
		ChfPatientListResponseForMobileApp response = ps.getChfPatientIdListBySearchParamWithPagination(patientSearchReq);
		LOGGER.info(response);
		Assert.assertNotNull(response);	
	}
	
	@Test
	public void coverTryResource() {
		PatientService ps = new PatientService();
		ps.addActionComment(null);
		ps.getPatientActions(0);
		ps.getPatientComments(0);
		ps.getPatientPhiDetailsBasedOnChfPatientId(null);
		ps.getPatientProviderDetailsByChfPatientId(null);
		ps.getChfPatientIdListBySearchParamWithPagination(null);
		ps.getPatientThresholds(0);
		ps.restoreDefaultThresholdBasedOnPatientId(0);
		ps.updatePatientThresholds(null);
		ps.updatePatientClinicianMapping(null);
		ps.deEnrollPatientBasedOnChfPatientId(null, null);
	}
}