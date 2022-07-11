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
 * This lambda function will return the list of super admins
 *
 * @author AyushRa
 */
public final class ListSuperAdmins implements RequestHandler<Object, List<Admins>> {
	private static final Logger LOGGER = LogManager.getLogger(ListSuperAdmins.class.getName());
	private List<Admins> adminList = null;

	@Override
	public List<Admins> handleRequest(final Object input, final Context context) {
		LOGGER.info(":::::::Request start to list super admins:::::::");

		AdminService adminServiceObj = new AdminService();
		adminList = adminServiceObj.getAdmins(AdminType.SA.name());

		LOGGER.info(":::::::Request completed to list super admins:::::::");

		if (adminList == null || adminList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.LIST_SUPER_ADMINS.name(), false);
		}

		return adminList;
	}
}