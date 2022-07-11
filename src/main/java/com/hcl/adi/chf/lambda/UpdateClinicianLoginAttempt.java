package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.service.ClinicianService;

/**
 * This lambda function will update the value of "retry_login_attempt_counter"
 * column in clinician table for the specified emailId. Also, if user has
 * reached to the value of "retry_login_attempts_allowed" for corresponding
 * institution policy, then, it will block(deactivate) that user in to the
 * clinician table and cognito pool. Moreover, in case of successful login, this
 * lambda function will update the value of "last_login_timestamp" column as
 * well
 *
 * @author DivyaAg
 */
public final class UpdateClinicianLoginAttempt implements RequestHandler<LoginActivity, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateClinicianLoginAttempt.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final LoginActivity loginActivity, final Context context) {
		LOGGER.info(
				":::::::Request start to update login activity in clinician table for the specified emailId:::::::");
		LOGGER.info("Input: " + loginActivity);

		ClinicianService clinicianServiceObj = new ClinicianService();
		response = clinicianServiceObj.updateLoginAttemptInClinicianAndBlockUserIfRequired(loginActivity);

		LOGGER.info(
				":::::::Request completed to update login activity in clinician table for the specified emailId:::::::");

		return response;
	}
}