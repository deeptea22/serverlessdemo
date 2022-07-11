package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DataArchivalPolicy;
import com.hcl.adi.chf.service.DataArchivalPolicyService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update data archival policy details and Compliance policy period
 *
 * @author AyushRa
 */
public final class UpdateDataArchivalPolicy implements RequestHandler<DataArchivalPolicy, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateDataArchivalPolicy.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final DataArchivalPolicy dataArchivalPolicy, final Context context) {
		LOGGER.info(":::::::Request start to update data archival policy details:::::::");
		LOGGER.info("Input: " + dataArchivalPolicy);

		DataArchivalPolicyService dataArchivalPolicyServiceObj = new DataArchivalPolicyService();

		response = dataArchivalPolicyServiceObj.updateDataArchivalPolicy(dataArchivalPolicy);

		LOGGER.info(":::::::Request completed to update data archival policy details:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_DATA_ARCHIVAL_POLICY.name(), true);
	}
}