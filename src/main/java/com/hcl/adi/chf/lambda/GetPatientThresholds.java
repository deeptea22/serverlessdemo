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
 * This lambda function will return Threshold value of patient based on
 * institution id
 *
 * @author Sandeep Singh
 */
public final class GetPatientThresholds implements RequestHandler<Map<String, Integer>, PatientThreshold> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientThresholds.class.getName());
	private PatientThreshold patientThreshold = null;
	
	@Override
	public PatientThreshold handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get patient threshold of Patient by patient id:::::::");
		

		if (input.get(Constants.QUERY_PARAM_PATIENT_ID) != null) {
			PatientService service = new PatientService();
			Integer patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);
			
			patientThreshold = service.getPatientThresholds(patientId);
		}
		System.out.println(patientThreshold);

		LOGGER.info(":::::::Request completed to  get patient threshold by patient id:::::::");

		if (patientThreshold == null || patientThreshold.getThreshold() == null || patientThreshold.getThreshold().isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_THRESHOLDS.name(), false);
		}

		return patientThreshold;
	}
}