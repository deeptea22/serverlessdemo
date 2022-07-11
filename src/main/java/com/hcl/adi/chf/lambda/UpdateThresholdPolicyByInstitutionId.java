package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.service.ThresholdService;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will be used to update the threshold policy
 * of a institution by institution id
 *
 * @author DivyaAg
 */
public final class UpdateThresholdPolicyByInstitutionId implements RequestHandler<ThresholdPolicy, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateThresholdPolicyByInstitutionId.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final ThresholdPolicy thresholdPolicy, final Context context) {
		LOGGER.info(":::::::Request start to update threshold policy of a institution :::::::");
		LOGGER.info("Input: " + thresholdPolicy);

		if (thresholdPolicy != null) {
			ThresholdService thresholdServiceObj = new ThresholdService();
			response = thresholdServiceObj.updateThresholdPolicyByInstitutionId(thresholdPolicy);
		}

		LOGGER.info(":::::::Request completed to update threshold policy of a institution:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_THRESHOLD_POLICY_BY_INSTITUTION_ID.name(), true);
	}
}
