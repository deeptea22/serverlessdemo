package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.AdminDao;
import com.hcl.adi.chf.dao.ClinicianDao;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class ClinicianDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(ClinicianDaoTest.class.getName());

	private static Clinician updateClinicians = null;
	private static Clinician createClinician = null;
	private static String emailId = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();
		
		emailId = "maga123@gmail.com";

		createClinician = new Clinician();
		createClinician.setEmailId("varshney@gmail.com");
		createClinician.setFirstName("Karthick");
		createClinician.setLastName("NG");
		createClinician.setEmployeeId("517518");
		createClinician.setCreatedBy("varshney@gmail.com");
		createClinician.setAction("DEACTIVATE");
		createClinician.setClinicianId(137);
		createClinician.setPortalAccess("WEB");
		createClinician.setInstitutionId(0);
		createClinician.setPoolId("us-east-1_tswTIQ0gH");
		createClinician.setStatus("A");
		createClinician.setType("SA");
		createClinician.setDeleteMarker("N");

		updateClinicians = new Clinician();
		updateClinicians.setEmailId("varshney@gmail.com");
		updateClinicians.setFirstName("Karthick");
		updateClinicians.setLastName("NG");
		updateClinicians.setEmployeeId("517518");
		updateClinicians.setCreatedBy("varshney@gmail.com");
		updateClinicians.setAction("DEACTIVATE");
		updateClinicians.setClinicianId(13);
		updateClinicians.setPortalAccess("WEB");
		updateClinicians.setInstitutionId(0);
		updateClinicians.setPoolId("us-east-1_tswTIQ0gH");
		updateClinicians.setStatus("A");
		updateClinicians.setType("SA");
		updateClinicians.setDeleteMarker("N");

	}

	@Test
	public void fetchClinicianDetailBasedOnEmailID() {
		emailId = "clinician1@gmail.com";
		Clinician clinician = ClinicianDao.fetchClinicianDetailBasedOnEmailID(emailId);
		LOGGER.info(clinician);
		Assert.assertNotNull(clinician);

	}

	@Test
	public void fetchClinicianDetailBasedOnEmailIDWithStatusChange() {
		emailId = "clinician8@gmail.com";
		Clinician clinician = ClinicianDao.fetchClinicianDetailBasedOnEmailID(emailId);
		LOGGER.info(clinician);
		Assert.assertNotNull(clinician);

	}
	
	@Test
	public void fetchEligibleCliniciansForPwdExpiryNotification() {
		List<PwdExpiryInfo> pwdExpryInfo = ClinicianDao.fetchEligibleCliniciansForPwdExpiryNotification();
		LOGGER.info(pwdExpryInfo);
		Assert.assertNotNull(pwdExpryInfo);

	}
	
	@Test
	public void fetchClinicianListOnInstitutionID() throws Exception {
		List<Clinician> clinicianList = ClinicianDao.fetchClinicianListOnInstitutionID(1);
		LOGGER.info(clinicianList);
		Assert.assertNotNull(clinicianList);

	}
	
	@Test
	public void fetchCliniciansWhoseCoolingPeriodHasExpired() {
		List<Clinician> clinician = ClinicianDao.fetchCliniciansWhoseCoolingPeriodHasExpired();
		LOGGER.info(clinician);
		Assert.assertNotNull(clinician);

	}
	
	@Test
	public void insertRecordIntoClinician() {
		CustomResponse customResponse = ClinicianDao.insertRecordIntoClinician(createClinician,"SA");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void blockClinicianInDBAndCognitoPool() {
		emailId= "clinician2@gmail.com";
		CustomResponse customResponse = ClinicianDao.blockClinicianInDBAndCognitoPool(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateRecordIntoClinicianForSpecifiedLoginFailureActivity() {
		emailId= "clinician2@gmail.com";
		CustomResponse customResponse = ClinicianDao.updateRecordIntoClinicianForSpecifiedLoginActivity(emailId,"Failure");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateRecordIntoClinicianForSpecifiedLoginSuccessActivity() {
		emailId= "clinician2@gmail.com";
		CustomResponse customResponse = ClinicianDao.updateRecordIntoClinicianForSpecifiedLoginActivity(emailId,"Success");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void unblockClinicianByEmailId() {
		emailId= "clinician2@gmail.com";
		CustomResponse customResponse = ClinicianDao.unblockClinicianByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateClinician() {
		CustomResponse customResponse = ClinicianDao.updateClinician(updateClinicians,updateClinicians.getAction());
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateClinicianWithProfileUpdated() {
		updateClinicians.setAction("profileupdated");
		CustomResponse customResponse = ClinicianDao.updateClinician(updateClinicians,updateClinicians.getAction());
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void exceptionTryWithResource() {
		SetupInMemoryDBUtil.resetConnection(true);
		ClinicianDao.fetchClinicianDetailBasedOnEmailID(null);
		try {
			ClinicianDao.fetchClinicianListOnInstitutionID(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ClinicianDao.fetchClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters(null, 0, 0);
		ClinicianDao.fetchEligibleCliniciansForPwdExpiryNotification();
		ClinicianDao.fetchCliniciansWhoseCoolingPeriodHasExpired();
		ClinicianDao.updateClinician(null, null);
		ClinicianDao.updatePwdUpdatedDateColumnInClinicianByEmailId(null);
		ClinicianDao.updateIsTncAcceptedColumnInClinicianByEmailId(null);
		ClinicianDao.blockClinicianInDBAndCognitoPool(null);
		ClinicianDao.updateRecordIntoClinicianForSpecifiedLoginActivity(null, null);
		ClinicianDao.unblockClinicianByEmailId(null);
		ClinicianDao.insertRecordIntoClinician(null, null);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}

