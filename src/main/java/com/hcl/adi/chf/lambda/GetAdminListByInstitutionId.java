package com.hcl.adi.chf.lambda;

import java.util.List;
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
 * This lambda function will return the list of admins based on institution id
 *
 * @author AyushRa
 */
public final class GetAdminListByInstitutionId implements RequestHandler<Map<String, Integer>, List<Admins>> {
	private static final Logger LOGGER = LogManager.getLogger(GetAdminListByInstitutionId.class.getName());
	private List<Admins> adminList = null;

	@Override
	public List<Admins> handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get the list of admins based on institution id:::::::");
		LOGGER.info("Input: " + input);

		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);

		AdminService adminServiceObj = new AdminService();
		adminList = adminServiceObj.getListOfAdminsByInstitutionId(institutionId);

		LOGGER.info(":::::::Request completed to get the list of admins based on institution id:::::::");

		if (adminList == null || adminList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_ADMIN_LIST_BY_INSTITUTION_ID.name(), false);
		}

		return adminList;
	}
}