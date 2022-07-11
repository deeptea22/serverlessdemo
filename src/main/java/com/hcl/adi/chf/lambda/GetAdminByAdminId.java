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
 * This lambda function will return an admin detail based on admin id
 *
 * @author AyushRa
 */
public final class GetAdminByAdminId implements RequestHandler<Map<String, Integer>, Admins> {
	private static final Logger LOGGER = LogManager.getLogger(GetAdminByAdminId.class.getName());
	private Admins admin = null;

	@Override
	public Admins handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get an admin detail based on admin id:::::::");
		LOGGER.info("Input: " + input);

		int adminId = input.get(Constants.QUERY_PARAM_ADMIN_ID);

		AdminService adminServiceObj = new AdminService();
		admin = adminServiceObj.getAdminByAdminId(adminId);

		LOGGER.info(":::::::Request completed to get an admin detail based on admin id:::::::");

		if (admin == null || admin.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_ADMIN_BY_ADMIN_ID.name(), false);
		}

		return admin;
	}
}