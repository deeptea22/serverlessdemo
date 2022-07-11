package com.hcl.adi.chf.service;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.hcl.adi.chf.dao.AdminDao;
import com.hcl.adi.chf.dao.AuditLogDao;
import com.hcl.adi.chf.dao.ClinicianDao;
import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.enums.ClinicianType;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.JsonUtil;
import com.hcl.adi.chf.util.SQSUtil;

/**
 * Service class for lambda functions related to audit logs
 *
 * @author DivyaAg
 */
public final class AuditLogService {
	private static final Logger LOGGER = LogManager.getLogger(AuditLogService.class.getName());

	/**
	 * This method will invoke AuditLogDao to get audit logs for super admin
	 * based on date filter
	 *
	 * @param startDate
	 * @param endDate
	 * @param accessedBy
	 * @param logType
	 * @return
	 */
	public final List<AuditLog> getAuditLogsForSuperAdminByDateFilter(final Date startDate, final Date endDate,
			final String accessedBy, final String logType) {
		List<AuditLog> auditLogList = null;

		try {
			auditLogList = AuditLogDao.fetchAuditLogsForSuperAdminByDateFilter(startDate, endDate, logType);

			if (auditLogList != null && StringUtils.isNotBlank(accessedBy) && auditLogList.size() > 0) {
				insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
						Activity.AUDIT_LOG_ACCESSED.name(), accessedBy);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return auditLogList;
	}

	/**
	 * This method will invoke AdminDao to fetch admin details by emaild and
	 * then either insert record into audit_logs table for SA or send the
	 * message to audit log queue for IA sothat record can be inserted into
	 * audit_logs table of institution side as per activity
	 *
	 * @param emailId
	 * @param activity
	 *            (TNC_ACCEPTED/ PWD_UPDATED/ DATA_ARCHIVAL_POLICY_UPDATED/
	 *            PWD_POLICY_UPDATED/ TNC_UPDATED/ USER_BLOCKED/
	 *            INVALID_LOGIN_ATTEMPT)
	 * @return
	 */
	public CustomResponse fetchAdminAndInsertRecordIntoAuditLogs(final String emailId, final String activity) {
		CustomResponse response = new CustomResponse();
		Admins admin = null;

		try {
			admin = AdminDao.fetchAdminDetailBasedOnEmailID(emailId);

			if (admin != null) {
				if (AdminType.SA.name().equalsIgnoreCase(admin.getType())) {
					response = insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(), activity,
							emailId);
				} else {
					response = insertRecordIntoAuditLogs(admin.getInstitutionId(), AdminType.IA.name(), activity, emailId);					
					//response = sendMessageToAuditLogQueue(admin.getInstitutionId(), AdminType.IA.name(), activity, emailId);
				}
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
	 * This method will create an audit log message to insert record into
	 * audit_logs table which exists on institution side and will send that
	 * message to SQS
	 *
	 * @param institutionId
	 * @param userType
	 * @param activity
	 * @param createdBy
	 * @return
	 */
	public CustomResponse sendMessageToAuditLogQueue(final Integer institutionId, final String userType,
			final String activity, final String createdBy) {
		AuditLog auditLog = new AuditLog();
		auditLog.setInstitutionId(institutionId);
		auditLog.setActivity(activity);
		auditLog.setUserType(userType);
		auditLog.setCreatedBy(createdBy);

		String message = JsonUtil.javaObjectToJsonString(auditLog);

		CustomResponse queueResponse = SQSUtil.sendMessageToSQS(
				Regions.fromName(Constants.REGION_NAME_FOR_INST_SIDE_QUEUE), Constants.QUEUE_NAME_FOR_AUDIT_LOGS,
				message);

		LOGGER.info("Message has been send to Audit log Queue for " + activity);

		return queueResponse;
	}

	/**
	 * This method will be used to create an audit log message based on action
	 * for specified admin(SA/IA). And, for SA this method will insert record
	 * into audit_logs table only and for IA this method will insert record into
	 * audit_logs table & will send the message to audit log queue also sothat
	 * record can be inserted into audit_logs table of institution side as well
	 *
	 * @param admin
	 */
	public void sendUpdateAdminMessageToAuditLogQueue(final Admins admin) {
		Actions action = Actions.fromStringAction(admin.getAction());

		if (AdminType.SA.name().equalsIgnoreCase(admin.getType())) {
			switch (action) {
			case DEACTIVATE:
				insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
						Activity.SUPER_ADMIN_DEACTIVATED.name(), admin.getCreatedBy());
				break;
			case ACTIVATE:
				insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
						Activity.SUPER_ADMIN_ACTIVATED.name(), admin.getCreatedBy());
				break;
			case DELETE:
				insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
						Activity.SUPER_ADMIN_DELETED.name(), admin.getCreatedBy());
				break;
			case EDIT:
				insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
						Activity.SUPER_ADMIN_UPDATED.name(), admin.getUpdatedBy());
				break;
			case PROFILEUPDATED:
				insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
						Activity.SUPER_ADMIN_PROFILE_UPDATED.name(), admin.getEmailId());
				break;
			default:
				break;
			}

		} else {
			// Need to decide institution id for below cases and how to identify
			// who is doing update for IA
			switch (action) {
			case DEACTIVATE:
				insertRecordIntoAuditLogs(admin.getInstitutionId(), admin.getType(),
						Activity.INSTITUTION_ADMIN_DEACTIVATED.name(), admin.getCreatedBy());

//				sendMessageToAuditLogQueue(admin.getInstitutionId(), admin.getType(),
//						Activity.INSTITUTION_ADMIN_DEACTIVATED.name(), admin.getCreatedBy());
				break;
			case ACTIVATE:
				insertRecordIntoAuditLogs(admin.getInstitutionId(), admin.getType(),
						Activity.INSTITUTION_ADMIN_ACTIVATED.name(), admin.getCreatedBy());

//				sendMessageToAuditLogQueue(admin.getInstitutionId(), admin.getType(),
//						Activity.INSTITUTION_ADMIN_ACTIVATED.name(), admin.getCreatedBy());
				break;
			case DELETE:
				insertRecordIntoAuditLogs(admin.getInstitutionId(), admin.getType(),
						Activity.INSTITUTION_ADMIN_DELETED.name(), admin.getCreatedBy());

//				sendMessageToAuditLogQueue(admin.getInstitutionId(), admin.getType(),
//						Activity.INSTITUTION_ADMIN_DELETED.name(), admin.getCreatedBy());
				break;
			case EDIT:
				insertRecordIntoAuditLogs(admin.getInstitutionId(), admin.getType(),
						Activity.INSTITUTION_ADMIN_UPDATED.name(), admin.getUpdatedBy());

//				sendMessageToAuditLogQueue(admin.getInstitutionId(), admin.getType(),
//						Activity.INSTITUTION_ADMIN_UPDATED.name(), admin.getUpdatedBy());
				break;
			case PROFILEUPDATED:
				insertRecordIntoAuditLogs(admin.getInstitutionId(), admin.getType(),
						Activity.INSTITUTION_ADMIN_PROFILE_UPDATED.name(), admin.getEmailId());

//				sendMessageToAuditLogQueue(admin.getInstitutionId(), admin.getType(),
//						Activity.INSTITUTION_ADMIN_PROFILE_UPDATED.name(), admin.getEmailId());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * This method will invoke AuditLogDao to insert record into audit_logs
	 * table
	 *
	 * @param auditLog
	 * @return
	 */
	public CustomResponse insertRecordIntoAuditLogs(final AuditLog auditLog) {
		CustomResponse response = new CustomResponse();

		try {
			if (auditLog != null) {
				//Activity should only log in cloud watch not in DB
				if (StringUtils.equalsIgnoreCase(auditLog.getLogToDB(),Constants.ACTIVITY_NO_ENTRY_DB)) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				} else {
					response = AuditLogDao.insertRecordIntoAuditLogs(auditLog);
				}
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
	 * This method will invoke AuditLogDao to insert record into audit_logs
	 * table
	 *
	 * @param institutionId
	 * @param userType
	 * @param activity
	 * @param createdBy
	 * @return
	 */
	public CustomResponse insertRecordIntoAuditLogs(final Integer institutionId, final String userType,
			final String activity, final String createdBy) {
		CustomResponse response = new CustomResponse();

		try {
			if (activity != null && userType != null) {
				AuditLog auditLog = new AuditLog();
				auditLog.setInstitutionId(institutionId);
				auditLog.setUserType(userType);
				auditLog.setActivity(activity);
				auditLog.setCreatedBy(createdBy);

				response = AuditLogDao.insertRecordIntoAuditLogs(auditLog);
				LOGGER.info("Audit Log message is :" + auditLog);

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
	 * This method will invoke ClinicianDao to fetch clinician details by
	 * emailId and then insert record into audit_logs table as per activity
	 *
	 * @param emailId
	 * @param activity
	 *            (TNC_ACCEPTED/ PWD_UPDATED/ USER_BLOCKED/
	 *            INVALID_LOGIN_ATTEMPT/ PATIENT_VITALS_CREATED/
	 *            PATIENT_VITALS_UPDATED/ PATIENT_THRESHOLDS_CREATED/
	 *            PATIENT_THRESHOLDS_UPDATED/ PATIENT_COMMENT_CREATED)
	 * @return
	 */
	public CustomResponse fetchClinicianAndInsertRecordIntoAuditLogs(final String emailId, final String activity) {
		CustomResponse response = new CustomResponse();
		Clinician clinician = null;

		try {
			clinician = ClinicianDao.fetchClinicianDetailBasedOnEmailID(emailId);

			if (clinician != null) {
				response = insertRecordIntoAuditLogs(clinician.getInstitutionId(), clinician.getType(), activity,
						emailId);
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
	 * This method will be used create an audit log message based on action for
	 * specified clinician
	 *
	 * @param clinician
	 */
	public void insertUpdateClinicianMessageIntoAuditLogs(Clinician clinician) {
		Actions action = Actions.fromStringAction(clinician.getAction());

		if (action != null) {
			switch (action) {
			case DEACTIVATE:
				insertRecordIntoAuditLogs(clinician.getInstitutionId(), AdminType.IA.name(),
						Activity.CLINICIAN_DEACTIVATED.name(), clinician.getCreatedBy());
				break;
			case ACTIVATE:
				insertRecordIntoAuditLogs(clinician.getInstitutionId(), AdminType.IA.name(),
						Activity.CLINICIAN_ACTIVATED.name(), clinician.getCreatedBy());
				break;
			case DELETE:
				insertRecordIntoAuditLogs(clinician.getInstitutionId(), AdminType.IA.name(),
						Activity.CLINICIAN_DELETED.name(), clinician.getCreatedBy());
				break;
			case EDIT:
				insertRecordIntoAuditLogs(clinician.getInstitutionId(), AdminType.IA.name(),
						Activity.CLINICIAN_UPDATED.name(), clinician.getCreatedBy());
				break;
			case PROFILEUPDATED:
				insertRecordIntoAuditLogs(clinician.getInstitutionId(), ClinicianType.CL.name(),
						Activity.CLINICIAN_PROFILE_UPDATED.name(), clinician.getEmailId());
				break;
			default:
				break;
			}
		}
	}
	/**
	 * This method will invoke AuditLogDao to get audit logs of specified
	 * institution based on date filter
	 *
	 * @param institutionId
	 * @param startDate
	 * @param endDate
	 * @param accessedBy
	 * @return
	 */
	public final List<AuditLog> getAuditLogsOfSpecifiedInstitutionByDateFilter(final int institutionId,
			final Date startDate, final Date endDate, final String accessedBy) {
		List<AuditLog> auditLogList = null;

		try {
			auditLogList = AuditLogDao.fetchAuditLogsOfSpecifiedInstitutionByDateFilter(institutionId, startDate,
					endDate);

			if (auditLogList != null && StringUtils.isNotBlank(accessedBy) && auditLogList.size() > 0) {
				insertRecordIntoAuditLogs(institutionId, AdminType.IA.name(), Activity.AUDIT_LOG_ACCESSED.name(),
						accessedBy);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return auditLogList;
	}


}