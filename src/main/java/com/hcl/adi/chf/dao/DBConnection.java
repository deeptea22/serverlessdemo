package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.util.Constants;

/**
 * This class is to provide connection object for DB Operations
 *
 * @author AyushRa
 */
public final class DBConnection {
	private static final Logger LOGGER = LogManager.getLogger(DBConnection.class.getName());

	private Connection connection;
	private static DBConnection connectionInstance;

	// For In Memory database connection object
	// Class.forName("org.h2.Driver");
	// Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem://localhost:9092/mem:test;DB_CLOSE_DELAY=-1", "", "");

	private DBConnection() {
		try {
			String driverClassName = Constants.DB_DRIVER_CLASS_NAME;
			String dbProtocol = Constants.DB_PROTOCOL;

			if (driverClassName == null) {
				driverClassName = "com.mysql.jdbc.Driver";
			}

			if (dbProtocol == null) {
				dbProtocol = "jdbc:mysql://";
			}

			Class.forName(driverClassName);
			this.connection = DriverManager.getConnection(
					dbProtocol + Constants.DB_HOSTNAME + ":" + Constants.DB_PORT + "/" + Constants.DB_SCHEMA_NAME,
					Constants.DB_USER_NAME, Constants.DB_PASSWORD);

			LOGGER.info("Connection created successfully " + connection);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
	}

	/**
	 *
	 * @return MYSQL database connection object
	 */
	public static synchronized Connection getConnection() throws Exception {
		if (connectionInstance == null || connectionInstance.connection.isClosed()) {
			synchronized (DBConnection.class) {
				if (connectionInstance == null || connectionInstance.connection.isClosed()) {
					connectionInstance = new DBConnection();
				}
			}
		}

		return connectionInstance.connection;
	}

	// Just to test the connection object locally.
	public static void main(final String[] args) throws Exception {
		System.out.println("Host: " + Constants.DB_HOSTNAME);
		System.out.println("Port: " + Constants.DB_PORT);
		System.out.println("Schema: " + Constants.DB_SCHEMA_NAME);
		System.out.println("UserName: " + Constants.DB_USER_NAME);
		System.out.println("Password: " + Constants.DB_PASSWORD);

		getConnection();
	}
}