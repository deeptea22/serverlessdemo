package com.hcl.adi.chf.lambda;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Organization;
import com.hcl.adi.chf.service.OrganizationService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return an Organization along with its pwd policy,
 * its data archival policy and its terms and conditions based on Organization id
 *
 * @author SandeepSingh
 */
public final class GetOrganizationListByType implements RequestHandler<Map<String, String>, List<Organization>> {
	private static final Logger LOGGER = LogManager.getLogger(GetOrganizationListByType.class.getName());
	private List<Organization> organizationList = null;

	@Override
	public List<Organization> handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get an Organization List based on Organization Type:::::::");
		LOGGER.info("Input: " + input);

		String organizationType = input.get(Constants.QUERY_PARAM_ORGANIZATION_TYPE);

		OrganizationService organizationServiceObj = new OrganizationService();
		organizationList = organizationServiceObj.getOrganizationListByType(organizationType);

		LOGGER.info(":::::::Request completed to get an Organization List based on Organization Type:::::::");

		if (organizationList == null || organizationList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_ORGANIZATION_DETAILS_BY_ORGANIZATION_ID.name(), false);
		}

		return organizationList;
	}
}