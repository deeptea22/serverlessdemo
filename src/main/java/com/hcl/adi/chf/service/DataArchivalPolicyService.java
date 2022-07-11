package com.hcl.adi.chf.service;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.CompliancePolicyDao;
import com.hcl.adi.chf.dao.DataArchivalPolicyDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DataArchivalPolicy;

/**
 * Service class for lambda functions related to data archival policy
 *
 * @author AyushRa
 */
public class DataArchivalPolicyService {
	private static final Logger LOGGER = LogManager.getLogger(DataArchivalPolicyService.class.getName());

	/**
	 * This method will invoke DataArchivalPolicyDao to update data archival
	 * policy details and also invoke CompliancePolicyDao to update compliance
	 * policy period
	 *
	 * @param dataArchivalPolicy
	 * @return
	 */
	public final CustomResponse updateDataArchivalPolicy(final DataArchivalPolicy dataArchivalPolicy) {
		CustomResponse response = new CustomResponse();

		try {
			if (dataArchivalPolicy != null) {
				response = DataArchivalPolicyDao.updateDataArchivalPolicy(dataArchivalPolicy);

				// Update Compliance Period
				if (dataArchivalPolicy.getCompliancePeriod() != null) {
					CompliancePolicy policy = new CompliancePolicy();
					policy.setPkId(dataArchivalPolicy.getPkId());
					policy.setInstitutionId(dataArchivalPolicy.getInstitutionId());
					policy.setUpdatedBy(dataArchivalPolicy.getUpdatedBy());
					policy.setCompliancePeriod(dataArchivalPolicy.getCompliancePeriod());
					response = CompliancePolicyDao.updateCompliancePolicy(policy);
				}

				/**
				 * Now invoke method that will fetch admin details by emaild and
				 * will insert/send the record for corresponding audit_logs
				 * table based on admin type
				 */
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(dataArchivalPolicy.getUpdatedBy(),
							Activity.DATA_ARCHIVAL_POLICY_UPDATED.name());
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