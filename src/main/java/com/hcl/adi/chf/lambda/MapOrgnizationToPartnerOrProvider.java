package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Organization;
import com.hcl.adi.chf.service.OrganizationService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to link Organization,partner organization and provider organization in CPM system
 *
 * @author SandeepSingh
 */
public final class MapOrgnizationToPartnerOrProvider implements RequestHandler<Organization, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(MapOrgnizationToPartnerOrProvider.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Organization organization, final Context context) {
		LOGGER.info(":::::::Request start to update organization details:::::::");
		LOGGER.info("Input: " + organization);

		OrganizationService organizationServiceObj = new OrganizationService();
		response = organizationServiceObj.mapOrgnizationToPartnerOrProvider(organization);

		LOGGER.info(":::::::Request completed to update organization details:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_ORGANIZATION.name(), true);
	}
}