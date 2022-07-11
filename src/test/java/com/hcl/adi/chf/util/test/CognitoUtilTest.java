package com.hcl.adi.chf.util.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.util.CognitoUtil;

public class CognitoUtilTest {
	private static final Logger LOGGER = LogManager.getLogger(CognitoUtilTest.class.getName());
	private static String userName = null;
	private static String groupName = null;
	private static String emailId = null;

	@BeforeClass
	public static void createInput() throws IOException {
		userName = "Shivendra_Super_Admin";
		groupName = "SA";
		emailId = "shi@gmail.com";
	}

	@Test
	public void addUserCognito() {
		CognitoUtil.addUser(userName, emailId, groupName);
	}

	@Test
	public void disableUserCognito() {
		CognitoUtil.disableUser(userName);
	}

	@Test
	public void deleteUserCognito() {
		CognitoUtil.deleteUser(userName);
	}

	@Test
	public void performActionOnCognitoPoolDeacivate() {
		CognitoUtil.performActionOnCognitoPool(Actions.DEACTIVATE, userName);
	}

	@Test
	public void performActionOnCognitoPoolActivate() {
		CognitoUtil.performActionOnCognitoPool(Actions.ACTIVATE, userName);
	}

	@Test
	public void performActionOnCognitoPoolDelete() {
		CognitoUtil.performActionOnCognitoPool(Actions.DELETE, userName);
	}

	@Test
	public void performActionOnCognitoPoolList() {
		CognitoUtil.performActionOnCognitoPool(Actions.LIST, userName);
	}
}