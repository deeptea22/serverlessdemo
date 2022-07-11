package com.hcl.adi.chf.util.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.util.EnvironmentVariablesDecrypter;

public class EnvironmentVariableDecrypterTest {
	private static final Logger LOGGER = LogManager.getLogger(EnvironmentVariableDecrypterTest.class.getName());
	private static String envName = null;
	
	@BeforeClass
	public static void createInput() throws IOException {
		envName = "env";
	}

	@Test
	public void testDecryptKey() {
		EnvironmentVariablesDecrypter.decryptKey(envName);
	}
}