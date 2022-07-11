package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.service.InstitutionService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to create an institution in DB
 *
 * @author DivyaAg
 */
public final class PersistInstitution implements RequestHandler<Institution, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(PersistInstitution.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Institution institution, final Context context) {
		LOGGER.info(":::::::Request start to create an institution:::::::");
		LOGGER.info("Input: " + institution);

		InstitutionService institutionServiceObj = new InstitutionService();
		response = institutionServiceObj.createInstitution(institution);

		LOGGER.info(":::::::Request completed to create an institution:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.PERSIST_INSTITUTION.name(), true);
	}
}