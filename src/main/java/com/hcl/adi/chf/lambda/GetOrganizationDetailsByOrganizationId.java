package com.hcl.adi.chf.lambda;

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
public final class GetOrganizationDetailsByOrganizationId implements RequestHandler<Map<String, Integer>, Organization> {
	private static final Logger LOGGER = LogManager.getLogger(GetOrganizationDetailsByOrganizationId.class.getName());
	private Organization organization = null;

	@Override
	public Organization handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(
				":::::::Request start to get an Organization along with its pwd policy, its data archival policy and its terms and condititons based on Organization id:::::::");
		LOGGER.info("Input: " + input);

		int organizationId = input.get(Constants.QUERY_PARAM_ORGANIZATION_ID);

		OrganizationService organizationServiceObj = new OrganizationService();
		organization = organizationServiceObj.getOrganizationDetailsByOrganizationId(organizationId);

		LOGGER.info(":::::::Response to return from GetOrganizationDetailsByOrganizationId:::::::" + organization);
		LOGGER.info(
				":::::::Request completed to get an institution along with its pwd policy, its data archival policy and its terms and condititons based on Organization id:::::::");

		if (organization == null || organization.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_ORGANIZATION_DETAILS_BY_ORGANIZATION_ID.name(), false);
		}

		return organization;
	}
}