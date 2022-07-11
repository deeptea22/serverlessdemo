package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.service.PasswordPolicyService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to get password policy detail by
 * institution Id
 *
 * @author DivyaAg
 */
public final class GetPwdPolicyByInstitutionId implements RequestHandler<Map<String, Integer>, PasswordPolicy> {
	private static final Logger LOGGER = LogManager.getLogger(GetPwdPolicyByInstitutionId.class.getName());
	private PasswordPolicy passwordPolicy = null;

	@Override
	public PasswordPolicy handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get password policy detail based on institution id:::::::");
		LOGGER.info("Input: " + input);

		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);

		PasswordPolicyService passwordPolicyServiceObj = new PasswordPolicyService();
		passwordPolicy = passwordPolicyServiceObj.getPwdPolicyInfoByInstitutionId(institutionId);

		LOGGER.info(":::::::Request completed to get password policy detail based on institution id:::::::");

		if (passwordPolicy == null || passwordPolicy.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PWD_POLICY_BY_INSTITUTION_ID.name(), false);
		}

		return passwordPolicy;
	}
}