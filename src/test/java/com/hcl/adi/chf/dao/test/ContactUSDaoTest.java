package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.ContactUSDao;
import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class ContactUSDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(ContactUSDaoTest.class.getName());

	private static ContactUS createContactUS = null;
	private static String sesMsgId = null;
	
	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		sesMsgId = "SA";
		
		createContactUS = new ContactUS();
		createContactUS.setRequestId(137);
		createContactUS.setRequesterName("Karthick");
		createContactUS.setFromEmail("vv@gmail.com");
		createContactUS.setToEmail("vj@gmail.com");
		createContactUS.setCcEmail("sv@gmail.com");		
		createContactUS.setSubject("NG");
		createContactUS.setMessage("msg");
		createContactUS.setIsCopyRequested(true);
		createContactUS.setSesMsgId("DEACTIVATE");
		createContactUS.setCreatedBy("VV");
		createContactUS.setUpdatedBy("vv");
		createContactUS.setInstitutionId(0);
		createContactUS.setUserType("Admin");
		
	}
	@Test
	public void fetchContactUsSubjectList() {
		List<ContactUS> contactUS = ContactUSDao.fetchContactUsSubjectList();
		LOGGER.info(contactUS);
		Assert.assertNotNull(contactUS);
	}
	
	@Test
	public void persistContactUSData() {
		CustomResponse customResponse = ContactUSDao.persistContactUSData(createContactUS, sesMsgId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void updateContactUSData() {
		createContactUS.setSubject(null);
		CustomResponse customResponse = ContactUSDao.persistContactUSData(createContactUS, sesMsgId);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void exceptionTryWithResource() {
		SetupInMemoryDBUtil.resetConnection(true);
		ContactUSDao.fetchContactUsSubjectList();		
		ContactUSDao.persistContactUSData(null, null);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}