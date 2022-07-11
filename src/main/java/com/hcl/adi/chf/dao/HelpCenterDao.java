package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.HelpCenterPageText;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on help_center table
 *
 * @author SandeepSingh
 */
public final class HelpCenterDao {
	private static final Logger LOGGER = LogManager.getLogger(HelpCenterDao.class.getName());

	private HelpCenterDao() {
		// private constructor
	}

	/**
	 * Fetch data from help_center table for help center page
	 *
	 * @return
	 */
	public static List<HelpCenterPageText> getHelpCenterPageTextList() {
		List<HelpCenterPageText> helpCenterPageTextList = new ArrayList<HelpCenterPageText>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_HELP_CENTER"));
			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					HelpCenterPageText helpCenterPageText = new HelpCenterPageText();
					helpCenterPageText.setHelpId(rs.getInt(TableConstants.HELP_CENTER_ID));
					helpCenterPageText.setHelpTopic(rs.getString(TableConstants.HELP_CENTER_HELP_TOPIC));
					helpCenterPageText.setHelpDesc(rs.getString(TableConstants.HELP_CENTER_HELP_DESC));
					helpCenterPageText.setHelpUrl(rs.getString(TableConstants.HELP_CENTER_HELP_URL));
					helpCenterPageText.setHelpType(rs.getString(TableConstants.HELP_CENTER_HELP_TYPE));
					helpCenterPageText.setCreatedBy(rs.getString(TableConstants.HELP_CENTER_CREATED_BY));
					helpCenterPageText
							.setCreatedTimestamp(rs.getTimestamp(TableConstants.HELP_CENTER_CREATED_TIMESTAMP));
					helpCenterPageText.setUpdatedBy(rs.getString(TableConstants.HELP_CENTER_UPDATED_BY));
					helpCenterPageText
							.setUpdatedTimestamp(rs.getTimestamp(TableConstants.HELP_CENTER_UPDATED_TIMESTAMP));

					helpCenterPageTextList.add(helpCenterPageText);
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return helpCenterPageTextList;
	}
}