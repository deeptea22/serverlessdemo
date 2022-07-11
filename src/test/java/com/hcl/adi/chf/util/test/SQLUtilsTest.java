package com.hcl.adi.chf.util.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.util.SQLUtils;

public class SQLUtilsTest {
	private static final Logger LOGGER = LogManager.getLogger(SQLUtilsTest.class.getName());

	private static String key = null;

	@BeforeClass
	public static void createInput() throws IOException {
		key = "SELECT_FROM_CLINICIAN_ON_INSTITUTION_ID";

	}

	@Test
	public void getSQLQuery() {
		String result = SQLUtils.getSQLQuery(key);
		LOGGER.info(result);
		Assert.assertNotNull(result);
	}

	@Test
	public void getSQLQueryException() {
		String result = SQLUtils.getSQLQuery("Exception");
		LOGGER.info(result);
		Assert.assertNull(result);
	}

	@Test
	public void getSQLQueryAsNull() {
		String result = SQLUtils.getSQLQuery(null);
		LOGGER.info(result);
		Assert.assertNull(result);
	}
}