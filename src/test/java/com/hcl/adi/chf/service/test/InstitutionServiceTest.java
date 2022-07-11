package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.service.InstitutionService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class InstitutionServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(InstitutionServiceTest.class.getName());

	private static Institution updateInstitution = null;
	private static int institutionId;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		institutionId = 15;

		updateInstitution = new Institution();
		updateInstitution.setInstitutionName("JP Jayaprakash");
		updateInstitution.setContactPerson("Jayprakash");
		updateInstitution.setAddress("NoidaIndia");
		updateInstitution.setContactNumber("87654329871");
//		updateInstitution.setInstitutionId(15);
		updateInstitution.setAction("DEACTIVATE");
		updateInstitution.setInstitutionStatus("A");
		updateInstitution.setDeleteMarker("Y");
	}

	@Test
	public void addInstitution() {
		updateInstitution.setContactNumber("87654329871");
//		updateInstitution.setInstitutionId(15);
		InstitutionService institutionService = new InstitutionService();
		CustomResponse customResponse = institutionService.createInstitution(updateInstitution);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void addInstitutionAsNull() {
		InstitutionService institutionService = new InstitutionService();
		CustomResponse customResponse = institutionService.createInstitution(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateInstitution() {
		updateInstitution.setInstitutionId(15);
		InstitutionService institutionService = new InstitutionService();
		CustomResponse customResponse = institutionService.updateInstitution(updateInstitution);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateInstitutionAsNull() {
		InstitutionService institutionService = new InstitutionService();
		CustomResponse customResponse = institutionService.updateInstitution(null);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void getInstitutions() {
		InstitutionService institutionService = new InstitutionService();
		List<Institution> institutions = institutionService.getInstitutions();
		LOGGER.info(institutions);
		Assert.assertNotNull(institutions);
	}

	@Test
	public void getInstitutionByInstitutionId() {
		InstitutionService institutionService = new InstitutionService();
		Institution institutions = institutionService.getInstitutionDetailsByInstitutionId(17);
		LOGGER.info(institutions);
		Assert.assertNotNull(institutions);
	}
	
	@Test
	public void getInstitutionDetailsByInstitutionId() {
		InstitutionService institutionService = new InstitutionService();
		Institution institution = institutionService
				.getInstitutionDetailsByInstitutionId(1);
		LOGGER.info(institution);
		Assert.assertNotNull(institution);
	}

	@Test
	public void getActivatedInstitutions() {
		InstitutionService institutionService = new InstitutionService();
		List<Institution> institutions = institutionService.getActivatedInstitutions();
		LOGGER.info(institutions);
		Assert.assertNotNull(institutions);
	}

	@Test
	public void updateClinicianOnInstitutionException() {
		updateInstitution.setContactNumber("11111111111111111111111111111111111111111111111111111111111111111"
				+ "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		InstitutionService institutionService = new InstitutionService();
		institutionService.updateInstitution(updateInstitution);
	}

	@Test
	public void addClinicianOnInstitutionIdException() {
		updateInstitution.setContactNumber("11111111111111111111111111111111111111111111111111111111111111111"
				+ "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		InstitutionService institutionService = new InstitutionService();
		institutionService.createInstitution(updateInstitution);
	}

	@Test
	public void addClinicianOnInstitutionIdNotFound() {
		updateInstitution.setInstitutionId(9000);
		InstitutionService institutionService = new InstitutionService();
		institutionService.createInstitution(updateInstitution);
	}

	@Test
	public void updateClinicianOnInstitutionIdNotFound() {
		updateInstitution.setInstitutionId(9000);
		InstitutionService institutionService = new InstitutionService();
		institutionService.updateInstitution(updateInstitution);
	}

	@Test
	public void createInstitution() {
		InstitutionService institutionService = new InstitutionService();
		CustomResponse customResponse = institutionService.createInstitution(updateInstitution);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	

	@Test
	public void exceptionTryWithResource() {
		InstitutionService institutionService = new InstitutionService();
		institutionService.getActivatedInstitutions();
		institutionService.updateInstitution(updateInstitution);
		institutionService.getInstitutionDetailsByInstitutionId(institutionId);
		institutionService.getInstitutions();
		institutionService.createInstitution(updateInstitution);
	}
}