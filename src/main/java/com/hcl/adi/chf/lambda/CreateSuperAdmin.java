package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to create a super admin by another super
 * admin
 *
 * @author AyushRa
 */
public final class CreateSuperAdmin implements RequestHandler<Admins, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(CreateSuperAdmin.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Admins admin, final Context context) {
		LOGGER.info(":::::::Request start to create super admin:::::::");
		LOGGER.info("Input: " + admin);

		AdminService adminServiceObj = new AdminService();
		response = adminServiceObj.createAdmin(admin, AdminType.SA.name(), "N");

		LOGGER.info(":::::::Request completed to create super admin:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.CREATE_SUPER_ADMIN.name(), true);
	}
}