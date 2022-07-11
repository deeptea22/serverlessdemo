package com.hcl.adi.chf.service.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.HelpCenterResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.service.HelpCenterService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class HelpCenterServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(HelpCenterServiceTest.class.getName());
	
	@Test
	public void getHelpCenterPageTextList() {
		HelpCenterService helpCenterService = new HelpCenterService();
		HelpCenterResponse helpCenterResponse = helpCenterService.getHelpCenterPageTextList();
		LOGGER.info(helpCenterResponse);
		Assert.assertNotNull(helpCenterResponse);
	}
	
}