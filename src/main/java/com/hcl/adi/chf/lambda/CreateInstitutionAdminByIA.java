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
 * This lambda function will be used to create an institution admin by another
 * institution admin
 *
 * @author AyushRa
 */
public final class CreateInstitutionAdminByIA implements RequestHandler<Admins, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(CreateInstitutionAdminByIA.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Admins admin, final Context context) {
		LOGGER.info(":::::::Request start to create institution admin by institution admin:::::::");
		LOGGER.info("Input: " + admin);

		AdminService adminServiceObj = new AdminService();
		response = adminServiceObj.createAdmin(admin, AdminType.IA.name(), "N");

		LOGGER.info(":::::::Request completed to create institution admin by institution admin:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.CREATE_INSTITUTION_ADMIN_BY_IA.name(), true);
	}
}