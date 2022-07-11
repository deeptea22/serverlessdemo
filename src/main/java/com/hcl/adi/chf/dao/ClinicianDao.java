package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.enums.ClinicianAccess;
import com.hcl.adi.chf.enums.UserStatus;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.util.CognitoUtil;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on clinician table
 *
 * @author AyushRa
 */
public final class ClinicianDao {
	private static final Logger LOGGER = LogManager.getLogger(ClinicianDao.class.getName());

	private ClinicianDao() {
		// private constructor
	}

	/**
	 * Fetch a clinician detail from clinician table based on email id
	 *
	 * @param emailId
	 * @return
	 */
	public static Clinician fetchClinicianDetailBasedOnEmailID(final String emailId) {
		Clinician clinician = new Clinician();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_CLINICIAN_ON_EMAIL_ID"));
			pstmt.setString(1, emailId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				clinician.setClinicianId(rs.getInt(TableConstants.CLINICIAN_ID));
				clinician.setInstitutionId(rs.getInt(TableConstants.CLINICIAN_INSTITUTION_ID));
				clinician.setEmailId(rs.getString(TableConstants.CLINICIAN_EMAIL_ID));
				clinician.setFirstName(rs.getString(TableConstants.CLINICIAN_FIRST_NAME));
				clinician.setLastName(rs.getString(TableConstants.CLINICIAN_LAST_NAME));
				clinician.setEmployeeId(rs.getString(TableConstants.CLINICIAN_EMPLOYEE_ID));
				clinician.setPoolId(rs.getString(TableConstants.CLINICIAN_POOL_ID));
				clinician.setType(rs.getString(TableConstants.CLINICIAN_TYPE));
				clinician.setPortalAccess(rs.getString(TableConstants.CLINICIAN_PORTAL_ACCESS));
				clinician.setLocation(rs.getString(TableConstants.CLINICIAN_LOCATION));
				clinician.setDeleteMarker(rs.getString(TableConstants.CLINICIAN_DELETE_MARKER));
				clinician.setIsTncAccepted(rs.getString(TableConstants.CLINICIAN_IS_TNC_ACCEPTED));
				clinician.setRetryLoginAttemptCounter(rs.getInt(TableConstants.CLINICIAN_RETRY_LOGIN_ATTEMPT_COUNTER));
				clinician.setLastLoginTimestamp(rs.getDate(TableConstants.CLNICIAN_LAST_LOGIN_TIMESTAMP));
				clinician.setPwdUpdatedDate(rs.getDate(TableConstants.CLINICIAN_PWD_UPDATED_DATE));
				clinician.setCreatedBy(rs.getString(TableConstants.CLINICIAN_CREATED_BY));
				clinician.setCreatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_CREATED_TIMESTAMP));
				clinician.setUpdatedBy(rs.getString(TableConstants.CLINICIAN_UPDATED_BY));
				clinician.setUpdatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_UPDATED_TIMESTAMP));

				if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.CLINICIAN_STATUS))) {
					clinician.setStatus(rs.getString(TableConstants.CLINICIAN_STATUS));
					clinician.setStatusFlag(true);
				} else {
					clinician.setStatus(UserStatus.I.name());
					clinician.setStatusFlag(false);
				}
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return clinician;
	}

	/**
	 * Fetch clinician list from clinician table based on institution id to
	 * activate/deactivate/delete those clinicians in cognito pool and to render
	 * those clinicians on IA dashboard page
	 *
	 * @param institutionId
	 * @return
	 * @throws Exception
	 */
	public static List<Clinician> fetchClinicianListOnInstitutionID(final int institutionId) throws Exception {
		List<Clinician> clinicianList = new ArrayList<Clinician>();

		Connection connection = DBConnection.getConnection();
		PreparedStatement pstmt = connection
				.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_CLINICIAN_ON_INSTITUTION_ID"));
		pstmt.setInt(1, institutionId);

		ResultSet rs = pstmt.executeQuery();

		if (rs != null) {
			while (rs.next()) {
				Clinician clinician = new Clinician();
				clinician.setClinicianId(rs.getInt(TableConstants.CLINICIAN_ID));
				clinician.setInstitutionId(rs.getInt(TableConstants.CLINICIAN_INSTITUTION_ID));
				clinician.setEmailId(rs.getString(TableConstants.CLINICIAN_EMAIL_ID));
				clinician.setFirstName(rs.getString(TableConstants.CLINICIAN_FIRST_NAME));
				clinician.setLastName(rs.getString(TableConstants.CLINICIAN_LAST_NAME));
				clinician.setEmployeeId(rs.getString(TableConstants.CLINICIAN_EMPLOYEE_ID));
				clinician.setPoolId(rs.getString(TableConstants.CLINICIAN_POOL_ID));
				clinician.setType(rs.getString(TableConstants.CLINICIAN_TYPE));
				clinician.setPortalAccess(rs.getString(TableConstants.CLINICIAN_PORTAL_ACCESS));
				clinician.setLocation(rs.getString(TableConstants.CLINICIAN_LOCATION));
				clinician.setDeleteMarker(rs.getString(TableConstants.CLINICIAN_DELETE_MARKER));
				clinician.setIsTncAccepted(rs.getString(TableConstants.CLINICIAN_IS_TNC_ACCEPTED));
				clinician.setPwdUpdatedDate(rs.getDate(TableConstants.CLINICIAN_PWD_UPDATED_DATE));
				clinician.setCreatedBy(rs.getString(TableConstants.CLINICIAN_CREATED_BY));
				clinician.setCreatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_CREATED_TIMESTAMP));
				clinician.setUpdatedBy(rs.getString(TableConstants.CLINICIAN_UPDATED_BY));
				clinician.setUpdatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_UPDATED_TIMESTAMP));

				if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.CLINICIAN_STATUS))) {
					clinician.setStatus(rs.getString(TableConstants.CLINICIAN_STATUS));
					clinician.setStatusFlag(true);
				} else {
					clinician.setStatus(UserStatus.I.name());
					clinician.setStatusFlag(false);
				}

				clinicianList.add(clinician);
			}
		}

		return clinicianList;
	}

	/**
	 * This method will fetch a clinician and its pwd expiry info detail based
	 * on email id and institution specific parameters(pwdExpireInDays &
	 * pwdExpiryNotificationStartInDays). Then, from the result set, it will
	 * populate a custom object named as UserDetails and will return that
	 * populated UserDetails object
	 *
	 * @param emailId
	 * @param pwdExpireInDays
	 * @param pwdExpiryNotificationStartInDays
	 * @return
	 */
	public static UserDetails fetchClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters(
			final String emailId, final int pwdExpireInDays, final int pwdExpiryNotificationStartInDays) {
		UserDetails userDetails = new UserDetails();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try (Connection connection = DBConnection.getConnection();) {
			pstmt = connection.prepareStatement(SQLUtils.getSQLQuery(
					"SELECT_CLINICIAN_AND_ITS_PWD_EXPIRY_INFO_BY_EMAIL_ID_AND_INSTITUTION_SPECIFIC_PARAMETERS"));
			pstmt.setInt(1, pwdExpireInDays);
			pstmt.setInt(2, pwdExpireInDays);
			pstmt.setInt(3, pwdExpireInDays);
			pstmt.setInt(4, pwdExpireInDays);
			pstmt.setInt(5, pwdExpiryNotificationStartInDays);
			pstmt.setString(6, emailId);

			rs = pstmt.executeQuery();

			populateUserDetails(rs, userDetails);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return userDetails;
	}

	/**
	 * Fetch eligible clinicians from clinician table for pwd expiry
	 * notification
	 * 
	 * Lambda function using this is not working right now....need to discuss
	 * approach for that
	 *
	 * @return
	 */
	public static List<PwdExpiryInfo> fetchEligibleCliniciansForPwdExpiryNotification() {
		List<PwdExpiryInfo> pwdExpiryInfoList = new ArrayList<PwdExpiryInfo>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_PWD_EXPIRY_INFO_FROM_CLINICIAN_TO_SENT_EMAIL"));

			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					PwdExpiryInfo pwdExpiryInfo = new PwdExpiryInfo();

					pwdExpiryInfo.setEmailId(rs.getString(TableConstants.CLINICIAN_EMAIL_ID));
					pwdExpiryInfo.setFirstName(rs.getString(TableConstants.CLINICIAN_FIRST_NAME));
					pwdExpiryInfo.setLastName(rs.getString(TableConstants.CLINICIAN_LAST_NAME));
					pwdExpiryInfo.setPwdUpdatedDate(rs.getDate(TableConstants.CLINICIAN_PWD_UPDATED_DATE));
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
	 * This method will fetch list of clinicians who have been
	 * blocked(deactivate) because of unsuccessful attempts allowed for their
	 * institution and cooling period has expired for them
	 *
	 * @return
	 */
	public static List<Clinician> fetchCliniciansWhoseCoolingPeriodHasExpired() {
		List<Clinician> clinicianList = new ArrayList<Clinician>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_CLINICIANS_LIST_TO_UNBLOCK"));
			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Clinician clinician = new Clinician();

					clinician.setClinicianId(rs.getInt(TableConstants.CLINICIAN_ID));
					clinician.setInstitutionId(rs.getInt(TableConstants.CLINICIAN_INSTITUTION_ID));
					clinician.setEmailId(rs.getString(TableConstants.CLINICIAN_EMAIL_ID));
					clinician.setPwdUpdatedDate(rs.getDate(TableConstants.CLINICIAN_PWD_UPDATED_DATE));
					clinician.setCreatedBy(rs.getString(TableConstants.CLINICIAN_CREATED_BY));
					clinician.setCreatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_CREATED_TIMESTAMP));
					clinician.setUpdatedBy(rs.getString(TableConstants.CLINICIAN_UPDATED_BY));
					clinician.setUpdatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_UPDATED_TIMESTAMP));

					if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.CLINICIAN_STATUS))) {
						clinician.setStatus(rs.getString(TableConstants.CLINICIAN_STATUS));
						clinician.setStatusFlag(true);
					} else {
						clinician.setStatus(UserStatus.I.name());
						clinician.setStatusFlag(false);
					}
					clinicianList.add(clinician);
				}

				LOGGER.info("Number of clinicians to unblock: " + clinicianList.size());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return clinicianList;
	}

	/**
	 * Update clinician in clinician table
	 *
	 * @param clinician
	 * @param actionString
	 * @return
	 */
	public static CustomResponse updateClinician(final Clinician clinician, final String actionString) {
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
						sqlQuery = SQLUtils.getSQLQuery("ACTIVATE_CLINICIAN_ON_CLINICIAN_ID");
						break;
					case PROFILEUPDATED:
						sqlQuery = SQLUtils.getSQLQuery("UPDATE_CLINICIAN_PROFILE_ON_EMAIL_ID");
						break;
					default:
						sqlQuery = SQLUtils.getSQLQuery("UPDATE_CLINICIAN_ON_ID");
						break;
					}
				}

				PreparedStatement pstmt = connection.prepareStatement(sqlQuery);

				if (Constants.PROFILE_UPDATED.equalsIgnoreCase(actionString)) {
					pstmt.setString(1, clinician.getFirstName());
					pstmt.setString(2, clinician.getLastName());
					pstmt.setString(3, clinician.getLocale());
					pstmt.setString(4, clinician.getTimezone());
					pstmt.setString(5, clinician.getEmailId());
					pstmt.setString(6, clinician.getEmailId());

				} else {
					pstmt.setInt(1, clinician.getInstitutionId());
					pstmt.setString(2, clinician.getFirstName());
					pstmt.setString(3, clinician.getLastName());
					pstmt.setString(4, clinician.getEmployeeId());
					pstmt.setString(5, clinician.getPoolId());
					pstmt.setString(6, clinician.getStatus());
					pstmt.setString(7, clinician.getType());
					pstmt.setString(8, clinician.getPortalAccess());
					pstmt.setString(9, clinician.getLocation());
					pstmt.setString(10, clinician.getDeleteMarker());
					pstmt.setString(11, clinician.getUpdatedBy());
					pstmt.setInt(12, clinician.getClinicianId());
				}

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					CognitoUtil.performActionOnCognitoPool(action, clinician.getEmailId());

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
	 * Update clinician in clinician table when someone perform activate,
	 * deactivate or delete operation on institution table
	 *
	 * @param updatedBy
	 * @param institutionId
	 * @param actionString
	 * @return
	 */
	public static CustomResponse updateClinicianOnInstitutionId(final String updatedBy, final int institutionId,
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
						sqlQuery = SQLUtils.getSQLQuery("DEACTIVATE_CLINICIAN_ON_INSTITUTION_ID");
						break;
					case ACTIVATE:
						sqlQuery = SQLUtils.getSQLQuery("ACTIVATE_CLINICIAN_ON_INSTITUTION_ID");
						break;
					case DELETE:
						sqlQuery = SQLUtils.getSQLQuery("DELETE_CLINICIAN_ON_INSTITUTION_ID");
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

					List<Clinician> clinicianList = fetchClinicianListOnInstitutionID(institutionId);
					for (Clinician clinician : clinicianList) {
						CognitoUtil.performActionOnCognitoPool(action, clinician.getEmailId());
					}

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
	 * Update pwd_updated_date column in clinician table based on emailId
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse updatePwdUpdatedDateColumnInClinicianByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_PWD_UPDATED_DATE_IN_CLINICIAN_BY_EMAIL_ID"));
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
	 * Update is_tnc_accepted column to 'Y' in clinician table based on emailId
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse updateIsTncAcceptedColumnInClinicianByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_IS_TNC_ACCEPTED_IN_CLINICIAN_BY_EMAIL_ID"));
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
	 * Update is_tnc_accepted column to 'N' in clinician table for all the users
	 *
	 * @param updatedBy
	 * @return
	 */
	public static CustomResponse updateIsTncAcceptedInClinician(final String updatedBy) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_IS_TNC_ACCEPTED_IN_CLINICIAN"));
				pstmt.setString(1, updatedBy);

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
	 * This method will block specified clinician who has reached to the maximum
	 * login attempts allowed limit for his institution. Clinician would be
	 * blocked in to the clinician table and cognito pool
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse blockClinicianInDBAndCognitoPool(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("BLOCK_CLINICIAN_BY_EMAIL_ID"));

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
						+ " has been blocked in clinician table and cognito pool after maximum login attempts allowed for the institution");

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
	 * success or login failed for the specified emailId into the clinician
	 * table. Moreover, in case of successful login, update the value of
	 * "last_login_timestamp" column as well
	 *
	 * @param emailId
	 * @param loginAttempt
	 * @return
	 */
	public static CustomResponse updateRecordIntoClinicianForSpecifiedLoginActivity(final String emailId,
			final String loginAttempt) {
		CustomResponse response = new CustomResponse();
		String sqlQuery = null;
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				if (Constants.LOGIN_FAILURE.equalsIgnoreCase(loginAttempt)) {
					sqlQuery = SQLUtils.getSQLQuery("UPDATE_CLINICIAN_FOR_FAILURE_LOGIN_ACTIVITY");
				} else {
					sqlQuery = SQLUtils.getSQLQuery("UPDATE_CLINICIAN_FOR_SUCCESS_LOGIN_ACTIVITY");
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
	 * Unblock specified clinician in clinician table and cognito pool after
	 * cooling period of 1 hour
	 *
	 * @param emailId
	 * @return
	 */
	public static CustomResponse unblockClinicianByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UNBLOCK_CLINICIAN_BY_EMAIL_ID"));
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
						+ " has been unblocked in clinician table and cognito pool after cooling period of 1 hour");

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
	 * Insert record into clinician table and invoke CognitoUtil to add the user
	 * into pool
	 *
	 * @param clinician
	 * @param clinicianType
	 * @return
	 */
	public static CustomResponse insertRecordIntoClinician(final Clinician clinician, final String clinicianType) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsInserted = 0;
		String clinicianGroupForCognito = null;

		if (isAdminAvilable(clinician.getEmailId()) != null) {
			LOGGER.info("::::::::::::::::Email Id reserve in Admins::::::::::::::::::::");
			response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
			response.setDescription(Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
			return response;
		}

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_CLINICIAN"));

				pstmt.setInt(1, clinician.getInstitutionId());
				pstmt.setString(2, clinician.getEmailId());
				pstmt.setString(3, clinician.getFirstName());
				pstmt.setString(4, clinician.getLastName());
				pstmt.setString(5, clinician.getEmployeeId());
				pstmt.setString(6, Constants.COGNITO_POOL_ID);
				pstmt.setString(7, clinicianType);
				pstmt.setString(8, clinician.getPortalAccess());
				pstmt.setString(9, clinician.getCreatedBy());
				pstmt.setString(10, clinician.getCreatedBy());

				numberOfRecordsInserted = pstmt.executeUpdate();

				LOGGER.info("Number of records inserted into clinician table: " + numberOfRecordsInserted);

				response.setStatusCode(Status.OK.getStatusCode());
				response.setDescription(Status.OK.toString());

				if (ClinicianAccess.WEB.name().equalsIgnoreCase(clinician.getPortalAccess())) {
					clinicianGroupForCognito = Constants.COGNITO_POOL_WEB_CLINICIAN_GROUP;
				} else if (ClinicianAccess.MOBILE.name().equalsIgnoreCase(clinician.getPortalAccess())) {
					clinicianGroupForCognito = Constants.COGNITO_POOL_MOBILE_CLINICIAN_GROUP;
				} else {
					clinicianGroupForCognito = Constants.COGNITO_POOL_WEB_MOBILE_CLINICIAN_GROUP;
				}

				// Now, add the user into cognito pool
				CognitoUtil.addUser(clinician.getEmailId(), clinician.getEmailId(), clinicianGroupForCognito);

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
	 * This method will populate the UserDetails object from fetched data
	 *
	 * @param rs
	 * @param userDetails
	 * @throws SQLException
	 */
	private static void populateUserDetails(final ResultSet rs, final UserDetails userDetails) throws SQLException {
		if (rs != null && rs.next()) {
			userDetails.setId(rs.getInt(TableConstants.CLINICIAN_ID));
			userDetails.setInstitutionId(rs.getInt(TableConstants.CLINICIAN_INSTITUTION_ID));
			userDetails.setEmailId(rs.getString(TableConstants.CLINICIAN_EMAIL_ID));
			userDetails.setFirstName(rs.getString(TableConstants.CLINICIAN_FIRST_NAME));
			userDetails.setLastName(rs.getString(TableConstants.CLINICIAN_LAST_NAME));
			userDetails.setEmployeeId(rs.getString(TableConstants.CLINICIAN_EMPLOYEE_ID));
			userDetails.setPoolId(rs.getString(TableConstants.CLINICIAN_POOL_ID));
			userDetails.setType(rs.getString(TableConstants.CLINICIAN_TYPE));
			userDetails.setPortalAccess(rs.getString(TableConstants.CLINICIAN_PORTAL_ACCESS));
			userDetails.setLocation(rs.getString(TableConstants.CLINICIAN_LOCATION));
			userDetails.setDeleteMarker(rs.getString(TableConstants.CLINICIAN_DELETE_MARKER));
			userDetails.setIsTncAccepted(rs.getString(TableConstants.CLINICIAN_IS_TNC_ACCEPTED));
			userDetails.setPwdUpdatedDate(rs.getDate(TableConstants.CLINICIAN_PWD_UPDATED_DATE));
			userDetails.setLocale(rs.getString(TableConstants.CLINICIAN_LOCALE));
			userDetails.setTimezone(rs.getString(TableConstants.CLINICIAN_TIMEZONE));
			userDetails.setCreatedBy(rs.getString(TableConstants.CLINICIAN_CREATED_BY));
			userDetails.setCreatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_CREATED_TIMESTAMP));
			userDetails.setUpdatedBy(rs.getString(TableConstants.CLINICIAN_UPDATED_BY));
			userDetails.setUpdatedTimestamp(rs.getTimestamp(TableConstants.CLINICIAN_UPDATED_TIMESTAMP));

			if (UserStatus.A.name().equalsIgnoreCase(rs.getString(TableConstants.CLINICIAN_STATUS))) {
				userDetails.setStatus(rs.getString(TableConstants.CLINICIAN_STATUS));
			} else {
				userDetails.setStatus(UserStatus.I.name());
			}

			userDetails.setPwdExpiryDate(rs.getDate(TableConstants.PWD_EXPIRY_DATE));
			userDetails
					.setPwdExpiryNotificationStartDate(rs.getDate(TableConstants.PWD_EXPIRY_NOTIFICATION_START_DATE));
			userDetails.setShowNotification(rs.getString(TableConstants.SHOW_NOTIFICATION));
			userDetails.setPwdExpired(rs.getString(TableConstants.PWD_EXPIRED));
			userDetails.setDaysLeftInPwdExpiration(rs.getInt(TableConstants.DAYS_LEFT_IN_PWD_EXPIRATION));
		}
	}

	/**
	 * This method will retuen admin id based on email id
	 *
	 * @param emailId
	 */
	public static Integer isAdminAvilable(final String emailId) {
		Integer id = null;

		try (Connection connection = DBConnection.getConnection();) {

			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("IS_ADMIN_AVILABLE"));
			pstmt.setString(1, emailId);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				if (rs != null) {
					while (rs.next()) {
						id = rs.getInt(TableConstants.ADMIN_ID);
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