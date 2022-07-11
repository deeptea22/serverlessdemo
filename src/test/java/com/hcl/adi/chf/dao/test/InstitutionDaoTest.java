package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.InstitutionDao;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class InstitutionDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(InstitutionDaoTest.class.getName());


	private static String actionString = null;
	private static int institutionId;
	private static Institution updateInstitution = null;
	private static Institution createInstitution = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		
		institutionId = 1;
		actionString = "DEACTIVATE";

		createInstitution = new Institution();
		createInstitution.setInstitutionName("JP Jayaprakash");
		createInstitution.setContactPerson("Jayprakash");
		createInstitution.setAddress("NoidaIndia");
		createInstitution.setContactNumber("87654329871");
		createInstitution.setInstitutionId(18);
		createInstitution.setAction("DEACTIVATE");
		createInstitution.setInstitutionStatus("A");
		createInstitution.setDeleteMarker("Y");
		createInstitution.setCreatedBy("vv");
		createInstitution.setCreatedTimestamp(new Date());
		createInstitution.setUpdatedBy("vv");
		createInstitution.setUpdatedTimestamp(new Date());

		updateInstitution = new Institution();
		updateInstitution.setInstitutionName("JK Jayaprakash");
		updateInstitution.setContactPerson("Jayprakash");
		updateInstitution.setAddress("NoidaIndia");
		updateInstitution.setContactNumber("87654329871");
		updateInstitution.setInstitutionId(18);
		updateInstitution.setAction("DEACTIVATE");
		updateInstitution.setInstitutionStatus("A");
		updateInstitution.setDeleteMarker("Y");
		updateInstitution.setCreatedBy("vv");
		updateInstitution.setCreatedTimestamp(new Date());
		updateInstitution.setUpdatedBy("vv");
		updateInstitution.setUpdatedTimestamp(new Date());


	}

	@Test
	public void addInstitution() {
		CustomResponse customResponse = InstitutionDao.insertRecordIntoInstitution(createInstitution);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void addInstitutionIdIgnoreAdd() {
		createInstitution.setInstitutionId(0);
		CustomResponse customResponse = InstitutionDao.insertRecordIntoInstitution(createInstitution);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void addClinicianOnInstitutionIdDropException() {
		createInstitution.setContactNumber("11111111111111111111111111111111111111111111111111111111111111111"
				+ "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
				+ "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		CustomResponse customResponse = InstitutionDao.insertRecordIntoInstitution(createInstitution);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void fetchInstitutionDetailBasedOnInstitutionID() {
		Institution institution = InstitutionDao.fetchInstitutionDetailsBasedOnInstitutionID(institutionId);
		LOGGER.info(institution);
		Assert.assertNotNull(institution);

	}
	
	@Test
	public void fetchInstitutionList() {
		List<Institution> institutions = InstitutionDao.fetchInstitutionList();
		LOGGER.info(institutions);
		Assert.assertNotNull(institutions);
	}

	@Test
	public void fetchAllActivatedInstitutionList() {
		List<Institution> institutions = InstitutionDao.fetchAllActivatedInstitutionList();
		LOGGER.info(institutions);
		Assert.assertNotNull(institutions);

	}	

	@Test
	public void updateClinicianOnInstitutionId() {
		CustomResponse customResponse = InstitutionDao.updateInstitution(updateInstitution, actionString);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateClinicianOnInstitutionIdActivate() {
		CustomResponse customResponse = InstitutionDao.updateInstitution(updateInstitution, "ACTIVATE");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateClinicianOnInstitutionIdNoAction() {
		updateInstitution.setInstitutionId(0);
		CustomResponse customResponse = InstitutionDao.updateInstitution(updateInstitution, "NOACTION");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateClinicianOnInstitutionIdActivateNoUpdate() {
		updateInstitution.setInstitutionId(5000);
		CustomResponse customResponse = InstitutionDao.updateInstitution(updateInstitution, "ACTIVATE");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	/*@Test
	public void updateInstitutionForPwdExpireInDays() {
		CustomResponse customResponse = InstitutionDao.updateInstitutionForPwd_expire_in_days(
				createInstitution.getPwdExpireInDays(), createInstitution.getUpdatedBy(),
				createInstitution.getInstitutionId());
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateInstitutionForPwdExpireInDaysIdNotExist() {
		CustomResponse customResponse = InstitutionDao.updateInstitutionForPwd_expire_in_days(
				createInstitution.getPwdExpireInDays(), createInstitution.getUpdatedBy(),89);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}*/

	@Test
	public void updateClinicianOnInstitutionIdActivateNoAction() {
		updateInstitution.setAction("NOACTION");
		CustomResponse customResponse = InstitutionDao.updateInstitution(updateInstitution, "NOACTION");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void updateClinicianOnInstitutionIdDelete() {
		CustomResponse customResponse = InstitutionDao.updateInstitution(updateInstitution, "DELETE");
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void exceptionTryWithResource() {
		SetupInMemoryDBUtil.resetConnection(true);
		
		InstitutionDao.updateInstitution(null, null);
		InstitutionDao.fetchInstitutionList();
		InstitutionDao.fetchAllActivatedInstitutionList();
		InstitutionDao.fetchInstitutionDetailsBasedOnInstitutionID(0);
		InstitutionDao.updateInstitutionForPwd_expire_in_days(0,null,0);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}