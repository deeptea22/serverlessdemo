package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update the value of "pwd_updated_date"
 * column in admins table for the specified email id
 *
 * @author AyushRa
 */
public final class UpdatePwdUpdatedDateInAdmins implements RequestHandler<Map<String, String>, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePwdUpdatedDateInAdmins.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(
				":::::::Request start to update the value of pwd_updated_date column in admins table for the specified email id:::::::");
		LOGGER.info("Input: " + input);

		String emailId = input.get("emailId");

		AdminService adminServiceObj = new AdminService();
		response = adminServiceObj.updatePwdUpdatedDateColumnInAdminsByEmailId(emailId);

		LOGGER.info(
				":::::::Request completed to update the value of pwd_updated_date column in admins table for the specified email id:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_PWD_UPDATED_DATE_IN_ADMINS.name(), true);
	}
}