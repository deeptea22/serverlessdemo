package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on pwd_policy_mgmt table
 *
 * @author DivyaAg
 */
public final class PwdPolicyDao {
	private static final Logger LOGGER = LogManager.getLogger(PwdPolicyDao.class.getName());

	private PwdPolicyDao() {
		// private constructor
	}

	/**
	 * Fetch password policy details based on institutionId
	 *
	 * @param institutionId
	 * @return
	 */
	public static PasswordPolicy fetchPwdPolicyDetailBasedOnInstitutionID(final int institutionId) {
		PasswordPolicy passwordPolicy = new PasswordPolicy();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PWD_POICY_MGMT_BY_INSTITUTION_ID"));

			pstmt.setInt(1, institutionId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				passwordPolicy.setPwdPolicyId(rs.getInt(TableConstants.PWD_POLICY_ID));
				passwordPolicy.setInstitutionId(rs.getInt(TableConstants.PWD_POLICY_INSTITUTION_ID));
				passwordPolicy.setPwdRotationInDays(rs.getInt(TableConstants.PWD_POLICY_ROTATION_IN_DAYS));
				passwordPolicy.setPwdMinLength(rs.getInt(TableConstants.PWD_POLICY_MIN_LENGTH));
				passwordPolicy.setPwdMaxLength(rs.getInt(TableConstants.PWD_POLICY_MAX_LENGTH));
				passwordPolicy.setIsCapsAllowed(rs.getString(TableConstants.PWD_POLICY_IS_CAPS_ALLOWED));
				passwordPolicy.setIsLowerAllowed(rs.getString(TableConstants.PWD_POLICY_IS_LOWER_ALLOWED));
				passwordPolicy.setIsNumericAllowed(rs.getString(TableConstants.PWD_POLICY_IS_NUMERIC_ALLOWED));
				passwordPolicy.setIsSplCharAllowed(rs.getString(TableConstants.PWD_POLICY_IS_SPL_CHAR_ALLOWED));
				passwordPolicy.setRetryLoginAttemptsAllowed(
						rs.getInt(TableConstants.PWD_POLICY_RETRY_LOGIN_ATTEMPTS_ALLOWED));
				passwordPolicy.setPwdHistory(rs.getInt(TableConstants.PWD_POLICY_HISTORY));
				passwordPolicy.setProhibitedPasswords(rs.getString(TableConstants.PWD_POLICY_PROHIBITED_PASSWORDS));
				passwordPolicy.setPwdPolicyStatus(rs.getString(TableConstants.PWD_POLICY_STATUS));
				passwordPolicy.setDeleteMarker(rs.getString(TableConstants.PWD_POLICY_DELETE_MARKER));
				passwordPolicy.setCreatedBy(rs.getString(TableConstants.PWD_POLICY_CREATED_BY));
				passwordPolicy.setCreatedTimestamp(rs.getTimestamp(TableConstants.PWD_POLICY_CREATED_TIMESTAMP));
				passwordPolicy.setUpdatedBy(rs.getString(TableConstants.PWD_POLICY_UPDATED_BY));
				passwordPolicy.setUpdatedTimestamp(rs.getTimestamp(TableConstants.PWD_POLICY_UPDATED_TIMESTAMP));
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return passwordPolicy;
	}

	/**
	 * Update password policy in pwd_policy_mgmt table. In case of successful
	 * updation, update pwd_expire_in_days field in institution table as well
	 *
	 * @param passwordPolicy
	 * @return
	 */
	public static CustomResponse updatePwdPolicy(final PasswordPolicy passwordPolicy) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_PWD_POLICY_ON_ID"));
				pstmt.setInt(1, passwordPolicy.getInstitutionId());
				pstmt.setInt(2, passwordPolicy.getPwdRotationInDays());
				pstmt.setInt(3, passwordPolicy.getPwdMinLength());
				pstmt.setInt(4, passwordPolicy.getPwdMaxLength());
				pstmt.setString(5, passwordPolicy.getIsCapsAllowed());
				pstmt.setString(6, passwordPolicy.getIsLowerAllowed());
				pstmt.setString(7, passwordPolicy.getIsNumericAllowed());
				pstmt.setString(8, passwordPolicy.getIsSplCharAllowed());
				pstmt.setInt(9, passwordPolicy.getRetryLoginAttemptsAllowed());
				pstmt.setInt(10, passwordPolicy.getPwdHistory());
				pstmt.setString(11, passwordPolicy.getProhibitedPasswords());
				pstmt.setString(12, passwordPolicy.getPwdPolicyStatus());
				pstmt.setString(13, passwordPolicy.getDeleteMarker());
				pstmt.setString(14, passwordPolicy.getUpdatedBy());
				pstmt.setInt(15, passwordPolicy.getPwdPolicyId());

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					// Now, update pwd_expire_in_days field in institution table
					// based on institution id.
					CustomResponse institutionResponse = InstitutionDao.updateInstitutionForPwd_expire_in_days(
							passwordPolicy.getPwdRotationInDays(), passwordPolicy.getUpdatedBy(),
							passwordPolicy.getInstitutionId());

					if (institutionResponse.getStatusCode() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
						response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
						response.setDescription(
								Status.INTERNAL_SERVER_ERROR.toString() + ": " + institutionResponse.getDescription());
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