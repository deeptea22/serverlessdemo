package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientClinicianMapping;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to add/delete a specified patient from
 * specified clinician's watchlist
 *
 * @author AyushRa
 */
public final class UpdatePatientClinicianMapping implements RequestHandler<PatientClinicianMapping, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientClinicianMapping.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final PatientClinicianMapping pcm, final Context context) {
		LOGGER.info(":::::::Request started to add/delete a patient from clinician's watchlist:::::::");

		PatientService patientService = new PatientService();
		customResponse = patientService.updatePatientClinicianMapping(pcm);

		LOGGER.info(":::::::Request completed to add/delete a patient from clinician's watchlist:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.UPDATE_PATIENT_CLINICIAN_MAPPING.name(), true);
	}
}