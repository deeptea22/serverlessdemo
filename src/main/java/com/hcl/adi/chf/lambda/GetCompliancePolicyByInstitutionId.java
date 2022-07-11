package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.service.CompliancePolicyService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return compliance policy on the basis of institution Id 
 *
 * @author SandeepSingh
 */
public final class GetCompliancePolicyByInstitutionId implements RequestHandler<Map<String, Integer>, CompliancePolicy> {
	private static final Logger LOGGER = LogManager.getLogger(GetCompliancePolicyByInstitutionId.class.getName());
	private CompliancePolicy compliancePolicy = null;

	@Override
	public CompliancePolicy handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(
				":::::::Request start to get an compliance policy based on institution id:::::::");
		LOGGER.info("Input: " + input);

		Integer institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);
		if(institutionId != null) {
			CompliancePolicyService compliancePolicyService = new CompliancePolicyService();
			compliancePolicy = compliancePolicyService.getCompliancePolicy(institutionId);
		}

		LOGGER.info(":::::::Response to return from GetCompliancePolicyByInstitutionId:::::::" + compliancePolicy);
		LOGGER.info(":::::::Request completed to get Compliance Policy by institution id:::::::");

		if (compliancePolicy == null || compliancePolicy.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_COMPLIANCE_POLICY_BY_INSTITUTION_ID.name(), false);
		}

		return compliancePolicy;
	}
}