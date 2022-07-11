package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will update the value of "retry_login_attempt_counter"
 * column in admins table for the specified emailId. Also, if user has reached
 * to the value of "retry_login_attempts_allowed" for corresponding institution
 * policy, then, it will block(deactivate) that user in to the admins table and
 * cognito pool. Moreover, in case of successful login, this lambda function
 * will update the value of "last_login_timestamp" column as well
 *
 * @author DivyaAg
 */
public final class UpdateAdminLoginAttempt implements RequestHandler<LoginActivity, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateAdminLoginAttempt.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final LoginActivity loginActivity, final Context context) {
		LOGGER.info(":::::::Request start to update login activity in admins table for the specified emailId:::::::");
		LOGGER.info("Input: " + loginActivity);

		AdminService adminServiceObj = new AdminService();
		response = adminServiceObj.updateLoginAttemptInAdminsAndBlockUserIfRequired(loginActivity);

		LOGGER.info(
				":::::::Request completed to update login activity in admins table for the specified emailId:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_ADMIN_LOGIN_ATTEMPT.name(), true);
	}
}