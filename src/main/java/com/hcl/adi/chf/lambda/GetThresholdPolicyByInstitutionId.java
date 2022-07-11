package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.service.ThresholdService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will be used to get threshold policy detail by
 * institution Id
 *
 * @author DivyaAg
 */
public final class GetThresholdPolicyByInstitutionId implements RequestHandler<Map<String, Integer>, ThresholdPolicy> {
	private static final Logger LOGGER = LogManager.getLogger(GetThresholdPolicyByInstitutionId.class.getName());
	private ThresholdPolicy thresholdPolicy = null;

	@Override
	public ThresholdPolicy handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get threshold Policy based on institution id:::::::");
		LOGGER.info("Input: " + input);

		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);

		ThresholdService thresholdServiceObj = new ThresholdService();
		thresholdPolicy = thresholdServiceObj.getThresholdPolicyByInstitutionId(institutionId);

		LOGGER.info(":::::::Request completed to get threshold Policy based on institution id:::::::");
		
		if (thresholdPolicy == null || thresholdPolicy.getThresholdPolicyId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_THRESHOLD_POLICY_BY_INSTITUTION_ID.name(), false);
		}

		return thresholdPolicy;
	}
}