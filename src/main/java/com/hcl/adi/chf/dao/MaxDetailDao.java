package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.enums.UserStatus;
import com.hcl.adi.chf.model.DataArchivalPolicy;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on multiple tables to provide maximum
 * possible details of an admin
 *
 * @author AyushRa
 */
public final class MaxDetailDao {
	private static final Logger LOGGER = LogManager.getLogger(MaxDetailDao.class.getName());

	private MaxDetailDao() {
		// private constructor
	}

	/**
	 * Fetch maximum possible details of an admin based on email id. This method
	 * will perform join operation on admins, institution, pwd_policy_mgmt, and
	 * terms_n_conditions tables. Then, from the result set, it will populate a
	 * custom object named as UserDetails and will return that populated
	 * UserDetails object
	 *
	 * @param emailId
	 * @return
	 */
	public static UserDetails fetchMaximumPossibleDetailsOfAdminByEmailId(final String emailId) {
		UserDetails userDetails = new UserDetails();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try (Connection connection = DBConnection.getConnection();) {
			pstmt = connection.prepareStatement(
					SQLUtils.getSQLQuery("SELECT_MAXIMUM_DETAILS_FROM_ADMINS_AND_OTHER_TABLES_ON_EMAIL_ID"));
			pstmt.setString(1, emailId);
			rs = pstmt.executeQuery();

			populateUserDetails(rs, userDetails);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return userDetails;
	}

	/**
	 * This method will populate the UserDetails object from fetched data
	 *
	 * @param rs
	 * @param userDetails
	 * @throws SQLException
	 */
	private static void populateUserDetails(final ResultSet rs, final UserDetails userDetails) throws SQLException {
		if (rs != null && rs.next()) {
			userDetails.setId(rs.getInt(TableConstants.ADMIN_ID));
			userDetails.setEmailId(rs.getString(TableConstants.ADMIN_EMAIL_ID));
			userDetails.setFirstName(rs.getString(TableConstants.ADMIN_FIRST_NAME));
			userDetails.setLastName(rs.getString(TableConstants.ADMIN_LAST_NAME));
			userDetails.setEmployeeId(rs.getString(TableConstants.ADMIN_EMPLOYEE_ID));
			userDetails.setPoolId(rs.getString(TableConstants.ADMIN_POOL_ID));
			userDetails.setType(rs.getString(TableConstants.ADMIN_TYPE));
			userDetails.setPortalAccess(rs.getString(TableConstants.ADMIN_PORTAL_ACCESS));
			userDetails.setLocation(rs.getString(TableConstants.ADMIN_LOCATION));
			userDetails.setDeleteMarker(rs.getString(TableConstants.ADMIN_DELETE_MARKER));
			userDetails.setIsTncAccepted(rs.getString(TableConstants.ADMIN_IS_TNC_ACCEPTED));
			userDetails.setPwdUpdatedDate(rs.getDate(TableConstants.ADMIN_PWD_UPDATED_DATE));
			userDetails.setLastLoginTimestamp(rs.getTimestamp(TableConstants.ADMIN_LAST_LOGIN_ATTEMPT_TIMESTAMP));
			userDetails.setLocale(rs.getString(TableConstants.ADMIN_LOCALE));
			userDetails.setTimezone(rs.getString(TableConstants.ADMIN_TIMEZONE));
			userDetails.setCreatedBy(rs.getString(TableConstants.ADMIN_CREATED_BY));
			userDetails.setCreatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_CREATED_TIMESTAMP));
			userDetails.setUpdatedBy(rs.getString(TableConstants.ADMIN_UPDATED_BY));
			userDetails.setUpdatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_UPDATED_TIMESTAMP));

			if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.ADMIN_STATUS))) {
				userDetails.setStatus(rs.getString(TableConstants.ADMIN_STATUS));
			} else {
				userDetails.setStatus(UserStatus.I.name());
			}

			userDetails.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
			userDetails.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));
			userDetails.setInstitutionAddress(rs.getString(TableConstants.INSTITUTION_ADDRESS));
			userDetails.setPwdExpireInDays(rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRE_IN_DAYS));
			userDetails.setPwdExpiryNotificationStartInDays(
					rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS));

			userDetails.setPwdExpiryDate(rs.getDate(TableConstants.PWD_EXPIRY_DATE));
			userDetails
			.setPwdExpiryNotificationStartDate(rs.getDate(TableConstants.PWD_EXPIRY_NOTIFICATION_START_DATE));
			userDetails.setShowNotification(rs.getString(TableConstants.SHOW_NOTIFICATION));
			userDetails.setPwdExpired(rs.getString(TableConstants.PWD_EXPIRED));
			userDetails.setDaysLeftInPwdExpiration(rs.getInt(TableConstants.DAYS_LEFT_IN_PWD_EXPIRATION));

			userDetails.setPwdPolicy(extractPwdPolicyFromResultSet(rs));
			userDetails.setTnc(extractTermsNConditionsFromResultSet(rs));
			userDetails.setDataArchivalPolicy(extractDataArchivalPolicyFromResultSet(rs));
		}
	}

	/**
	 * This method will populate PasswordPolicy object from the result set and
	 * will return that object
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static PasswordPolicy extractPwdPolicyFromResultSet(final ResultSet rs) throws SQLException {
		PasswordPolicy pwdPolicy = new PasswordPolicy();

		if (rs.getInt(TableConstants.PWD_POLICY_ID) != 0) {
			pwdPolicy.setPwdPolicyId(rs.getInt(TableConstants.PWD_POLICY_ID));
			pwdPolicy.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
			pwdPolicy.setPwdRotationInDays(rs.getInt(TableConstants.PWD_POLICY_ROTATION_IN_DAYS));
			pwdPolicy.setPwdMinLength(rs.getInt(TableConstants.PWD_POLICY_MIN_LENGTH));
			pwdPolicy.setPwdMaxLength(rs.getInt(TableConstants.PWD_POLICY_MAX_LENGTH));
			pwdPolicy.setIsCapsAllowed(rs.getString(TableConstants.PWD_POLICY_IS_CAPS_ALLOWED));
			pwdPolicy.setIsLowerAllowed(rs.getString(TableConstants.PWD_POLICY_IS_LOWER_ALLOWED));
			pwdPolicy.setIsNumericAllowed(rs.getString(TableConstants.PWD_POLICY_IS_NUMERIC_ALLOWED));
			pwdPolicy.setIsSplCharAllowed(rs.getString(TableConstants.PWD_POLICY_IS_SPL_CHAR_ALLOWED));
			pwdPolicy.setRetryLoginAttemptsAllowed(rs.getInt(TableConstants.PWD_POLICY_RETRY_LOGIN_ATTEMPTS_ALLOWED));
			pwdPolicy.setPwdHistory(rs.getInt(TableConstants.PWD_POLICY_HISTORY));
			pwdPolicy.setProhibitedPasswords(rs.getString(TableConstants.PWD_POLICY_PROHIBITED_PASSWORDS));
			pwdPolicy.setPwdPolicyStatus(rs.getString(TableConstants.PWD_POLICY_STATUS));
			pwdPolicy.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_PWD_POLICY_DELETE_MARKER));
			pwdPolicy.setCreatedBy(rs.getString(TableConstants.INSTITUTION_PWD_POLICY_CREATED_BY));
			pwdPolicy.setCreatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_PWD_POLICY_CREATED_TIMESTAMP));
			pwdPolicy.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_PWD_POLICY_UPDATED_BY));
			pwdPolicy.setUpdatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_PWD_POLICY_UPDATED_TIMESTAMP));
		}

		return pwdPolicy;
	}

	/**
	 * This method will populate TermsAndConditions object from the result set
	 * and will return that object
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static TermsAndConditions extractTermsNConditionsFromResultSet(final ResultSet rs) throws SQLException {
		TermsAndConditions tnc = new TermsAndConditions();

		if (rs.getInt(TableConstants.TNC_ID) != 0) {
			tnc.setTncId(rs.getInt(TableConstants.TNC_ID));
			tnc.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
			tnc.setCreatedBy(rs.getString(TableConstants.INSTITUTION_TNC_CREATED_BY));
			tnc.setCreatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_TNC_CREATED_TIMESTAMP));
			tnc.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_TNC_UPDATED_BY));
			tnc.setUpdatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_TNC_UPDATED_TIMESTAMP));
			tnc.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_TNC_DELETE_MARKER));
			tnc.setTncStatus(rs.getString(TableConstants.TNC_STATUS));
			tnc.setTncText(rs.getString(TableConstants.TNC_TEXT));
		}

		return tnc;
	}

	/**
	 * This method will populate DataArchivalPolicy object from the result set
	 * and will return that object
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static DataArchivalPolicy extractDataArchivalPolicyFromResultSet(final ResultSet rs) throws SQLException {
		DataArchivalPolicy dataArchivalPolicy = new DataArchivalPolicy();

		if (rs.getInt(TableConstants.DATA_ARCHIVAL_POLICY_ID) != 0) {
			dataArchivalPolicy.setDataArchivalPolicyId(rs.getInt(TableConstants.DATA_ARCHIVAL_POLICY_ID));
			dataArchivalPolicy.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
			dataArchivalPolicy.setArchivalPeriodInMonths(rs.getInt(TableConstants.DATA_ARCHIVAL_PERIOD));
			dataArchivalPolicy.setAutoArchivalFrequency(rs.getString(TableConstants.DATA_ARCHIVAL_FREQUENCY));
			dataArchivalPolicy.setAutoLogOffTimeInMinutes(rs.getInt(TableConstants.DATA_ARCHIVAL_AUTO_LOG_OFF_TIME));
			dataArchivalPolicy.setDurationToStoreAuditLogsInMonths(
					rs.getInt(TableConstants.DATA_ARCHIVAL_DURATION_TO_STORE_AUDIT_LOGS));
			dataArchivalPolicy.setDataArchivalPolicyStatus(rs.getString(TableConstants.DATA_ARCHIVAL_POLICY_STATUS));
			dataArchivalPolicy
			.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_DELETE_MARKER));
			dataArchivalPolicy.setCreatedBy(rs.getString(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_CREATED_BY));
			dataArchivalPolicy.setCreatedTimestamp(
					rs.getTimestamp(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_CREATED_TIMESTAMP));
			dataArchivalPolicy.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_UPDATED_BY));
			dataArchivalPolicy.setUpdatedTimestamp(
					rs.getTimestamp(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_UPDATED_TIMESTAMP));
		}

		return dataArchivalPolicy;
	}
}