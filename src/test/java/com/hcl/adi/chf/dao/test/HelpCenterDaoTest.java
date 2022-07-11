package com.hcl.adi.chf.dao.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.hcl.adi.chf.dao.HelpCenterDao;
import com.hcl.adi.chf.model.HelpCenterPageText;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class HelpCenterDaoTest {
	private static final Logger LOGGER = LogManager.getLogger(HelpCenterDaoTest.class.getName());

	
	@Test
	public void getHelpCenterPageTextList() {
		List<HelpCenterPageText> helpCenterPageText = HelpCenterDao.getHelpCenterPageTextList();
		LOGGER.info(helpCenterPageText);
		Assert.assertNotNull(helpCenterPageText);
	}
	
	@Test
	public void tryWithResourceCoverage() {

		SetupInMemoryDBUtil.resetConnection(true);
		HelpCenterDao.getHelpCenterPageTextList();
		
		SetupInMemoryDBUtil.resetConnection(false);
	}

}