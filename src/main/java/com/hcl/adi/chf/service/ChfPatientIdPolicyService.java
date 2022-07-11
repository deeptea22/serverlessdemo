package com.hcl.adi.chf.service;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.ChfPatientIdPolicyDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.CustomResponse;

/**
 * Service class for lambda functions related to chf patient id policy
 *
 * @author AyushRa
 */
public class ChfPatientIdPolicyService {
	private static final Logger LOGGER = LogManager.getLogger(ChfPatientIdPolicyService.class.getName());
	private static AuditLogService auditLogService = new AuditLogService();

	/**
	 * This method will invoke ChfPatientIdPolicyDao to persist chf patient id
	 * policy in DB
	 *
	 * @param chfPatientIdPolicy
	 * @return
	 */
	public final CustomResponse persistChfPatientIdPolicy(final ChfPatientIdPolicy chfPatientIdPolicy) {
		CustomResponse response = new CustomResponse();

		try {
			if (chfPatientIdPolicy != null && chfPatientIdPolicy.getPolicyList() != null
					&& !chfPatientIdPolicy.getPolicyList().isEmpty()) {
				response = ChfPatientIdPolicyDao.persistChfPatientIdPolicy(chfPatientIdPolicy);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.insertRecordIntoAuditLogs(chfPatientIdPolicy.getInstitutionId(),
							AdminType.IA.name(), Activity.CHF_PATIENT_ID_POLICY_CREATED.name(),
							chfPatientIdPolicy.getCreatedBy());
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
	 * This method will invoke ChfPatientIdPolicyDao to get chf patient id
	 * policy by institution id
	 *
	 * @param institutionId
	 * @return
	 */
	public final ChfPatientIdPolicy getChfPatientIdPolicyByInstitutionId(final Integer institutionId) {
		ChfPatientIdPolicy chfPatientIdPolicy = null;

		try {
			if (institutionId != null) {
				chfPatientIdPolicy = ChfPatientIdPolicyDao.getChfPatientIdPolicyByInstitutionId(institutionId);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

		}

		return chfPatientIdPolicy;
	}
}