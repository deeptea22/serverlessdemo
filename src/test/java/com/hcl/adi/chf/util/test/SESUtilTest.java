package com.hcl.adi.chf.util.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.util.SESUtil;

public final class SESUtilTest {
	private static final Logger LOGGER = LogManager.getLogger(SESUtilTest.class.getName());

	private static String emailID = null;
	private static String mailContent = null;

	@BeforeClass
	public static void createInput() throws IOException {
		emailID = "";
		mailContent = "";
	}

	@Test
	public void addUser() {
		SESUtil.sendEmailToSpecifiedEmailID(emailID, mailContent, null, null, null);
	}
}