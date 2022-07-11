package com.hcl.adi.chf.service;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.ThresholdDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdPolicy;

public final class ThresholdService {
	private static final Logger LOGGER = LogManager.getLogger(ThresholdService.class.getName());
	private static AuditLogService auditLogService = new AuditLogService();


	/**
	 * This method will invoke ThresholdPolicyDao to get Threshold policy detail based
	 * on institution id
	 *
	 * @param institutionId
	 * @return
	 */
	public final ThresholdPolicy getThresholdPolicyByInstitutionId(final int institutionId) {
		ThresholdPolicy thresholdPolicy = null;

		try {
			thresholdPolicy = ThresholdDao.fetchThresholdPolicyDetailBasedOnInstitutionID(institutionId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return thresholdPolicy;
	}
	
	/**
	 * This method will invoke ThresholdDao to update the Threshold policy of a instituion by
	 * institutionId in DB
	 * 
	 * @param thresholdPolicy
	 * @return
	 */
	public final CustomResponse updateThresholdPolicyByInstitutionId(final ThresholdPolicy thresholdPolicy) {
		CustomResponse response = new CustomResponse();

		try {
			if (thresholdPolicy != null && thresholdPolicy.getThresholdPolicyId() != null) {
				response = ThresholdDao.updateThresholdPolicyByInstitutionId(thresholdPolicy);
				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.insertRecordIntoAuditLogs(thresholdPolicy.getInstitutionId(), AdminType.IA.name(), 
							Activity.THRESHOLD_POLICY_UPDATED.name(), thresholdPolicy.getUpdatedBy());
				}

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
