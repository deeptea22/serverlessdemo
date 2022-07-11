package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.CompliancePolicyService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update the compliance policy based on pk_id
 *
 * @author SandeepSingh
 */
public final class UpdateCompliancePolicy implements RequestHandler<CompliancePolicy, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateCompliancePolicy.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final CompliancePolicy compliancePolicy, final Context context) {
		LOGGER.info(":::::::Request start to update Compliance Policy based on pk_id:::::::");
		LOGGER.info("Input: " + compliancePolicy);
		if(compliancePolicy != null) {
			CompliancePolicyService service = new CompliancePolicyService();
			customResponse = service.updateCompliancePolicy(compliancePolicy);
		}

		LOGGER.info(":::::::Request completed to update Compliance Policy based on pk_id:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.UPDATE_COMPLIANCE_POLICY_BY_INSTITUTION_ID.name(), true);
	}
}