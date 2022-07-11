package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.enums.ClinicianType;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to create a clinician by institution admin
 *
 * @author AyushRa
 */
public final class CreateClinician implements RequestHandler<Clinician, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(CreateClinician.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Clinician clinician, final Context context) {
		LOGGER.info(":::::::Request start to create a clinician:::::::");
		LOGGER.info("Input: " + clinician);

		ClinicianService clinicianServiceObj = new ClinicianService();
		response = clinicianServiceObj.createClinician(clinician, ClinicianType.CL.name());

		LOGGER.info(":::::::Request completed to create a clinician:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.CREATE_CLINICIAN.name(), true);
	}
}