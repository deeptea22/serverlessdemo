package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.service.ChfPatientIdPolicyService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to fetch chf patient id policy by
 * institution id
 *
 * @author AyushRa
 */
public final class GetCHFPatientIdPolicyByInstitutionId implements RequestHandler<Map<String, Integer>, ChfPatientIdPolicy> {
	private static final Logger LOGGER = LogManager.getLogger(GetCHFPatientIdPolicyByInstitutionId.class.getName());
	private ChfPatientIdPolicy chfPatientIdPolicy = null;

	@Override
	public ChfPatientIdPolicy handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to fetch chf patient id policy by institution id:::::::");
		LOGGER.info("Input: " + input);

		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);

		ChfPatientIdPolicyService chfPatientIdPolicyServiceObj = new ChfPatientIdPolicyService();
		chfPatientIdPolicy = chfPatientIdPolicyServiceObj.getChfPatientIdPolicyByInstitutionId(institutionId);

		if (chfPatientIdPolicy == null || chfPatientIdPolicy.getPolicyList() == null || chfPatientIdPolicy.getPolicyList().isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_CHF_PATIENT_POLICY_BY_INSTITUTION_ID.name(), false);
		}

		LOGGER.info(":::::::Request completed to fetch chf patient id policy by institution id:::::::");

		return chfPatientIdPolicy;
	}
}