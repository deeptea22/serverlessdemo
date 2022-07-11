package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.AdminDao;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class AdminDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(AdminDaoTest.class.getName());

	private static String adminType = null;
	private static Integer adminId = null;
	private static String emailId = null;
	private static String actionString = null;
	private static int institutionId;
	private static Admins updateAdmins = null;
	private static Admins createAdmins = null;
	private static String isDefault = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		adminType = "CL";
		adminId = 12;
		emailId = "admin2@gmail.com";
		institutionId = 0;
		actionString = "DEACTIVATE";
		isDefault = "Y";
		createAdmins = new Admins();
		createAdmins.setEmailId("manoj@gmail.com");
		createAdmins.setFirstName("Karthick");
		createAdmins.setLastName("NG");
		createAdmins.setEmployeeId("517518");
		createAdmins.setCreatedBy("manoj@gmail.com");
		createAdmins.setAction("DEACTIVATE");
		createAdmins.setAdminId(137);
		createAdmins.setPortalAccess("WEB");
		createAdmins.setInstitutionId(0);
		createAdmins.setPoolId("us-east-1_tswTIQ0gH");
		createAdmins.setStatus("A");
		createAdmins.setType("SA");
		createAdmins.setIsDefault("N");
		createAdmins.setDeleteMarker("N");
		createAdmins.setCreatedBy("vv");
		createAdmins.setCreatedTimestamp(new Date());
		createAdmins.setUpdatedBy("vv");
		createAdmins.setUpdatedTimestamp(new Date());

		updateAdmins = new Admins();
		updateAdmins.setEmailId("manoj@gmail.com");
		updateAdmins.setFirstName("Karthick");
		updateAdmins.setLastName("NG");
		updateAdmins.setEmployeeId("517518");
		updateAdmins.setCreatedBy("manoj@gmail.com");
		updateAdmins.setAction("ACTIVATE");
		updateAdmins.setAdminId(13);
		updateAdmins.setPortalAccess("WEB");
		updateAdmins.setInstitutionId(0);
		updateAdmins.setPoolId("us-east-1_tswTIQ0gH");
		updateAdmins.setStatus("A");
		updateAdmins.setType("SA");
		updateAdmins.setIsDefault("N");
		updateAdmins.setDeleteMarker("N");
		updateAdmins.setCreatedBy("vv");
		updateAdmins.setCreatedTimestamp(new Date());
		updateAdmins.setUpdatedBy("vv");
		updateAdmins.setUpdatedTimestamp(new Date());

	}

	@Test
	public void fetchAdminListOnAdminType() {
		List<Admins> adminsList = AdminDao.fetchAdminListOnAdminType(adminType);
		LOGGER.info(adminsList);
		Assert.assertNotNull(adminsList);
	}
	
	@Test
	public void fetchAdminDetailBasedOnAdminID() {
		Admins admins = AdminDao.fetchAdminDetailBasedOnAdminID(adminId);
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void fetchInstitutionAdminListOnInstitutionID() throws Exception {
		
			List<Admins> adminsList = AdminDao.fetchAdminListOnInstitutionID(institutionId);
			LOGGER.info(adminsList);
			Assert.assertNotNull(adminsList);
	}

	@Test
	public void fetchAdminDetailBasedOnEmailID() {
		Admins admins = AdminDao.fetchAdminDetailBasedOnEmailID(emailId);
		LOGGER.info(admins);
		Assert.assertNotNull(admins);

	}
	@Test
	public void updateAdmin() {
		CustomResponse customResponse = AdminDao.updateAdmin(updateAdmins, actionString);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdminNullFail() {
		updateAdmins.setPoolId(null);
		updateAdmins.setInstitutionId(null);
		CustomResponse customResponse = AdminDao.updateAdmin(updateAdmins, actionString);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdminWithActivate() {
		updateAdmins.setInstitutionId(0);
		updateAdmins.setAdminId(13);
		CustomResponse customResponse = AdminDao.updateAdmin(updateAdmins, "c");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateAdminWithProfileUpdate() {
		updateAdmins.setEmailId("admin2@gmail.com");
		CustomResponse customResponse = AdminDao.updateAdmin(updateAdmins, "PROFILEUPDATED");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePwdExpiryInfoByEmailId() {
		CustomResponse customResponse = AdminDao.updatePwdUpdatedDateColumnInAdminsByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePwdExpiryInfoByEmailIdCondition() {
		CustomResponse customResponse = AdminDao.updatePwdUpdatedDateColumnInAdminsByEmailId(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateIsTncAcceptedInAdminsByEmailId() {
		CustomResponse customResponse = AdminDao.updateIsTncAcceptedColumnInAdminsByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateIsTncAcceptedInAdminsByEmailIdNull() {
		CustomResponse customResponse = AdminDao.updateIsTncAcceptedColumnInAdminsByEmailId(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateAdminBYInstitutionId() {
		CustomResponse customResponse = AdminDao.updateAdminOnInstitutionId(emailId, institutionId, actionString);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	@Test
	public void updateAdminOnInstitutionId() {
		CustomResponse customResponse = AdminDao.updateAdminOnInstitutionId(null, institutionId, actionString);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdminOnInstitutionIdActive() {
		CustomResponse customResponse = AdminDao.updateAdminOnInstitutionId(null, institutionId, "ACTIVATE");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdminOnInstitutionIdDeactive() {
		CustomResponse customResponse = AdminDao.updateAdminOnInstitutionId(null, institutionId, "DEACTIVATE");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdminOnInstitutionIdDelete() {
		CustomResponse customResponse = AdminDao.updateAdminOnInstitutionId(null, institutionId, "DELETE");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdminOnInstitutionIdAsNill() {
		CustomResponse customResponse = AdminDao.updateAdminOnInstitutionId(null, 0, null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdminOnInstitutionIdCover() {
		CustomResponse customResponse = AdminDao.updateAdminOnInstitutionId(null, 5000, "ACTIVATE");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void insertRecordIntoAdmins() {
		CustomResponse customResponse = AdminDao.insertRecordIntoAdmins(createAdmins, "IA", isDefault);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void insertRecordIntoAdminsElse() {
		createAdmins.setAdminId(50000);
		CustomResponse customResponse = AdminDao.insertRecordIntoAdmins(createAdmins, adminType, isDefault);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void insertRecordIntoAdminsElseIA() {
		createAdmins.setAdminId(50000);
		CustomResponse customResponse = AdminDao.insertRecordIntoAdmins(createAdmins, "IA", isDefault);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void insertRecordIntoAdminsElseNull() {
		createAdmins.setAdminId(50000);
		CustomResponse customResponse = AdminDao.insertRecordIntoAdmins(null, null, isDefault);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void fetchEligibleAdminsForPwdExpiryNotification() {
		List<PwdExpiryInfo> pwdExpiryInformationResponses = AdminDao.fetchEligibleAdminsForPwdExpiryNotification();
		LOGGER.info(pwdExpiryInformationResponses);
		Assert.assertNotNull(pwdExpiryInformationResponses);
	}

	

	@Test
	public void updateIsTncAcceptedInAdmins() {
		CustomResponse customResponse = AdminDao.updateIsTncAcceptedInAdmins(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateIsTncAcceptedInAdminsNull() {
		CustomResponse customResponse = AdminDao.updateIsTncAcceptedInAdmins(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void unblockAdminByEmailId() {
		emailId = "var@hcl.com";
		CustomResponse customResponse = AdminDao.unblockAdminByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void unblockAdminByEmailIdNull() {
		CustomResponse customResponse = AdminDao.unblockAdminByEmailId(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void fetchAdminsWhoseCoolingPeriodHasExpired() {
		List<Admins> admins = AdminDao.fetchAdminsWhoseCoolingPeriodHasExpired();
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void updateRecordIntoAdminsForSpecifiedLoginActivity() {
		CustomResponse customResponse = AdminDao.updateRecordIntoAdminsForSpecifiedLoginActivity(emailId, "2");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void blockAdminInDBAndCognitoPool() {
		emailId = "admin6@gmail.com";
		CustomResponse customResponse = AdminDao.blockAdminInDBAndCognitoPool(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void fetchRemainingLoginAttempts() {
		emailId = "admin6@gmail.com";
		int remainingLoginAttempts = AdminDao.fetchRemainingLoginAttempts(emailId);
		LOGGER.info(remainingLoginAttempts);
		Assert.assertNotNull(remainingLoginAttempts);
	}
	
	// Try Resource Exception

	@Test
	public void updatePwdExpiryInfoByEmailIdTryResource() {
		SetupInMemoryDBUtil.resetConnection(true);
		AdminDao.updateAdminOnInstitutionId(null, 0, null);
		AdminDao.updateAdmin(null, null);
		AdminDao.fetchAdminDetailBasedOnEmailID(null);
		AdminDao.fetchEligibleAdminsForPwdExpiryNotification();
		AdminDao.updateIsTncAcceptedInAdmins(emailId);
		AdminDao.updateIsTncAcceptedColumnInAdminsByEmailId(emailId);
		AdminDao.unblockAdminByEmailId(emailId);
		AdminDao.fetchRemainingLoginAttempts(null);
		try {
			AdminDao.fetchAdminListOnInstitutionID(0);
		} catch (Exception e) {

		}
		AdminDao.fetchAdminDetailBasedOnAdminID(0);
		AdminDao.fetchAdminListOnAdminType(null);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}