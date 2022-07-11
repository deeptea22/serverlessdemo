package com.hcl.adi.chf.dao.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.MaxDetailDao;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class MaxDetailDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(MaxDetailDaoTest.class.getName());
	private static String emailId = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		emailId = "admin9@gmail.com";
	}

	@Test
	public void fetchMaximumPossibleDetailsOfAdminByEmailId() {
		UserDetails userDetails = MaxDetailDao.fetchMaximumPossibleDetailsOfAdminByEmailId(emailId);
		LOGGER.info(userDetails);
		Assert.assertNotNull(userDetails);
	}

	@Test
	public void exceptionTryWithResource() {
		SetupInMemoryDBUtil.resetConnection(true);
		MaxDetailDao.fetchMaximumPossibleDetailsOfAdminByEmailId(emailId);
		SetupInMemoryDBUtil.resetConnection(false);
	}
}
