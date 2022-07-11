package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.service.MaxDetailService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return maximum possible details of an admin based
 * on email id
 *
 * @author AyushRa
 */
public final class GetMaximumDetailsOfAdminByEmailId implements RequestHandler<Map<String, String>, UserDetails> {
	private static final Logger LOGGER = LogManager.getLogger(GetMaximumDetailsOfAdminByEmailId.class.getName());
	private UserDetails userDetails = null;

	@Override
	public UserDetails handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get maximum possible details of an admin based on email id:::::::");
		LOGGER.info("Input: " + input);

		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);

		MaxDetailService maxDetailServiceObj = new MaxDetailService();
		userDetails = maxDetailServiceObj.getMaximumPossibleDetailsOfAdminByEmailId(emailId);

		LOGGER.info(":::::::Response to return from GetMaximumDetailsOfAdminByEmailId:::::::" + userDetails);
		LOGGER.info(":::::::Request completed to get maximum possible details of an admin based on email id:::::::");

		if (userDetails == null || userDetails.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_MAXIMUM_DETAILS_OF_ADMIN_BY_EMAIL_ID.name(), false);
		}

		return userDetails;
	}
}