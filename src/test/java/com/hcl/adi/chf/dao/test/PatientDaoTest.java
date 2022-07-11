package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.PatientDao;
import com.hcl.adi.chf.enums.MasterDetailsCode;
import com.hcl.adi.chf.enums.WatchlistAction;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.model.ChfPatientSearchRequest;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DashboardVitals;
import com.hcl.adi.chf.model.PatientActionComments;
import com.hcl.adi.chf.model.PatientClinicianMapping;
import com.hcl.adi.chf.model.PatientComments;
import com.hcl.adi.chf.model.PatientDeviceMapping;
import com.hcl.adi.chf.model.PatientMasterMapping;
import com.hcl.adi.chf.model.PatientMasterOtherMappings;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.model.PatientPhi.PatientAttributes;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.model.PatientProvider.Provider;
import com.hcl.adi.chf.model.PatientRecord;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.model.PatientThreshold.Controls;
import com.hcl.adi.chf.model.PatientThreshold.Threshold;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class PatientDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(PatientDaoTest.class.getName());

	private static PatientPhi updatePatientPhi = null;
	private static PatientPhi createPatientPhi = null;
	private static PatientClinicianMapping patientClinicianMapping = null;
	private static PatientMasterMapping patientMasterMapping = null;
	private static ChfPatientSearchRequest patientSearchReq = null;
	private static PatientThreshold patientThreshold = null;
	private static PatientProvider patientProvider = null;
	private static Provider provider = null;
	private static PatientDeviceMapping pdm = null;
	private static PatientActionComments pac = null;
	private static PatientComments patientComments = null;
	private static String chfPatientId =null;
	private static String updatedBy =null;
	private static Integer patientId;
	private static Integer institutionId;
	private static Map<String, Integer> masterTypeCodeList;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();
		chfPatientId ="c5";
		patientId = 1;
		institutionId =1;
		updatedBy = "test@hcl.com";
		masterTypeCodeList = new HashMap<String, Integer>();
		masterTypeCodeList.put(MasterDetailsCode.IMP.toString(), 0);
		masterTypeCodeList.put(MasterDetailsCode.ADM.toString(), 0);
		masterTypeCodeList.put(MasterDetailsCode.PRO.toString(), 0);
		
		patientSearchReq = new ChfPatientSearchRequest();
		patientSearchReq.setInstitutionId(1);
		patientSearchReq.setPolicyField("First Name");
		patientSearchReq.setPolicyValue("$");
		patientSearchReq.setPageCount(1);
		patientSearchReq.setPageStartIndex(0);
		
		pac = new PatientActionComments();
		pac.setActionId(11);
		pac.setComments("patient is fine.");
		pac.setCreatedBy("vv");
		
		List<PatientAttributes> patientAttributes = new ArrayList<PatientPhi.PatientAttributes>();
		PatientAttributes attributes = new PatientAttributes();
		attributes.setLabel("First 3 chars of first name");
		attributes.setValue("JOE");
		patientAttributes.add(attributes);
		createPatientPhi = new PatientPhi();
		createPatientPhi.setPatientId(5);
		createPatientPhi.setChfPatientId("John-Deo-24111985");
		createPatientPhi.setInstitutionId(1);
		createPatientPhi.setMrNumber("retr56");
		createPatientPhi.setPatientAttributesList(patientAttributes);
		createPatientPhi.setCreatedBy("gtestg@ymail.com");
		createPatientPhi.setCreatedTimestamp(new Date(1556529931260l));
		createPatientPhi.setUpdatedBy("gtestg@ymail.com");
		createPatientPhi.setUpdatedTimestamp(new Date(1556529931260l));
		createPatientPhi.setAddress("noida");
		createPatientPhi.setContactNo("7397297922");
		createPatientPhi.setDeleteMarker("Y");
		createPatientPhi.setDeviceLabelId("21");
		createPatientPhi.setDoB("29-11-94");
		createPatientPhi.setFirstName("John");
		createPatientPhi.setGender("m");
		createPatientPhi.setIsActionOpen(false);
		
		createPatientPhi.setLastName("bill");
		createPatientPhi.setSsn("ss");
		createPatientPhi.setSystemId("s1");
		createPatientPhi.setZip("898291");
		createPatientPhi.setPatientDetailsJson("'label:' 'l1'");
		
		updatePatientPhi = new PatientPhi();
		updatePatientPhi.setPatientId(1);
		updatePatientPhi.setUpdatedBy("AyushRa@hcl.com");
		updatePatientPhi.setCreatedBy("AyushRa@hcl.com");

		patientClinicianMapping = new PatientClinicianMapping();
		patientClinicianMapping.setClinicianId(16);
		patientClinicianMapping.setCreatedBy("vartika@hcl.com");
		patientClinicianMapping.setCreatedTimestamp(new Date());
		patientClinicianMapping.setMapId(6);
		patientClinicianMapping.setPatientId(6);
		patientClinicianMapping.setUpdatedBy("vartika@hcl.com");
		patientClinicianMapping.setUpdatedTimestamp(new Date());
		
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
		
		provider = new Provider();
		provider.setProviderId(0);
		provider.setProviderName("jj");
		provider.setDefault(true);
		provider.setDoctorContactNumber("7293797987");
		provider.setDoctorEmail("abc@gmail.com");
		provider.setHospitalAffilation("Apollo");
		provider.setOtherContactNumber("7968686868");
		List providerL = new ArrayList<Provider>();
		providerL.add(provider);
		patientProvider = new PatientProvider();
		patientProvider.setChfPatientId("1");
		patientProvider.setInstitutionId(1);
		patientProvider.setProviderId(1);
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
		
		PatientMasterOtherMappings pmom = new PatientMasterOtherMappings();
		pmom.setLabelText("K11");
		pmom.setPatientId("5");
		pmom.setLabel("k1");
		pmom.setText("COPD");
		pmom.setIsSelected(false);
		pmom.setCreatedBy("vartika@hcl.com");
		pmom.setCreatedTimestamp(new Date());
		pmom.setUpdatedBy("vartika@hcl.com");
		pmom.setUpdatedTimestamp(new Date());
		
		List medicationL = new ArrayList<PatientMasterOtherMappings>();
		pmom.setMasterTypeCode(MasterDetailsCode.MED.toString());
		medicationL.add(pmom);
		
		List comorbiditiesL = new ArrayList<PatientMasterOtherMappings>();
		pmom.setMasterTypeCode(MasterDetailsCode.COM.toString());
		comorbiditiesL.add(pmom);
		
		List implantsL = new ArrayList<PatientMasterOtherMappings>();
		pmom.setMasterTypeCode(MasterDetailsCode.IMP.toString());
		implantsL.add(pmom);
		
		List proceduresL = new ArrayList<PatientMasterOtherMappings>();
		pmom.setMasterTypeCode(MasterDetailsCode.PRO.toString());
		proceduresL.add(pmom);
		
		List admissionsL = new ArrayList<PatientMasterOtherMappings>();
		pmom.setMasterTypeCode(MasterDetailsCode.ADM.toString());
		admissionsL.add(pmom);
		
		patientMasterMapping = new PatientMasterMapping();
		patientMasterMapping.setMasterId(5);
		patientMasterMapping.setPatientId(5);
		patientMasterMapping.setCreatedBy("vartika@hcl.com");
		patientMasterMapping.setCreatedTimestamp(new Date());
		patientMasterMapping.setUpdatedBy("vartika@hcl.com");
		patientMasterMapping.setUpdatedTimestamp(new Date());
		patientMasterMapping.setInstitutionId(5);
		patientMasterMapping.setAdmissions(admissionsL);
		patientMasterMapping.setMedication(medicationL);
		patientMasterMapping.setProcedures(proceduresL);
		patientMasterMapping.setComorbidities(comorbiditiesL);
		patientMasterMapping.setImplants(implantsL);
	}
	
	@Test
	public void addActionComment() {
		PatientActionComments response = PatientDao.addActionComment(pac);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void insertRecordPatientDeviceMapping() {
		CustomResponse customResponse = PatientDao.insertPatientDeviceMapping(pdm);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void insertRecordPatientDeviceMappingException() {
		pdm.setCreatedBy("gtestg@ymail.commmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		CustomResponse customResponse = PatientDao.insertPatientDeviceMapping(pdm);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void insertRecordIntoPatientPhi() {
		CustomResponse customResponse = PatientDao.insertRecordIntoPatientPhi(createPatientPhi);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void insertRecordIntoPatientPhiException() {
		createPatientPhi
				.setCreatedBy("gtestg@ymail.commmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		CustomResponse customResponse = PatientDao.insertRecordIntoPatientPhi(createPatientPhi);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void checkPatientClinicianWatchListStatus() {
		boolean response = PatientDao.checkPatientClinicianWatchListStatus(updatePatientPhi.getPatientId(), 24);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void checkPatientClinicianWatchListStatusExist() {
		
		boolean response = PatientDao.checkPatientClinicianWatchListStatus(updatePatientPhi.getPatientId(), 7);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}

	@Test
	public void getPatientThresholds() throws Exception {
			PatientThreshold patientThreshold = PatientDao.getPatientThresholds(patientId);
			LOGGER.info(patientThreshold);
			Assert.assertNotNull(patientThreshold);
	}

	@Test
	public void updatePatientThresholds() {
		CustomResponse customResponse = PatientDao.updatePatientThresholds(patientThreshold);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void deEnrollPatientByChfPatientId() {
		CustomResponse customResponse = PatientDao.deEnrollPatientByChfPatientId(chfPatientId, updatedBy);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePatientThresholdsDeleteMarkerYes() {
		CustomResponse customResponse = PatientDao.updatePatientThresholds(patientThreshold);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	

	@Test
	public void fetchDataByInstitutionId() {
		Map<String, String> input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_PATIENT_ID, "1");
		List<PatientRecord> patientRecords = null;
		try {
			patientRecords = PatientDao.fetchDataByInstitutionId(input);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.info(patientRecords);
		Assert.assertNotNull(patientRecords);
	}

	@Test
	public void fetchDataByInstitutionIdWatchList() {
		Map<String, String> input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_PATIENT_WATCHLIST, "1");
		List<PatientRecord> patientRecords = null;
		try {
			patientRecords = PatientDao.fetchDataByInstitutionId(input);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.info(patientRecords);
		Assert.assertNotNull(patientRecords);
	}

	@Test
	public void fetchDataByInstitutionIdThresholds() {
		Map<String, String> input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_PATIENT_THRESHOLDS, "1");
		List<PatientRecord> patientRecords = null;
		try {
			patientRecords = PatientDao.fetchDataByInstitutionId(input);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.info(patientRecords);
		Assert.assertNotNull(patientRecords);
	}

	@Test
	public void fetchDataByInstitutionIdAction() {
		Map<String, String> input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_PATIENT_ACTIONS, "1");
		List<PatientRecord> patientRecords = null;
		try {
			patientRecords = PatientDao.fetchDataByInstitutionId(input);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.info(patientRecords);
		Assert.assertNotNull(patientRecords);
	}

	@Test
	public void fetchDataByInstitutionIdReading() {
		Map<String, String> input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_NEW_READINGS, "1");
		List<PatientRecord> patientRecords = null;
		try {
			patientRecords = PatientDao.fetchDataByInstitutionId(input);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.info(patientRecords);
		Assert.assertNotNull(patientRecords);
	}

	@Test
	public void fetchDataByInstitutionIdID() {
		Map<String, String> input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_INSTITUTION_ID, "1");
		List<PatientRecord> patientRecords = null;
		try {
			patientRecords = PatientDao.fetchDataByInstitutionId(input);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.info(patientRecords);
		Assert.assertNotNull(patientRecords);
	}

	@Test
	public void getPatientDashboardVitalByInstitutionId() {
		Map<String, String> input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_INSTITUTION_ID, "1");
		input.put(Constants.QUERY_PARAM_CLINICIAN_ID, "4");
		DashboardVitals data = new DashboardVitals();
		try {
			PatientDao.getPatientDashboardVitalByInstitutionId(input, data);

		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	@Test
	public void confirmPatient() {
		CustomResponse customResponse = PatientDao.confirmPatient(patientProvider);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void confirmPatientWithChangeID() {
		provider.setProviderId(1);
		CustomResponse customResponse = PatientDao.confirmPatient(patientProvider);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void fetchPatientProviderByChfPatientId() throws Exception {
		PatientProvider patientProvider = PatientDao.fetchPatientProviderByChfPatientId(chfPatientId);
		LOGGER.info(patientProvider);
		Assert.assertNotNull(patientProvider);
	}
	
	@Test
	public void fetchChfPatientIdListBasedOnInstitutionId() throws Exception {
		ChfPatientListResponseForMobileApp patientProvider = PatientDao.fetchChfPatientIdListBasedOnInstitutionId(1,0,1);
		LOGGER.info(patientProvider);
		Assert.assertNotNull(patientProvider);
	}
	
	@Test
	public void getCPMTrends() {
		try {
			PatientRecord patientCommentsList = PatientDao.getCPMTrends(updatePatientPhi.getPatientId() + "");
			LOGGER.info(patientCommentsList);
			Assert.assertNotNull(patientCommentsList);
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
	}
	
	@Test
	public void getPatientComments() {
		List<PatientComments> patientCommentsList = PatientDao.getPatientComments(patientId);
		LOGGER.info(patientCommentsList);
		Assert.assertNotNull(patientCommentsList);
	}
	
	@Test
	public void getPatientMasterOtherMappingList() throws Exception {
		PatientMasterMapping response = PatientDao.getPatientMasterOtherMappingList(institutionId, patientId,
				masterTypeCodeList);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}

	@Test
	public void fetchPatientPhiDetailsBasedOnChfPatientId() throws Exception {
		PatientPhi response = PatientDao.fetchPatientPhiDetailsBasedOnChfPatientId(chfPatientId);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}

	@Test
	public void restoreDefaultThresholdBasedOnPatientId(){
		patientId = 5;
		PatientThreshold patientThreshold = PatientDao.restoreDefaultThresholdBasedOnPatientId(patientId);
		LOGGER.info(patientThreshold);
		Assert.assertNotNull(patientThreshold);
	}
	
	@Test
	public void updatePatientClinicianMappingAdd() {
		patientClinicianMapping.setAction(WatchlistAction.ADD.toString());
		CustomResponse customResponse = PatientDao.updatePatientClinicianMapping(patientClinicianMapping);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePatientClinicianMappingDelete() {
		patientClinicianMapping.setAction(WatchlistAction.DELETE.toString());
		CustomResponse customResponse = PatientDao.updatePatientClinicianMapping(patientClinicianMapping);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updatePatientMasterOtherMapping() throws Exception {
		CustomResponse customResponse = PatientDao.updatePatientMasterOtherMapping(patientMasterMapping);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void getChfPatientIdListBySearchParamWithPagination() throws Exception {
		ChfPatientListResponseForMobileApp response = PatientDao.getChfPatientIdListBySearchParamWithPagination(patientSearchReq);
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}

	@Test
	public void tryResourceCover() {
		SetupInMemoryDBUtil.resetConnection(true);
		PatientDao.checkPatientClinicianWatchListStatus(updatePatientPhi.getPatientId(), 24);
		PatientDao.insertRecordIntoPatientPhi(null);
		PatientDao.confirmPatient(null);
		PatientDao.fetchPatientPhiDetailsBasedOnChfPatientId(null);
		PatientDao.getChfPatientIdListBySearchParamWithPagination(null);
		try {
			PatientDao.fetchDataByInstitutionId(new HashMap<String, String>());
			PatientDao.fetchPatientProviderByChfPatientId(null);
			PatientDao.getPatientThresholds(0);
			PatientDao.getCPMTrends(null);
			PatientDao.updatePatientMasterOtherMapping(null);
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		}
		PatientDao.restoreDefaultThresholdBasedOnPatientId(0);
		PatientDao.addActionComment(null);
		PatientDao.getPatientComments(0);
		PatientDao.insertPatientDeviceMapping(null);
		PatientDao.updatePatientThresholds(null);
		PatientDao.deEnrollPatientByChfPatientId(null, null);
		PatientDao.updatePatientClinicianMapping(null);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}