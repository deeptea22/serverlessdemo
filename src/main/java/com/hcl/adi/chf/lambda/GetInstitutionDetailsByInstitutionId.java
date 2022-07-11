package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.service.InstitutionService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return an institution along with its pwd policy,
 * its data archival policy and its terms and conditions based on institution id
 *
 * @author AyushRa
 */
public final class GetInstitutionDetailsByInstitutionId implements RequestHandler<Map<String, Integer>, Institution> {
	private static final Logger LOGGER = LogManager.getLogger(GetInstitutionDetailsByInstitutionId.class.getName());
	private Institution institution = null;

	@Override
	public Institution handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(
				":::::::Request start to get an institution along with its pwd policy, its data archival policy and its terms and condititons based on institution id:::::::");
		LOGGER.info("Input: " + input);

		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);

		InstitutionService institutionServiceObj = new InstitutionService();
		institution = institutionServiceObj.getInstitutionDetailsByInstitutionId(institutionId);

		LOGGER.info(":::::::Response to return from GetInstitutionDetailsByInstitutionId:::::::" + institution);
		LOGGER.info(
				":::::::Request completed to get an institution along with its pwd policy, its data archival policy and its terms and condititons based on institution id:::::::");

		if (institution == null || institution.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_INSTITUTION_DETAILS_BY_INSTITUTION_ID.name(), false);
		}

		return institution;
	}
}