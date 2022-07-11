package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return an admin detail based on email id. Right
 * now, we are using this lambda while editing an admin on IA dashboard page
 *
 * @author AyushRa
 */
public final class GetAdminByEmailId implements RequestHandler<Map<String, String>, Admins> {
	private static final Logger LOGGER = LogManager.getLogger(GetAdminByEmailId.class.getName());
	private Admins admin = null;

	@Override
	public Admins handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get an admin detail based on email id:::::::");
		LOGGER.info("Input: " + input);

		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);

		AdminService adminServiceObj = new AdminService();
		admin = adminServiceObj.getAdminByEmailId(emailId);

		LOGGER.info(":::::::Request completed to get an admin detail based on email id:::::::");

		if (admin == null || admin.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_ADMIN_BY_EMAIL_ID.name(), false);
		}

		return admin;
	}
}