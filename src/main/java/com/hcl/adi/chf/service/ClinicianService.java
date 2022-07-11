package com.hcl.adi.chf.service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.ClinicianDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.JsonUtil;
import com.hcl.adi.chf.util.SQSUtil;

/**
 * Service class for lambda functions related to Clinician
 *
 * @author AyushRa
 */
public class ClinicianService {
	private static final Logger LOGGER = LogManager.getLogger(ClinicianService.class.getName());
	private static AuditLogService auditLogService = new AuditLogService();

	/**
	 * This method will invoke ClinicianDao to get a clinician detail based on
	 * email id
	 *
	 * @param emailId
	 * @return
	 */
	public final Clinician getClinicianByEmailId(final String emailId) {
		Clinician clinician = null;

		try {
			clinician = ClinicianDao.fetchClinicianDetailBasedOnEmailID(emailId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return clinician;
	}

	/**
	 * This method will invoke ClinicianDao to get the list of clinicians based
	 * on institution id
	 *
	 * @param institutionId
	 * @return
	 */
	public final List<Clinician> getListOfCliniciansByInstitutionId(final int institutionId) {
		List<Clinician> clinicianList = null;

		try {
			clinicianList = ClinicianDao.fetchClinicianListOnInstitutionID(institutionId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return clinicianList;
	}

	/**
	 * This method will invoke ClinicianDao to get a clinician and its pwd
	 * expiry info detail based on email id and institution specific
	 * parameters(pwdExpireInDays & pwdExpiryNotificationStartInDays)
	 *
	 * @param emailId
	 * @param pwdExpireInDays
	 * @param pwdExpiryNotificationStartInDays
	 * @return
	 */
	public final UserDetails getClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters(
			final String emailId, final int pwdExpireInDays, final int pwdExpiryNotificationStartInDays) {
		UserDetails userDetails = null;

		try {
			userDetails = ClinicianDao.fetchClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters(
					emailId, pwdExpireInDays, pwdExpiryNotificationStartInDays);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return userDetails;
	}

	/**
	 * This method will invoke ClinicianDao to get the list of those clinicians
	 * for whom password is about to expire and notification email need to be
	 * send to those users based on pwd_expiry_notification_start_in_days. And,
	 * it will send that list into SQS
	 * 
	 * Lambda function using this is not working right now....need to discuss
	 * approach for that
	 *
	 * @return
	 */
	public final CustomResponse getPwdExpiryInfoListOfEligibleCliniciansAndSendThatListToSQS() {
		CustomResponse response = null;
		List<PwdExpiryInfo> pwdExpiryInfoList = null;
		int counter = 0;

		try {
			pwdExpiryInfoList = ClinicianDao.fetchEligibleCliniciansForPwdExpiryNotification();

			LOGGER.info("Size of list that have eligible clinicians: " + pwdExpiryInfoList.size());
			LOGGER.info("List of eligible clinicians: " + pwdExpiryInfoList);

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
	 * This method will invoke ClinicianDao to update clinician detail
	 *
	 * @param clinician
	 * @return
	 */
	public final CustomResponse updateClinician(final Clinician clinician) {
		CustomResponse response = new CustomResponse();

		try {
			if (clinician != null && clinician.getAction() != null) {
				response = ClinicianDao.updateClinician(clinician, clinician.getAction());

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.insertUpdateClinicianMessageIntoAuditLogs(clinician);
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
	 * This method will invoke ClinicianDao to update the value of
	 * "pwd_updated_date" column for the specified email id
	 *
	 * @param emailId
	 * @return
	 */
	public final CustomResponse updatePwdUpdatedDateColumnInClinicianByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();

		try {
			if (emailId != null) {
				response = ClinicianDao.updatePwdUpdatedDateColumnInClinicianByEmailId(emailId);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(emailId, Activity.PWD_UPDATED.name());
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
	 * This method will invoke ClinicianDao to update the value of
	 * is_tnc_accepted column for the specified email id
	 *
	 * @param emailId
	 * @return
	 */
	public final CustomResponse updateIsTncAcceptedColumnInClinicianByEmailId(final String emailId) {
		CustomResponse response = new CustomResponse();

		try {
			if (emailId != null) {
				response = ClinicianDao.updateIsTncAcceptedColumnInClinicianByEmailId(emailId);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(emailId, Activity.TNC_ACCEPTED.name());
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
	 * This method will invoke ClinicianDao to update the value of
	 * "retry_login_attempt_counter" column in clinician table for the specified
	 * emailId. Also, if user has reached to the value of
	 * "retry_login_attempts_allowed" for corresponding institution policy,
	 * then, it will block(deactivate) that user in to the clinician table and
	 * cognito pool. Moreover, in case of successful login, this method will
	 * update the value of "last_login_timestamp" column as well
	 *
	 * @param loginActivity
	 * @return
	 */
	public final CustomResponse updateLoginAttemptInClinicianAndBlockUserIfRequired(final LoginActivity loginActivity) {
		CustomResponse response = new CustomResponse();
		int remainingLoginAttempts = -1;
		Clinician clinician = null;

		try {
			if (loginActivity != null && Constants.LOGIN_FAILURE.equalsIgnoreCase(loginActivity.getLoginAttempt())) {
				// First, fetch clinician details based on email Id
				clinician = ClinicianDao.fetchClinicianDetailBasedOnEmailID(loginActivity.getEmailId());

				if (clinician != null && clinician.getInstitutionId() != null) {
					/*
					 * Now, do a rest call for ADI module to get institution
					 * specific details from lambda function named as -
					 * (GetInstitutionDetailsByInstitutionId)
					 */
					
					
					InstitutionService institutionService = new InstitutionService();					
					Institution institution = institutionService.getInstitutionDetailsByInstitutionId(clinician.getInstitutionId());

					if (institution != null) {
						// Now, calculate remaining login attempts for the user
						remainingLoginAttempts = institution.getPwdPolicy().getRetryLoginAttemptsAllowed()
								- clinician.getRetryLoginAttemptCounter();
					} else {
						response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
						response.setDescription(
								Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.INSTITUTION_NOT_EXISTS);

						return response;
					}

					/**
					 * If, remaining attempts is 1, then, it means this is the
					 * last attempt and we need to block the user, else
					 * increment the login attempt counter in clinician table
					 */
					if (remainingLoginAttempts == 1) {
						response = ClinicianDao.blockClinicianInDBAndCognitoPool(loginActivity.getEmailId());

						// Now insert record into audit_logs table
						if (Status.OK.getStatusCode() == response.getStatusCode()) {
							auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(loginActivity.getEmailId(),
									Activity.USER_BLOCKED.name());
						}

					} else {
						response = ClinicianDao.updateRecordIntoClinicianForSpecifiedLoginActivity(
								loginActivity.getEmailId(), Constants.LOGIN_FAILURE);

						// Now insert record into audit_logs table
						if (Status.OK.getStatusCode() == response.getStatusCode()) {
							auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(loginActivity.getEmailId(),
									Activity.INVALID_LOGIN_ATTEMPT.name());
						}
					}
				}
			} else if (loginActivity != null
					&& Constants.LOGIN_SUCCESS.equalsIgnoreCase(loginActivity.getLoginAttempt())) {
				// In case of successful login attempt, reset the
				// "retry_login_attempt_counter" as 0
				response = ClinicianDao.updateRecordIntoClinicianForSpecifiedLoginActivity(loginActivity.getEmailId(),
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
	 * This method will invoke ClinicianDao to fetch clinicians who have been
	 * blocked(deactivate) because of unsuccessful attempts allowed for their
	 * institution and cooling period has expired for them. After fetching those
	 * clinicians, it will again invoke ClinicianDao to enable them into the
	 * database and cognito pool
	 *
	 * @return
	 */
	public final CustomResponse unblockCliniciansWhoseCoolingPeriodHasExpired() {
		int counter = 0;
		CustomResponse response = new CustomResponse();

		try {
			List<Clinician> clinicianList = ClinicianDao.fetchCliniciansWhoseCoolingPeriodHasExpired();

			for (Clinician clinician : clinicianList) {
				response = ClinicianDao.unblockClinicianByEmailId(clinician.getEmailId());

				if (response.getStatusCode() == Status.OK.getStatusCode()) {
					counter = counter + 1;
				}
			}

			LOGGER.info("Number of clinicians unblocked successfully: " + counter);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * This method will invoke ClinicianDao to create a clinician into the
	 * database
	 *
	 * @param clinician
	 * @param clinicianType
	 * @return
	 */
	public final CustomResponse createClinician(final Clinician clinician, final String clinicianType) {
		CustomResponse response = new CustomResponse();

		try {
			if (clinician != null) {
				response = ClinicianDao.insertRecordIntoClinician(clinician, clinicianType);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.insertRecordIntoAuditLogs(clinician.getInstitutionId(), AdminType.IA.name(),
							Activity.CLINICIAN_CREATED.name(), clinician.getCreatedBy());
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