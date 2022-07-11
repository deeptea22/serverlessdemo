package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class AdminServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(AdminServiceTest.class.getName());
	private static String adminType = null;
	private static Integer adminId = null;
	private static String emailId = null;
	private static Admins updateAdmins = null;
	private static Admins createAdmins = null;
	private static LoginActivity loginActivity = null;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		adminType = "SA";
		adminId = 13;
		emailId = "admin2@gmail.com";
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

		updateAdmins = new Admins();
		updateAdmins.setEmailId("admin2@gmail.com");
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

		loginActivity = new LoginActivity();
		loginActivity.setEmailId("admin6@gmail.com");
		loginActivity.setLoginAttempt("SA");
		loginActivity.setType("SA");
	}

	@Test
	public void testGetAdminTypeAsNull() {
		AdminService adminService = new AdminService();
		List<Admins> admins = adminService.getAdmins(null);
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void testGetAdminType() {
		AdminService adminService = new AdminService();
		List<Admins> admins = adminService.getAdmins(adminType);
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void getAdminByAdminId() {
		AdminService adminService = new AdminService();
		Admins admins = adminService.getAdminByAdminId(adminId);
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void getAdminByEmailId() {
		AdminService adminService = new AdminService();
		Admins admins = adminService.getAdminByEmailId(emailId);
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void getAdminByEmailIdAsNull() {
		AdminService adminService = new AdminService();
		Admins admins = adminService.getAdminByEmailId(null);
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void updateAdminAsNull() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.updateAdmin(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateAdmin() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.updateAdmin(updateAdmins);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void createAdminAsNull() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.createAdmin(null, null, "YES");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void createAdmin() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.createAdmin(createAdmins, adminType, "Y");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void coverOtherAdminInsert() {
		AdminService adminService = new AdminService();
		createAdmins.setAdminId(50000);
		CustomResponse customResponse = adminService.createAdmin(createAdmins, adminType, "Y");
		adminService.createAdmin(createAdmins, adminType, "Y");
		adminService.createAdmin(createAdmins, "IA", "Y");
		adminService.createAdmin(null, null, "Y");
		LOGGER.info(customResponse);
	}

	@Test
	public void coverOtherAdminUpdate() {
		AdminService adminService = new AdminService();
		updateAdmins.setInstitutionId(0);
		updateAdmins.setAdminId(0);
		updateAdmins.setAction("DELETE");
		adminService.updateAdmin(updateAdmins);
		updateAdmins.setAction("ACTIVATE");
		adminService.updateAdmin(updateAdmins);
		updateAdmins.setAction("DEACTIVATE");
		adminService.updateAdmin(updateAdmins);
		updateAdmins.setInstitutionId(5000);
		updateAdmins.setAdminId(5000);
		updateAdmins.setAction("DEACTIVATE");
		adminService.updateAdmin(updateAdmins);
		updateAdmins.setAction("ACTIVATE");

		updateAdmins.setAdminId(137);
		updateAdmins.setPoolId(null);
		updateAdmins.setIsDefault("YESYSEYSEYSYEYSEYSEYSYESYYE");
		adminService.updateAdmin(updateAdmins);
	}

	@Test
	public void getListOfAdminsByInstitutionId() {
		AdminService adminService = new AdminService();
		createAdmins.setAdminId(50000);
		List<Admins> admins = adminService.getListOfAdminsByInstitutionId(createAdmins.getInstitutionId());
		LOGGER.info(admins);
		Assert.assertNotNull(admins);
	}

	@Test
	public void updatePwdUpdatedDateColumnInAdminsByEmailId() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService
				.updatePwdUpdatedDateColumnInAdminsByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	/*@Test
	public void getPwdExpiryInfoListOfEligibleAdminsAndSendThatListToSQS() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.getPwdExpiryInfoListOfEligibleAdminsAndSendThatListToSQS();
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}*/

	@Test
	public void updateIsTncAcceptedColumnInAdminsByEmailId() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService
				.updateIsTncAcceptedColumnInAdminsByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateLoginAttemptInAdminsAndBlockUserIfRequiredFail() {
		loginActivity.setLoginAttempt(Constants.LOGIN_FAILURE);
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.updateLoginAttemptInAdminsAndBlockUserIfRequired(loginActivity);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateLoginAttemptInAdminsAndBlockUserIfRequiredSuccess() {
		loginActivity.setLoginAttempt(Constants.LOGIN_SUCCESS);
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.updateLoginAttemptInAdminsAndBlockUserIfRequired(loginActivity);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void unblockAdminsWhoseCoolingPeriodHasExpired() {
		AdminService adminService = new AdminService();
		CustomResponse customResponse = adminService.unblockAdminsWhoseCoolingPeriodHasExpired();
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePwdExpiryInfoByEmailIdTryResource() {
		AdminService adminService = new AdminService();
		adminService.createAdmin(createAdmins, adminType, "YES");
		adminService.updateAdmin(updateAdmins);
		adminService.getAdminByEmailId(null);
		adminService.getAdminByAdminId(adminId);
		adminService.getAdmins(null);
	}
}