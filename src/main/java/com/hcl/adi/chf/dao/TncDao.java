package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.util.SQLUtils;

/**
 * This class is to perform DB Operations on terms_n_conditions table
 *
 * @author AyushRa
 */
public final class TncDao {
	private static final Logger LOGGER = LogManager.getLogger(TncDao.class.getName());

	private TncDao() {
		// private constructor
	}

	/**
	 * Update record in terms_n_conditions table. Also, update is_tnc_accepted
	 * column to 'N' in admins & clinician table for all the users
	 *
	 * @param tnc
	 * @return
	 */
	public static CustomResponse updateTermsNConditions(final TermsAndConditions tnc) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_TERMS_N_CONDITIONS_ON_ID"));
				pstmt.setString(1, tnc.getUpdatedBy());
				pstmt.setString(2, tnc.getTncStatus());
				pstmt.setString(3, tnc.getDeleteMarker());
				pstmt.setString(4, tnc.getTncText());
				pstmt.setInt(5, tnc.getTncId());

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					// Now, update isTncAccepted flag for all the admins
					CustomResponse adminResponse = AdminDao.updateIsTncAcceptedInAdmins(tnc.getUpdatedBy());

					if (adminResponse.getStatusCode() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
						response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
						response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + adminResponse.getDescription());
					}
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

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
}