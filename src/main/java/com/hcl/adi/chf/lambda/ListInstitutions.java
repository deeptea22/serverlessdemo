package com.hcl.adi.chf.lambda;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.service.InstitutionService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the list of institutions
 *
 * @author AyushRa
 */
public final class ListInstitutions implements RequestHandler<Object, List<Institution>> {
	private static final Logger LOGGER = LogManager.getLogger(ListInstitutions.class.getName());
	private List<Institution> institutionList = null;

	@Override
	public List<Institution> handleRequest(final Object input, final Context context) {
		LOGGER.info(":::::::Request start to list institutions:::::::");

		InstitutionService institutionServiceObj = new InstitutionService();
		institutionList = institutionServiceObj.getInstitutions();

		LOGGER.info(":::::::Request completed to list institutions:::::::");

		if (institutionList == null || institutionList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.LIST_INSTITUTION.name(), false);
		}

		return institutionList;
	}
}