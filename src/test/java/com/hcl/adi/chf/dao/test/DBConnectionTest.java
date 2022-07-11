package com.hcl.adi.chf.dao.test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.dao.DBConnection;

public class DBConnectionTest {
	private static final Logger LOGGER = LogManager.getLogger(DBConnectionTest.class.getName());

	@BeforeClass
	public static void createInput() throws IOException {

	}

	@Test
	public void getConnection() {
		try {
			Connection connection = DBConnection.getConnection();
			LOGGER.info(connection);
			Assert.assertNotNull(connection);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Test
	public void createPrivateObject() {
		try {
			Class<?> classs = Class.forName("com.hcl.adi.chf.dao.DBConnection");
			Constructor<?> constructor = classs.getDeclaredConstructors()[0];
			constructor.setAccessible(true);
			constructor.newInstance();
		} catch (Exception e) {

		}
	}
}