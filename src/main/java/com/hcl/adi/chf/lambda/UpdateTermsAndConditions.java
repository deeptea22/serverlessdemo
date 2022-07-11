package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.service.TncService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update the terms n conditions detail
 *
 * @author AyushRa
 */
public final class UpdateTermsAndConditions implements RequestHandler<TermsAndConditions, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateAdmin.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final TermsAndConditions tnc, final Context context) {
		LOGGER.info(":::::::Request start to update terms n conditions detail:::::::");
		LOGGER.info("Input: " + tnc);

		TncService tncServiceObj = new TncService();
		response = tncServiceObj.updateTermsNConditions(tnc);

		LOGGER.info(":::::::Request completed to update terms n conditions detail:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_TNC.name(), true);
	}
}