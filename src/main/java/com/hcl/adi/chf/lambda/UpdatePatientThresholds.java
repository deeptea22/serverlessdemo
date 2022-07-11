package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will update Threshold of a patient based on institution
 * id
 *
 * @author Sandeep Singh
 */
public final class UpdatePatientThresholds implements RequestHandler<PatientThreshold, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientThresholds.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final PatientThreshold bean, final Context context) {
		LOGGER.info(":::::::Request start to update Patient Threshold :::::::");
		LOGGER.info("Input: " + bean);

		if (bean != null) {
			PatientService service = new PatientService();
			response = service.updatePatientThresholds(bean);
		}

		LOGGER.info(":::::::Request completed to update Patient Threshold:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_PATIENT_THRESHOLDS.name(), true);
	}
}