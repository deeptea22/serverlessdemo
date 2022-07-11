package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update the clinician details
 *
 * @author AyushRa
 */
public final class UpdateClinician implements RequestHandler<Clinician, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateClinician.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Clinician clinician, final Context context) {
		LOGGER.info(":::::::Request start to update clinician details:::::::");
		LOGGER.info("Input: " + clinician);

		ClinicianService clinicianServiceObj = new ClinicianService();
		response = clinicianServiceObj.updateClinician(clinician);

		LOGGER.info(":::::::Request completed to update clinician details:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_PATIENT_MASTER_OTHER_MAPPING.name(), true);
	}
}