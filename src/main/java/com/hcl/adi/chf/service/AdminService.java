package com.hcl.adi.chf.service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.AdminDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.JsonUtil;
import com.hcl.adi.chf.util.SQSUtil;

/**
 * Service class for lambda functions related to Admins
 *
 * @author AyushRa
 */
public class AdminService {
	private static final Logger LOGGER = LogManager.getLogger(AdminService.class.getName());

	/**
	 * This method will invoke AdminDao to get admin list based on admin type
	 *
	 * @param adminType
	 * @return
	 */
	public final List<Admins> getAdmins(final String adminType) {
		List<Admins> adminList = null;

		try {
			adminList = AdminDao.fetchAdminListOnAdminType(adminType);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return adminList;
	}

	/**
	 * This method will invoke AdminDao to get an admin detail based on admin id
	 *
	 * @param adminId
	 * @return
	 */
	public final Admins getAdminByAdminId(final int adminId) {
		Admins admin = null;

		try {
			admin = AdminDao.fetchAdminDetailBasedOnAdminID(adminId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return admin;
	}

	/**
	 * This method will invoke AdminDao to get an admin detail based on email id
	 *
	 * @param emailId
	 * @return
	 */
	public final Admins getAdminByEmailId(final String emailId) {
		Admins admin = null;

		try {
			admin = AdminDao.fetchAdminDetailBasedOnEmailID(emailId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return admin;
	}

	/**
	 * This method will invoke AdminDao to get the list of admins based on
	 * institution id
	 *
	 * @param institutionId
	 * @return
	 */
	public final List<Admins> getListOfAdminsByInstitutionId(final int institutionId) {
		List<Admins> adminList = null;

		try {
			adminList = AdminDao.fetchAdminListOnInstitutionID(institutionId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return adminList;
	}

	/**
	 * This method will invoke AdminDao to get the list of those admins for whom
	 * password is about to expire and notification email need to be send to
	 * those users based on pwd_expiry_notification_start_in_days. And, it will
	 * send that list into SQS
	 *
	 * @return
	 */
	public final CustomResponse getPwdExpiryInfoListOfEligibleAdminsAndSendThatListToSQS() {
		CustomResponse response = null;
		List<PwdExpiryInfo> pwdExpiryInfoList = null;
		int counter = 0;

		try {
			pwdExpiryInfoList = AdminDao.fetchEligibleAdminsForPwdExpiryNotification();

			LOGGER.info("Size of list that have eligible admins: " + pwdExpiryInfoList.size());
			LOGGER.info("List of eligible admins: " + pwdExpiryInfoList);

			for (PwdExpiryInfo pwdExpiryInfo : pwdExpiryInfoList) {
				String message = JsonUtil.javaObjectToJsonString(pwdExpiryInfo);

				response = SQSUtil.sendMessageToSQS(Constants.CURRENT_REGION, Constants.QUEUE_NAME_FOR_PWD_EXPIRY_INFO,
						message);

				if (response.getStatusCode() == Status.OK.getStatusCode()) {
					counter = counter + 1;
				}
			}

			LOGGER.info("Total message sent to SQS: " + counter);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return response;
	}

	/**
	 * This method will invoke AdminDao to update an admin detail
	 *
	 * @param admin
	 * @return
	 */
	public final CustomResponse updateAdmin(final Admins admin) {
		CustomResponse response = new CustomResponse();

		try {
			if (admin != null && admin.getAction() != null) {
				response = AdminDao.updateAdmin(admin, admin.getAction());

				/**
				 * Now invoke method that will insert record into audit_logs
				 * table only for SA and will insert record into audit_logs
				 * table along with sending the message to audit log queue also
				 * for IA sothat record can be inserted into audit_logs table of
				 * institution side as well
				 */
				if (response.getStatusCode() == Status.OK.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.sendUpdateAdminMessageToAuditLogQueue(admin);
				}

			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			response.setDescription(Status.BAD_REQUEST.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * This method will invoke AdminDao to update the value of
	 * "pwd_updated_date" column for the specified email id
	 *
	 * @param emailId
	 * @return
	 */
	public final CustomResponse updatePwdUpdatedDateColumnInAdminsByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();

		if (emailId != null) {
			response = AdminDao.updatePwdUpdatedDateColumnInAdminsByEmailId(emailId);

			/**
			 * Now invoke method that will fetch admin details by emaild and
			 * will insert/send the record for corresponding audit_logs table
			 * based on admin type
			 */
			if (Status.OK.getStatusCode() == response.getStatusCode()) {
				AuditLogService auditLogService = new AuditLogService();
				auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(emailId, Activity.PWD_UPDATED.name());
			}
		} else {
			response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			response.setDescription(Status.BAD_REQUEST.toString());
		}

		return response;
	}

	/**
	 * This method will invoke AdminDao to update the value of "is_tnc_accepted"
	 * column for the specified email id
	 *
	 * @param emailId
	 * @return
	 */
	public final CustomResponse updateIsTncAcceptedColumnInAdminsByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();

		try {
			if (emailId != null) {
				response = AdminDao.updateIsTncAcceptedColumnInAdminsByEmailId(emailId);

				/**
				 * Now invoke method that will fetch admin details by emaild and
				 * will insert/send the record for corresponding audit_logs
				 * table based on admin type
				 */
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(emailId, Activity.TNC_ACCEPTED.name());
				}

			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return response;
	}

	/**
	 * This method will invoke AdminDao to update the value of
	 * "retry_login_attempt_counter" column in admins table for the specified
	 * emailId. Also, if user has reached to the value of
	 * "retry_login_attempts_allowed" for corresponding institution policy,
	 * then, it will block(deactivate) that user in to the admins table and
	 * cognito pool. Moreover, in case of successful login, this method will
	 * update the value of "last_login_timestamp" column as well
	 *
	 * @param loginActivity
	 * @return
	 */
	public final CustomResponse updateLoginAttemptInAdminsAndBlockUserIfRequired(final LoginActivity loginActivity) {
		CustomResponse response = new CustomResponse();
		AuditLogService auditLogService = new AuditLogService();

		try {
			if (loginActivity != null && Constants.LOGIN_FAILURE.equalsIgnoreCase(loginActivity.getLoginAttempt())) {

				// Fetch remaining login attempts for the user
				int remainingLoginAttempts = AdminDao.fetchRemainingLoginAttempts(loginActivity.getEmailId());

				/**
				 * If, remaining attempts is 1, then, it means this is the last
				 * attempt and we need to block the user, else increment the
				 * login attempt counter in admins table
				 */
				if (remainingLoginAttempts == 1) {
					response = AdminDao.blockAdminInDBAndCognitoPool(loginActivity.getEmailId());

					/**
					 * Now invoke method that will fetch admin details by emaild
					 * and will insert/send the record for corresponding
					 * audit_logs table based on admin type
					 */
					if (Status.OK.getStatusCode() == response.getStatusCode()) {
						auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(loginActivity.getEmailId(),
								Activity.USER_BLOCKED.name());
					}

				} else {
					response = AdminDao.updateRecordIntoAdminsForSpecifiedLoginActivity(loginActivity.getEmailId(),
							Constants.LOGIN_FAILURE);

					/**
					 * Now invoke method that will fetch admin details by emaild
					 * and will insert/send the record for corresponding
					 * audit_logs table based on admin type
					 */
					if (Status.OK.getStatusCode() == response.getStatusCode()) {
						auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(loginActivity.getEmailId(),
								Activity.INVALID_LOGIN_ATTEMPT.name());
					}
				}
			} else if (loginActivity != null
					&& Constants.LOGIN_SUCCESS.equalsIgnoreCase(loginActivity.getLoginAttempt())) {
				// In case of successful login attempt, reset the
				// "retry_login_attempt_counter" as 0
				response = AdminDao.updateRecordIntoAdminsForSpecifiedLoginActivity(loginActivity.getEmailId(),
						Constants.LOGIN_SUCCESS);
			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * This method will invoke AdminDao to fetch admins who have been
	 * blocked(deactivate) because of unsuccessful attempts allowed for their
	 * institution and cooling period has expired for them. After fetching those
	 * admins, it will again invoke AdminDao to enable them into the database
	 * and cognito pool
	 *
	 * @return
	 */
	public final CustomResponse unblockAdminsWhoseCoolingPeriodHasExpired() {
		int counter = 0;
		CustomResponse response = new CustomResponse();

		try {
			List<Admins> adminList = AdminDao.fetchAdminsWhoseCoolingPeriodHasExpired();

			for (Admins admin : adminList) {
				response = AdminDao.unblockAdminByEmailId(admin.getEmailId());

				if (response.getStatusCode() == Status.OK.getStatusCode()) {
					counter = counter + 1;
				}
			}

			LOGGER.info("Number of admins unblocked successfully: " + counter);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * This method will invoke AdminDao to create an admin
	 * (SuperAdmin/InstitutionAdmin) into the database
	 *
	 * @param admin
	 * @param adminType
	 * @param isDefault
	 * @return
	 */
	public final CustomResponse createAdmin(final Admins admin, final String adminType, final String isDefault) {
		CustomResponse response = new CustomResponse();

		try {
			if (admin != null) {
				response = AdminDao.insertRecordIntoAdmins(admin, adminType, isDefault);

				/**
				 * Now for SA insert record into audit_logs table only and for
				 * IA insert record into audit_logs table along with sending the
				 * message to audit log queue also sothat record can be inserted
				 * into audit_logs table of institution side as well
				 */
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();

					if (AdminType.SA.name().equalsIgnoreCase(adminType)) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
								Activity.SUPER_ADMIN_CREATED.name(), admin.getCreatedBy());
					} else {
						if ("Y".equalsIgnoreCase(isDefault)) {
							auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
									Activity.INSTITUTION_ADMIN_CREATED.name(), admin.getCreatedBy());

							// Need to decide institution id for this case
							auditLogService.insertRecordIntoAuditLogs(admin.getInstitutionId(), AdminType.SA.name(), Activity.INSTITUTION_ADMIN_CREATED.name(), admin.getCreatedBy());
						} else {
							auditLogService.insertRecordIntoAuditLogs(admin.getInstitutionId(), AdminType.IA.name(),
									Activity.INSTITUTION_ADMIN_CREATED.name(), admin.getCreatedBy());

							//auditLogService.sendMessageToAuditLogQueue(admin.getInstitutionId(), AdminType.IA.name(), Activity.INSTITUTION_ADMIN_CREATED.name(), admin.getCreatedBy());
						}
					}
				}
			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			response.setDescription(Status.BAD_REQUEST.toString() + ": " + e.getMessage());
		}

		return response;
	}
}