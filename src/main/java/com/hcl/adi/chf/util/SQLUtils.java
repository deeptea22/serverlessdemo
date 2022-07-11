package com.hcl.adi.chf.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This utility class will provide the SQL query from the properties file
 *
 * @author AyushRa
 */
public final class SQLUtils {
	private static final Logger LOGGER = LogManager.getLogger(SQLUtils.class.getName());
	private static final String BUNDLE_NAME = Constants.SQL_PROPERTIES_FILE_LOCATION;

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private SQLUtils() {
		// private constructor
	}

	/**
	 * This method will return the SQL query from properties file based on key
	 *
	 * @param key
	 * @return
	 */
	public static String getSQLQuery(final String key) {
		String query = null;

		try {
			query = RESOURCE_BUNDLE.getString(key);

		} catch (MissingResourceException e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return query;
	}
}