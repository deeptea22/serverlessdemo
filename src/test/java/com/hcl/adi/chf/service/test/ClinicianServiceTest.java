package com.hcl.adi.chf.service.test;



import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class ClinicianServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(ClinicianServiceTest.class.getName());
	private static Clinician updateClinician = null;
	private static String clinicianType = null;
	private static Clinician createClinician = null;
	private static String emailId = null;
	private static LoginActivity loginActivity = null;

	@BeforeClass
	public static void createInput() throws IOException {
		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		// set up your sample input object here.
		clinicianType = "CL";

		emailId = "clinician1@gmail.com";

		createClinician = new Clinician();
		createClinician.setEmailId("clinician3@gmail.com");
		createClinician.setFirstName("Karthick");
		createClinician.setLastName("NG");
		createClinician.setEmployeeId("517518");
		createClinician.setCreatedBy("karthick@gmail.com");
		createClinician.setInstitutionId(1);
		createClinician.setType("CL");
		createClinician.setPortalAccess("WEB");
		createClinician.setDeleteMarker("N");
		createClinician.setClinicianId(5000);
		createClinician.setPoolId("us-east-1_tswTIQ0gH");
		createClinician.setStatus("A");

		updateClinician = new Clinician();
		updateClinician.setEmailId("clinician3@gmail.com");
		updateClinician.setFirstName("Karthick");
		updateClinician.setLastName("NG");
		updateClinician.setEmployeeId("517518");
		updateClinician.setCreatedBy("karthick@gmail.com");
		updateClinician.setInstitutionId(1);
		updateClinician.setClinicianId(13);
		updateClinician.setType("CL");
		updateClinician.setPortalAccess("WEB");
		updateClinician.setPoolId("us-east-1_tswTIQ0gH");
		updateClinician.setDeleteMarker("N");
		updateClinician.setStatus("A");
		updateClinician.setAction("profileupdated");

		loginActivity = new LoginActivity();
		loginActivity.setEmailId(emailId);
		loginActivity.setLoginAttempt("Success");
		loginActivity.setType("CL");

	}

	@Test
	public void updateClinicianAsNull() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.updateClinician(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);

	}

	@Test
	public void updateClinician() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.updateClinician(updateClinician);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePwdUpdatedDateColumnInClinicianByEmailId() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.updatePwdUpdatedDateColumnInClinicianByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updatePwdUpdatedDateColumnInClinicianByEmailIdNull() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.updatePwdUpdatedDateColumnInClinicianByEmailId(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void createClinicianAsNull() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.createClinician(null, null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void createClinician() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.createClinician(createClinician, clinicianType);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateIsTncAcceptedByEmailId() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.updateIsTncAcceptedColumnInClinicianByEmailId(emailId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateIsTncAcceptedByEmailIdNull() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.updateIsTncAcceptedColumnInClinicianByEmailId(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void getClinicianByEmailId() {
		ClinicianService clinicianService = new ClinicianService();
		Clinician clinician = clinicianService.getClinicianByEmailId(emailId);
		LOGGER.info(clinician);
		Assert.assertNotNull(clinician);
	}

	@Test
	public void getListOfCliniciansByInstitutionId() {
		ClinicianService clinicianService = new ClinicianService();
		List<Clinician> clinicians = clinicianService
				.getListOfCliniciansByInstitutionId(updateClinician.getInstitutionId());
		LOGGER.info(clinicians);
		Assert.assertNotNull(clinicians);
	}

	@Test
	public void getClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters() {
		ClinicianService clinicianService = new ClinicianService();
		UserDetails userDetails = clinicianService
				.getClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters(emailId, 10, 10);
		LOGGER.info(userDetails);
		Assert.assertNotNull(userDetails);
	}

	@Test
	public void getPwdExpiryInfoListOfEligibleCliniciansAndSendThatListToSQS() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.getPwdExpiryInfoListOfEligibleCliniciansAndSendThatListToSQS();
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateLoginAttemptInClinicianAndBlockUserIfRequiredSuccess() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService
				.updateLoginAttemptInClinicianAndBlockUserIfRequired(loginActivity);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateLoginAttemptInClinicianAndBlockUserIfRequiredFailure() {
		ClinicianService clinicianService = new ClinicianService();
		loginActivity.setLoginAttempt("Failure");
		CustomResponse customResponse = clinicianService
				.updateLoginAttemptInClinicianAndBlockUserIfRequired(loginActivity);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void unblockCliniciansWhoseCoolingPeriodHasExpired() {
		ClinicianService clinicianService = new ClinicianService();
		CustomResponse customResponse = clinicianService.unblockCliniciansWhoseCoolingPeriodHasExpired();
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void coverUpdateException() {
		ClinicianService clinicianService = new ClinicianService();
		updateClinician.setPoolId(null);
		updateClinician.setDeleteMarker("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN"
				+ "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN"
				+ "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
		updateClinician.setInstitutionId(null);
		updateClinician.setStatus("asdfasdfgasdjhgfkahsdgfkhasjdgfkasdjh");
		clinicianService.updateClinician(updateClinician);
	}
		
	@Test
	public void coverTryResource() {
		ClinicianService clinicianService = new ClinicianService();
		clinicianService.getClinicianByEmailId(emailId);
		clinicianService.createClinician(createClinician, clinicianType);
		clinicianService.updateClinician(updateClinician);
		clinicianService.updateClinician(updateClinician);
	}
}