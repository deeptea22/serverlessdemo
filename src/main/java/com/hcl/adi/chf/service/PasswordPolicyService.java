package com.hcl.adi.chf.service;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.PwdPolicyDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PasswordPolicy;

/**
 * Service class for lambda functions related to pwd policy information
 *
 * @author DivyaAg
 */
public class PasswordPolicyService {
	private static final Logger LOGGER = LogManager.getLogger(PasswordPolicyService.class.getName());

	/**
	 * This method will invoke PwdPolicyDao to get password policy detail based
	 * on institution id
	 *
	 * @param institutionId
	 * @return
	 */
	public final PasswordPolicy getPwdPolicyInfoByInstitutionId(final int institutionId) {
		PasswordPolicy passwordPolicy = null;

		try {
			passwordPolicy = PwdPolicyDao.fetchPwdPolicyDetailBasedOnInstitutionID(institutionId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return passwordPolicy;
	}

	/**
	 * This method will invoke PwdPolicyDao to update password policy details
	 *
	 * @param passwordPolicy
	 * @return
	 */
	public final CustomResponse updatePasswordPolicy(final PasswordPolicy passwordPolicy) {
		CustomResponse response = new CustomResponse();

		try {
			if (passwordPolicy != null) {
				response = PwdPolicyDao.updatePwdPolicy(passwordPolicy);

				/**
				 * Now invoke method that will fetch admin details by emaild and
				 * will insert/send the record for corresponding audit_logs
				 * table based on admin type
				 */
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(passwordPolicy.getUpdatedBy(),
							Activity.PWD_POLICY_UPDATED.name());
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