package com.hcl.adi.chf.service;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.CompliancePolicyDao;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;

/**
 * Service class for lambda functions related to Compliance Policy
 *
 * @author SandeepSingh
 */
public class CompliancePolicyService {
	private static final Logger LOGGER = LogManager.getLogger(CompliancePolicyService.class.getName());

	/**
	 * This method will invoke CompliancePolicyDao to get CompliancePolicy for particular institution 
	 *
	 * @return
	 */
	public final CompliancePolicy getCompliancePolicy(final int institutionId) {
		CompliancePolicy policy = null;

		try {
			policy = CompliancePolicyDao.fetchCompliancePolicyBasedOnInstitutionID(institutionId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return policy;
	}


	/**
	 * This method will invoke CompliancePolicyDao to update an CompliancePolicy detail
	 *
	 * @param CompliancePolicy
	 * @return
	 */
	public final CustomResponse updateCompliancePolicy(final CompliancePolicy policy) {
		CustomResponse response = new CustomResponse();

		try {
			if (policy != null && policy.getCompliancePeriod() != null) {
				response = CompliancePolicyDao.updateCompliancePolicy(policy);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					//TODO
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