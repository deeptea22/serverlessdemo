package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will update the patient default
 * threshold values based on institution default threshold policy
 * by patient id
 *
 * @author DivyaAg
 */
public final class RestoreDefaultThresholdByPatientId implements RequestHandler<Map<String, Integer>, PatientThreshold> {
	private static final Logger LOGGER = LogManager.getLogger(RestoreDefaultThresholdByPatientId.class.getName());
	private PatientThreshold patientThreshold = null;

	@Override
	public PatientThreshold handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to restore default threshold of patient by patientId :::::::");
		LOGGER.info("Input: " + input);

		int patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);

		PatientService patientServiceObj = new PatientService();
		patientThreshold = patientServiceObj.restoreDefaultThresholdBasedOnPatientId(patientId);

		if (patientThreshold == null || patientThreshold.getThreshold() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.RESTORE_PATIENT_THRESHOLD.name(), false);
		}

		LOGGER.info(":::::::Request completed to restore default threshold of patient by patientId:::::::");

		return patientThreshold;
	}
}
