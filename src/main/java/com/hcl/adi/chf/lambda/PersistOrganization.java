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
 * This lambda function will be used to create an Organization in DB
 *
 * @author SandeepSingh
 */
public final class PersistOrganization implements RequestHandler<Organization, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(PersistOrganization.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Organization organization, final Context context) {
		LOGGER.info(":::::::Request start to create an Organization:::::::");
		LOGGER.info("Input: " + organization);

		OrganizationService service = new OrganizationService();
		response = service.createOrganization(organization);

		LOGGER.info(":::::::Request completed to create an Organization:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.PERSIST_ORGANIZATION.name(), true);
	}
}