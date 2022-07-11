package com.hcl.adi.chf.service.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.service.MaxDetailService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class MaxDetailServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(MaxDetailServiceTest.class.getName());
	private static String emailId = null;

	@BeforeClass
	public static void createInput() throws IOException {

		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		emailId = "shivendra@hcl.com";
	}

	@Test
	public void fetchMaximumPossibleDetailsOfAdminByEmailId() {
		MaxDetailService maxDetailService = new MaxDetailService();
		UserDetails userDetails = maxDetailService.getMaximumPossibleDetailsOfAdminByEmailId(emailId);
		LOGGER.info(userDetails);
		Assert.assertNotNull(userDetails);
	}
}