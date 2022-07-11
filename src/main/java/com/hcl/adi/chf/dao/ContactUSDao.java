package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on contact_us table
 *
 * @author Shivendra Singh
 */
public final class ContactUSDao {
	private static final Logger LOGGER = LogManager.getLogger(ContactUSDao.class.getName());

	private ContactUSDao() {
		// private constructor
	}

	/**
	 * Insert contact-us form's details into contact_us table
	 *
	 * @param contactUS
	 * @return
	 */
	public static CustomResponse persistContactUSData(final ContactUS contactUS, final String sesMsgId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsInserted = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_CONTACT_US"));

				pstmt.setString(1, contactUS.getRequesterName());
				pstmt.setString(2, Constants.CONTACT_US_FROM_EMAIL);
				pstmt.setString(3, Constants.CONTACT_US_TO_EMAIL);
				pstmt.setString(4, contactUS.getCcEmail());
				pstmt.setString(5, contactUS.getSubject());
				pstmt.setString(6, contactUS.getMessage());
				pstmt.setBoolean(7, contactUS.getIsCopyRequested());
				pstmt.setString(8, sesMsgId);
				pstmt.setString(9, contactUS.getCreatedBy());
				pstmt.setString(10, contactUS.getCreatedBy());

				numberOfRecordsInserted = pstmt.executeUpdate();

				LOGGER.info("Number of records inserted into contact_us table: " + numberOfRecordsInserted);

				response.setStatusCode(Status.OK.getStatusCode());
				response.setDescription(Status.OK.toString() + "| Reference Id: " + sesMsgId);

				connection.setAutoCommit(true);

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

				if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
					response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
					response.setDescription(
							Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
				} else {
					response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
					response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
				}

				if (connection != null && !connection.isClosed()) {
					connection.rollback();
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * Fetch data from help_center_master table for contactUS subject list
	 *
	 * @return
	 */
	public static List<ContactUS> fetchContactUsSubjectList() {
		List<ContactUS> subjectList = new ArrayList<ContactUS>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_HELP_CENTER_MASTER"));
			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
				ContactUS contactUs = new ContactUS();
				contactUs.setRequestId(rs.getInt(TableConstants.CONTACT_US_MASTER_ID));
				contactUs.setSubject(rs.getString(TableConstants.CONTACT_US_SUBJECT));
				subjectList.add(contactUs);
				}
				
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return subjectList;
	}


}