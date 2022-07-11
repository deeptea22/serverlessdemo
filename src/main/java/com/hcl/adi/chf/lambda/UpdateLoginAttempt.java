package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.enums.ClinicianType;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.LoginActivity;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will act as an orcherastrator. And, based on user
 * type(clinician/admin) it will execute the corresponding lambda to update the
 * value of "retry_login_attempt_counter" column in admins/clinician table for
 * the specified emailId. Also, the invoking lambda function will check whether
 * user has reached to the value of "retry_login_attempts_allowed" for
 * corresponding institution policy or not to block(deactivate) that user in to
 * the corresponding database table and cognito pool. Moreover, in case of
 * successful login, corresponding lambda function will update the value of
 * "last_login_timestamp" column as well
 *
 * @author DivyaAg
 */
public final class UpdateLoginAttempt implements RequestHandler<LoginActivity, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateLoginAttempt.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final LoginActivity loginActivity, final Context context) {
		LOGGER.info(":::::::UpdateLoginAttempt orcherastrator started:::::::");
		LOGGER.info("Input: " + loginActivity);

		if (ClinicianType.CL.name().equalsIgnoreCase(loginActivity.getType())) {
			// Invoke lambda to update login attempt in clinician table
			ClinicianService clinicianService = new ClinicianService();			
			response = clinicianService.updateLoginAttemptInClinicianAndBlockUserIfRequired(loginActivity);
		} else {
			// Send request to adi side to update login attempt in admins table
			AdminService adminService = new AdminService();
			response = adminService.updateLoginAttemptInAdminsAndBlockUserIfRequired(loginActivity);;
		}

		LOGGER.info(":::::::UpdateLoginAttempt orcherastrator completed:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_LOGIN_ATTEMPT.name(), true);
	}
}