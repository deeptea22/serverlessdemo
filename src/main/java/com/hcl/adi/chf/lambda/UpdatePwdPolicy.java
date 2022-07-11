package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.service.PasswordPolicyService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update password policy details
 *
 * @author AyushRa
 */
public final class UpdatePwdPolicy implements RequestHandler<PasswordPolicy, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePwdPolicy.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final PasswordPolicy passwordPolicy, final Context context) {
		LOGGER.info(":::::::Request start to update password policy details:::::::");
		LOGGER.info("Input: " + passwordPolicy);

		PasswordPolicyService passwordPolicyServiceObj = new PasswordPolicyService();

		response = passwordPolicyServiceObj.updatePasswordPolicy(passwordPolicy);

		LOGGER.info(":::::::Request completed to update password policy details:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_PWD_POLICY.name(), true);
	}
}