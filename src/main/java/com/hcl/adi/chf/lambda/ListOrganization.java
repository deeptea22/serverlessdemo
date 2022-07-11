package com.hcl.adi.chf.lambda;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Organization;
import com.hcl.adi.chf.service.OrganizationService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the list of institutions
 *
 * @author SandeepSingh
 */
public final class ListOrganization implements RequestHandler<Object, List<Organization>> {
	private static final Logger LOGGER = LogManager.getLogger(ListOrganization.class.getName());
	private List<Organization> institutionList = null;

	@Override
	public List<Organization> handleRequest(final Object input, final Context context) {
		LOGGER.info(":::::::Request start to list Organizations:::::::");

		OrganizationService organizationServiceObj = new OrganizationService();
		institutionList = organizationServiceObj.getOrganizations();

		LOGGER.info(":::::::Request completed to list Organizations:::::::");

		if (institutionList == null || institutionList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.LIST_ORGANIZATION.name(), false);
		}

		return institutionList;
	}
}