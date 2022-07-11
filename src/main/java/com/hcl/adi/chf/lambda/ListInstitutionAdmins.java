package com.hcl.adi.chf.lambda;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the list of institution admins
 *
 * @author AyushRa
 */
public final class ListInstitutionAdmins implements RequestHandler<Object, List<Admins>> {
	private static final Logger LOGGER = LogManager.getLogger(ListInstitutionAdmins.class.getName());
	private List<Admins> adminList = null;

	@Override
	public List<Admins> handleRequest(final Object input, final Context context) {
		LOGGER.info(":::::::Request start to list institution admins:::::::");

		AdminService adminServiceObj = new AdminService();
		adminList = adminServiceObj.getAdmins(AdminType.IA.name());

		LOGGER.info(":::::::Request completed to list institution admins:::::::");

		if (adminList == null || adminList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.LIST_INSTITUTION_ADMINS.name(), false);
		}

		return adminList;
	}
}