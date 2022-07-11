package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.enums.UserStatus;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.MessageForClinician;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.util.CognitoUtil;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.JsonUtil;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.SQSUtil;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on admins table
 *
 * @author AyushRa
 */
public final class AdminDao {
	private static final Logger LOGGER = LogManager.getLogger(AdminDao.class.getName());

	private AdminDao() {
		// private constructor
	}

	/**
	 * Fetch admin list from admins table based on admin type
	 *
	 * @param adminType
	 * @return
	 */
	public static List<Admins> fetchAdminListOnAdminType(final String adminType) {
		List<Admins> adminList = new ArrayList<Admins>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_ADMINS_ON_TYPE"));
			pstmt.setString(1, adminType);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Admins admin = new Admins();
					admin.setAdminId(rs.getInt(TableConstants.ADMIN_ID));
					admin.setInstitutionId(rs.getInt(TableConstants.ADMIN_INSTITUTION_ID));
					admin.setEmailId(rs.getString(TableConstants.ADMIN_EMAIL_ID));
					admin.setFirstName(rs.getString(TableConstants.ADMIN_FIRST_NAME));
					admin.setLastName(rs.getString(TableConstants.ADMIN_LAST_NAME));
					admin.setEmployeeId(rs.getString(TableConstants.ADMIN_EMPLOYEE_ID));
					admin.setPoolId(rs.getString(TableConstants.ADMIN_POOL_ID));
					admin.setType(rs.getString(TableConstants.ADMIN_TYPE));
					admin.setIsDefault(rs.getString(TableConstants.ADMIN_IS_DEFAULT));
					admin.setPortalAccess(rs.getString(TableConstants.ADMIN_PORTAL_ACCESS));
					admin.setLocation(rs.getString(TableConstants.ADMIN_LOCATION));
					admin.setDeleteMarker(rs.getString(TableConstants.ADMIN_DELETE_MARKER));
					admin.setIsTncAccepted(rs.getString(TableConstants.ADMIN_IS_TNC_ACCEPTED));
					admin.setPwdUpdatedDate(rs.getDate(TableConstants.ADMIN_PWD_UPDATED_DATE));
					admin.setCreatedBy(rs.getString(TableConstants.ADMIN_CREATED_BY));
					admin.setCreatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_CREATED_TIMESTAMP));
					admin.setUpdatedBy(rs.getString(TableConstants.ADMIN_UPDATED_BY));
					admin.setUpdatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_UPDATED_TIMESTAMP));
					admin.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));

					if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.ADMIN_STATUS))) {
						admin.setStatus(rs.getString(TableConstants.ADMIN_STATUS));
						admin.setStatusFlag(true);
					} else {
						admin.setStatus(UserStatus.I.name());
						admin.setStatusFlag(false);
					}

					adminList.add(admin);
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return adminList;
	}

	/**
	 * Fetch an admin detail from admins table based on admin id
	 *
	 * @param adminId
	 * @return
	 */
	public static Admins fetchAdminDetailBasedOnAdminID(final int adminId) {
		Admins admin = new Admins();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_ADMINS_ON_ADMIN_ID"));
			pstmt.setInt(1, adminId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				admin.setAdminId(rs.getInt(TableConstants.ADMIN_ID));
				admin.setInstitutionId(rs.getInt(TableConstants.ADMIN_INSTITUTION_ID));
				admin.setEmailId(rs.getString(TableConstants.ADMIN_EMAIL_ID));
				admin.setFirstName(rs.getString(TableConstants.ADMIN_FIRST_NAME));
				admin.setLastName(rs.getString(TableConstants.ADMIN_LAST_NAME));
				admin.setEmployeeId(rs.getString(TableConstants.ADMIN_EMPLOYEE_ID));
				admin.setPoolId(rs.getString(TableConstants.ADMIN_POOL_ID));
				admin.setType(rs.getString(TableConstants.ADMIN_TYPE));
				admin.setIsDefault(rs.getString(TableConstants.ADMIN_IS_DEFAULT));
				admin.setPortalAccess(rs.getString(TableConstants.ADMIN_PORTAL_ACCESS));
				admin.setLocation(rs.getString(TableConstants.ADMIN_LOCATION));
				admin.setDeleteMarker(rs.getString(TableConstants.ADMIN_DELETE_MARKER));
				admin.setIsTncAccepted(rs.getString(TableConstants.ADMIN_IS_TNC_ACCEPTED));
				admin.setPwdUpdatedDate(rs.getDate(TableConstants.ADMIN_PWD_UPDATED_DATE));
				admin.setCreatedBy(rs.getString(TableConstants.ADMIN_CREATED_BY));
				admin.setCreatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_CREATED_TIMESTAMP));
				admin.setUpdatedBy(rs.getString(TableConstants.ADMIN_UPDATED_BY));
				admin.setUpdatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_UPDATED_TIMESTAMP));
				admin.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));

				if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.ADMIN_STATUS))) {
					admin.setStatus(rs.getString(TableConstants.ADMIN_STATUS));
					admin.setStatusFlag(true);
				} else {
					admin.setStatus(UserStatus.I.name());
					admin.setStatusFlag(false);
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return admin;
	}

	/**
	 * Fetch an admin detail from admins table based on email id
	 *
	 * @param emailId
	 * @return
	 */
	public static Admins fetchAdminDetailBasedOnEmailID(final String emailId) {
		Admins admin = new Admins();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_ADMINS_ON_EMAIL_ID"));
			pstmt.setString(1, emailId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				admin.setAdminId(rs.getInt(TableConstants.ADMIN_ID));
				admin.setInstitutionId(rs.getInt(TableConstants.ADMIN_INSTITUTION_ID));
				admin.setEmailId(rs.getString(TableConstants.ADMIN_EMAIL_ID));
				admin.setFirstName(rs.getString(TableConstants.ADMIN_FIRST_NAME));
				admin.setLastName(rs.getString(TableConstants.ADMIN_LAST_NAME));
				admin.setEmployeeId(rs.getString(TableConstants.ADMIN_EMPLOYEE_ID));
				admin.setPoolId(rs.getString(TableConstants.ADMIN_POOL_ID));
				admin.setType(rs.getString(TableConstants.ADMIN_TYPE));
				admin.setIsDefault(rs.getString(TableConstants.ADMIN_IS_DEFAULT));
				admin.setPortalAccess(rs.getString(TableConstants.ADMIN_PORTAL_ACCESS));
				admin.setLocation(rs.getString(TableConstants.ADMIN_LOCATION));
				admin.setDeleteMarker(rs.getString(TableConstants.ADMIN_DELETE_MARKER));
				admin.setIsTncAccepted(rs.getString(TableConstants.ADMIN_IS_TNC_ACCEPTED));
				admin.setPwdUpdatedDate(rs.getDate(TableConstants.ADMIN_PWD_UPDATED_DATE));
				admin.setCreatedBy(rs.getString(TableConstants.ADMIN_CREATED_BY));
				admin.setCreatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_CREATED_TIMESTAMP));
				admin.setUpdatedBy(rs.getString(TableConstants.ADMIN_UPDATED_BY));
				admin.setUpdatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_UPDATED_TIMESTAMP));
				admin.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));

				if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.ADMIN_STATUS))) {
					admin.setStatus(rs.getString(TableConstants.ADMIN_STATUS));
					admin.setStatusFlag(true);
				} else {
					admin.setStatus(UserStatus.I.name());
					admin.setStatusFlag(false);
				}
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return admin;
	}

	/**
	 * Fetch admin list from admins table based on institution id to
	 * activate/deactivate/delete those admins in cognito pool and to render
	 * those admins on IA dashboard page
	 *
	 * @param institutionId
	 * @return
	 * @throws Exception
	 */
	public static List<Admins> fetchAdminListOnInstitutionID(final int institutionId) throws Exception {
		List<Admins> adminList = new ArrayList<Admins>();

		Connection connection = DBConnection.getConnection();
		PreparedStatement pstmt = connection
				.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_ADMINS_ON_INSTITUTION_ID"));
		pstmt.setInt(1, institutionId);

		ResultSet rs = pstmt.executeQuery();

		if (rs != null) {
			while (rs.next()) {
				Admins admin = new Admins();
				admin.setAdminId(rs.getInt(TableConstants.ADMIN_ID));
				admin.setInstitutionId(rs.getInt(TableConstants.ADMIN_INSTITUTION_ID));
				admin.setEmailId(rs.getString(TableConstants.ADMIN_EMAIL_ID));
				admin.setFirstName(rs.getString(TableConstants.ADMIN_FIRST_NAME));
				admin.setLastName(rs.getString(TableConstants.ADMIN_LAST_NAME));
				admin.setEmployeeId(rs.getString(TableConstants.ADMIN_EMPLOYEE_ID));
				admin.setPoolId(rs.getString(TableConstants.ADMIN_POOL_ID));
				admin.setType(rs.getString(TableConstants.ADMIN_TYPE));
				admin.setIsDefault(rs.getString(TableConstants.ADMIN_IS_DEFAULT));
				admin.setPortalAccess(rs.getString(TableConstants.ADMIN_PORTAL_ACCESS));
				admin.setLocation(rs.getString(TableConstants.ADMIN_LOCATION));
				admin.setDeleteMarker(rs.getString(TableConstants.ADMIN_DELETE_MARKER));
				admin.setIsTncAccepted(rs.getString(TableConstants.ADMIN_IS_TNC_ACCEPTED));
				admin.setPwdUpdatedDate(rs.getDate(TableConstants.ADMIN_PWD_UPDATED_DATE));
				admin.setCreatedBy(rs.getString(TableConstants.ADMIN_CREATED_BY));
				admin.setCreatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_CREATED_TIMESTAMP));
				admin.setUpdatedBy(rs.getString(TableConstants.ADMIN_UPDATED_BY));
				admin.setUpdatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_UPDATED_TIMESTAMP));
				admin.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));

				if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.ADMIN_STATUS))) {
					admin.setStatus(rs.getString(TableConstants.ADMIN_STATUS));
					admin.setStatusFlag(true);
				} else {
					admin.setStatus(UserStatus.I.name());
					admin.setStatusFlag(false);
				}

				adminList.add(admin);
			}
		}

		return adminList;
	}

	/**
	 * Fetch eligible admins from admins table for pwd expiry notification
	 *
	 * @return
	 */
	public static List<PwdExpiryInfo> fetchEligibleAdminsForPwdExpiryNotification() {
		List<PwdExpiryInfo> pwdExpiryInfoList = new ArrayList<PwdExpiryInfo>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_PWD_EXPIRY_INFO_FROM_ADMINS_TO_SENT_EMAIL"));

			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					PwdExpiryInfo pwdExpiryInfo = new PwdExpiryInfo();

					pwdExpiryInfo.setEmailId(rs.getString(TableConstants.ADMIN_EMAIL_ID));
					pwdExpiryInfo.setFirstName(rs.getString(TableConstants.ADMIN_FIRST_NAME));
					pwdExpiryInfo.setLastName(rs.getString(TableConstants.ADMIN_LAST_NAME));
					pwdExpiryInfo.setPwdUpdatedDate(rs.getDate(TableConstants.ADMIN_PWD_UPDATED_DATE));
					pwdExpiryInfo.setPwdExpireInDays(rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRE_IN_DAYS));
					pwdExpiryInfo.setPwdExpiryNotificationStartInDays(
							rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS));
					pwdExpiryInfo.setPwdExpiryDate(rs.getDate(TableConstants.PWD_EXPIRY_DATE));
					pwdExpiryInfo.setPwdExpiryNotificationStartDate(
							rs.getDate(TableConstants.PWD_EXPIRY_NOTIFICATION_START_DATE));
					pwdExpiryInfo.setShowNotification(rs.getString(TableConstants.SHOW_NOTIFICATION));
					pwdExpiryInfo.setPwdExpired(rs.getString(TableConstants.PWD_EXPIRED));
					pwdExpiryInfo.setDaysLeftInPwdExpiration(rs.getInt(TableConstants.DAYS_LEFT_IN_PWD_EXPIRATION));

					pwdExpiryInfoList.add(pwdExpiryInfo);
				}
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return pwdExpiryInfoList;
	}

	/**
	 * This method will return remaining login attempts for specified emailId
	 *
	 * @param emailId
	 * @return
	 */
	public static int fetchRemainingLoginAttempts(final String emailId) {
		int remainingLoginAttempts = -1;

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils
					.getSQLQuery("SELECT_REMAINING_LOGIN_ATTEMPTS_FROM_ADMINS_AND_PWD_POLICY_TABLE_ON_EMAIL_ID"));
			pstmt.setString(1, emailId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				remainingLoginAttempts = rs.getInt(1);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return remainingLoginAttempts;
	}

	/**
	 * This method will fetch list of admins who have been blocked(deactivate)
	 * because of unsuccessful attempts allowed for their institution and
	 * cooling period has expired for them
	 *
	 * @return
	 */
	public static List<Admins> fetchAdminsWhoseCoolingPeriodHasExpired() {
		List<Admins> adminList = new ArrayList<Admins>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_ADMINS_LIST_TO_UNBLOCK"));
			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Admins admin = new Admins();
					admin.setAdminId(rs.getInt(TableConstants.ADMIN_ID));
					admin.setInstitutionId(rs.getInt(TableConstants.ADMIN_INSTITUTION_ID));
					admin.setEmailId(rs.getString(TableConstants.ADMIN_EMAIL_ID));
					admin.setCreatedBy(rs.getString(TableConstants.ADMIN_CREATED_BY));
					admin.setCreatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_CREATED_TIMESTAMP));
					admin.setUpdatedBy(rs.getString(TableConstants.ADMIN_UPDATED_BY));
					admin.setUpdatedTimestamp(rs.getTimestamp(TableConstants.ADMIN_UPDATED_TIMESTAMP));

					if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.ADMIN_STATUS))) {
						admin.setStatus(rs.getString(TableConstants.ADMIN_STATUS));
						admin.setStatusFlag(true);
					} else {
						admin.setStatus(UserStatus.I.name());
						admin.setStatusFlag(false);
					}

					adminList.add(admin);
				}

				LOGGER.info("Number of admins to unblock: " + adminList.size());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return adminList;
	}

	/**
	 * Update admin in admins table
	 *
	 * @param admin
	 * @param actionString
	 * @return
	 */
	public static CustomResponse updateAdmin(final Admins admin, final String actionString) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;
		String sqlQuery = null;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				Actions action = Actions.fromStringAction(actionString);

				if (action != null) {
					switch (action) {
					case ACTIVATE:
						sqlQuery = SQLUtils.getSQLQuery("ACTIVATE_ADMIN_ON_ADMIN_ID");
						break;
					case PROFILEUPDATED:
						sqlQuery = SQLUtils.getSQLQuery("UPDATE_ADMIN_PROFILE_ON_EMAIL_ID");
						break;
					default:
						sqlQuery = SQLUtils.getSQLQuery("UPDATE_ADMIN_ON_ID");
						break;
					}
				}

				PreparedStatement pstmt = connection.prepareStatement(sqlQuery);

				if (Constants.PROFILE_UPDATED.equalsIgnoreCase(actionString)) {
					pstmt.setString(1, admin.getFirstName());
					pstmt.setString(2, admin.getLastName());
					pstmt.setString(3, admin.getLocale());
					pstmt.setString(4, admin.getTimezone());
					pstmt.setString(5, admin.getEmailId());
					pstmt.setString(6, admin.getEmailId());

				} else {
					pstmt.setInt(1, admin.getInstitutionId());
					pstmt.setString(2, admin.getFirstName());
					pstmt.setString(3, admin.getLastName());
					pstmt.setString(4, admin.getEmployeeId());
					pstmt.setString(5, admin.getPoolId());
					pstmt.setString(6, admin.getStatus());
					pstmt.setString(7, admin.getType());
					pstmt.setString(8, admin.getIsDefault());
					pstmt.setString(9, admin.getPortalAccess());
					pstmt.setString(10, admin.getLocation());
					pstmt.setString(11, admin.getDeleteMarker());
					pstmt.setString(12, admin.getUpdatedBy());
					pstmt.setInt(13, admin.getAdminId());
				}

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					CognitoUtil.performActionOnCognitoPool(action, admin.getEmailId());

				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

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

	/**
	 * Update admin in admins table when someone perform activate, deactivate or
	 * delete operation on institution table. Also, invoke method of
	 * ClinicianDao to update clinician in clinician table(if required)
	 *
	 * @param updatedBy
	 * @param institutionId
	 * @param actionString
	 * @return
	 */
	public static CustomResponse updateAdminOnInstitutionId(final String updatedBy, final int institutionId,
			final String actionString) {
		CustomResponse response = new CustomResponse();
		String sqlQuery = null;
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				Actions action = Actions.fromStringAction(actionString);
				if (action != null) {
					switch (action) {
					case DEACTIVATE:
						sqlQuery = SQLUtils.getSQLQuery("DEACTIVATE_ADMIN_ON_INSTITUTION_ID");
						break;
					case ACTIVATE:
						sqlQuery = SQLUtils.getSQLQuery("ACTIVATE_ADMIN_ON_INSTITUTION_ID");
						break;
					case DELETE:
						sqlQuery = SQLUtils.getSQLQuery("DELETE_ADMIN_ON_INSTITUTION_ID");
						break;
					default:
						break;
					}
				}

				PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
				pstmt.setString(1, updatedBy);
				pstmt.setInt(2, institutionId);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					List<Admins> adminList = fetchAdminListOnInstitutionID(institutionId);
					for (Admins admin : adminList) {
						CognitoUtil.performActionOnCognitoPool(action, admin.getEmailId());
					}

					// Now, update clinicians as well based on institution id.
					CustomResponse clinicianResponse = ClinicianDao.updateClinicianOnInstitutionId(updatedBy,
							institutionId, actionString);

					if (clinicianResponse.getStatusCode() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
						response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
						response.setDescription(
								Status.INTERNAL_SERVER_ERROR.toString() + ": " + clinicianResponse.getDescription());
					}

				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
					connection.setAutoCommit(true);
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

	/**
	 * Update pwd_updated_date column in admins table based on emailId
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse updatePwdUpdatedDateColumnInAdminsByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_PWD_UPDATED_DATE_IN_ADMINS_BY_EMAIL_ID"));
				pstmt.setString(1, emailId);
				pstmt.setString(2, emailId);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

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

	/**
	 * Update is_tnc_accepted column to 'Y' in admins table based on emailId
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse updateIsTncAcceptedColumnInAdminsByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_IS_TNC_ACCEPTED_IN_ADMINS_BY_EMAIL_ID"));
				pstmt.setString(1, emailId);
				pstmt.setString(2, emailId);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

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

	/**
	 * Update is_tnc_accepted column to 'N' in admins table for all the users
	 *
	 * @param updatedBy
	 * @return
	 */
	public static CustomResponse updateIsTncAcceptedInAdmins(final String updatedBy) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_IS_TNC_ACCEPTED_IN_ADMINS"));
				pstmt.setString(1, updatedBy);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					// Now, update isTncAccepted flag for all the clinicians
					CustomResponse clinicianResponse = ClinicianDao.updateIsTncAcceptedInClinician(updatedBy);

					if (clinicianResponse.getStatusCode() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
						response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
						response.setDescription(
								Status.INTERNAL_SERVER_ERROR.toString() + ": " + clinicianResponse.getDescription());
					}
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
					connection.setAutoCommit(true);
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

	/**
	 * Update the value of "retry_login_attempt_counter" column based on login
	 * success or login failed for the specified emailId into the admins table.
	 * Moreover, in case of successful login, update the value of
	 * "last_login_timestamp" column as well
	 *
	 * @param emailId
	 * @param loginAttempt
	 * @return
	 */
	public static CustomResponse updateRecordIntoAdminsForSpecifiedLoginActivity(final String emailId,
			final String loginAttempt) {
		CustomResponse response = new CustomResponse();
		String sqlQuery = null;
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				if (Constants.LOGIN_FAILURE.equalsIgnoreCase(loginAttempt)) {
					sqlQuery = SQLUtils.getSQLQuery("UPDATE_ADMINS_FOR_FAILURE_LOGIN_ACTIVITY");
				} else {
					sqlQuery = SQLUtils.getSQLQuery("UPDATE_ADMINS_FOR_SUCCESS_LOGIN_ACTIVITY");
				}

				PreparedStatement pstmt = connection.prepareStatement(sqlQuery);

				pstmt.setString(1, emailId);
				pstmt.setString(2, emailId);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

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

	/**
	 * This method will block specified admin who has reached to the maximum
	 * login attempts allowed limit for his institution. Admin would be blocked
	 * in to the admins table and cognito pool
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse blockAdminInDBAndCognitoPool(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("BLOCK_ADMIN_BY_EMAIL_ID"));

				pstmt.setString(1, emailId);
				pstmt.setString(2, emailId);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					CognitoUtil.disableUser(emailId);

				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

				LOGGER.info(emailId
						+ " has been blocked in admins table and cognito pool after maximum login attempts allowed for the institution");

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

	/**
	 * Unblock specified admin in admins table and cognito pool after cooling
	 * period of 1 hour
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse unblockAdminByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UNBLOCK_ADMIN_BY_EMAIL_ID"));
				pstmt.setString(1, Constants.AUTO_UNBLOCK);
				pstmt.setString(2, emailId);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					CognitoUtil.enableUser(emailId);

				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

				LOGGER.info(emailId
						+ " has been unblocked in admins table and cognito pool after cooling period of 1 hour");

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

	/**
	 * Insert record into admins table and invoke CognitoUtil to add the user
	 * into pool
	 *
	 * @param admin
	 * @param adminType
	 * @param isDefault
	 * @return
	 */
	public static CustomResponse insertRecordIntoAdmins(final Admins admin, final String adminType,
			final String isDefault) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsInserted = 0;
		String adminGroupForCognito = null;

		if (isClinicianAvilable(admin.getEmailId()) != null) {
			LOGGER.info("::::::::::::::::Email Id reserve in Clinician::::::::::::::::::::");
			response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
			response.setDescription(Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
			return response;
		}

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_ADMINS"));

				if (AdminType.SA.name().equalsIgnoreCase(adminType)) {
					pstmt.setInt(1, 0);
					adminGroupForCognito = Constants.COGNITO_POOL_SUPER_ADMIN_GROUP;

				} else if (AdminType.IA.name().equalsIgnoreCase(adminType)) {
					pstmt.setInt(1, admin.getInstitutionId());
					adminGroupForCognito = Constants.COGNITO_POOL_INSTITUTION_ADMIN_GROUP;

				}

				pstmt.setString(2, admin.getEmailId());
				pstmt.setString(3, admin.getFirstName());
				pstmt.setString(4, admin.getLastName());
				pstmt.setString(5, admin.getEmployeeId());
				pstmt.setString(6, Constants.COGNITO_POOL_ID);
				pstmt.setString(7, adminType);
				pstmt.setString(8, isDefault);
				pstmt.setString(9, admin.getCreatedBy());
				pstmt.setString(10, admin.getCreatedBy());

				numberOfRecordsInserted = pstmt.executeUpdate();

				LOGGER.info("Number of records inserted into admins table: " + numberOfRecordsInserted);

				response.setStatusCode(Status.OK.getStatusCode());
				response.setDescription(Status.OK.toString());

				// Now, add the user into cognito pool
				CognitoUtil.addUser(admin.getEmailId(), admin.getEmailId(), adminGroupForCognito);

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
	 * This method will create an appropriate message with action for
	 * institution module and will send that message to SQS. So that we can
	 * update(Activate/Deactivate/Delete/is_tnc_accepted flag change) clinician
	 * as well based on institution id
	 *
	 * @param updatedBy
	 * @param institutionId
	 * @param action
	 * @return
	 */
	private static CustomResponse sendMessageToSQSForInstitutionModuleToPerformAppropriateAction(final String updatedBy,
			final Integer institutionId, final String action) {
		MessageForClinician messageForClinician = new MessageForClinician();
		messageForClinician.setUpdatedBy(updatedBy);
		messageForClinician.setInstitutionId(institutionId);
		messageForClinician.setAction(action);

		String message = JsonUtil.javaObjectToJsonString(messageForClinician);

		CustomResponse queueResponse = SQSUtil.sendMessageToSQS(
				Regions.fromName(Constants.REGION_NAME_FOR_INST_SIDE_QUEUE), Constants.QUEUE_NAME_FOR_UPDATE_CLINICIAN,
				message);

		return queueResponse;
	}

	/**
	 * This method will retuen clinician id based on email id
	 *
	 * @param emailId
	 */
	private static Integer isClinicianAvilable(final String emailId) {
		Integer id = null;
		try (Connection connection = DBConnection.getConnection();) {

			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("IS_CLINICIAN_AVILABLE"));
			pstmt.setString(1, emailId);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				if (rs != null) {
					while (rs.next()) {
						id = rs.getInt(TableConstants.CLINICIAN_ID);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return id;
	}
}