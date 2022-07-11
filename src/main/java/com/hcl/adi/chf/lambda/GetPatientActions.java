package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientActions;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return patient actions with their comments based on
 * patient id
 *
 * @author Shivendra Singh
 */
public final class GetPatientActions implements RequestHandler<Map<String, Integer>, PatientActions> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientActions.class.getName());
	private PatientActions patientActions = null;

	@Override
	public PatientActions handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get patient actions and their comments based on patient id:::::::");
		LOGGER.info("Input: " + input);

		Integer patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);

		if (patientId != null) {
			PatientService patientServiceObj = new PatientService();
			patientActions = patientServiceObj.getPatientActions(patientId);
		}

		LOGGER.info(":::::::Request completed to get patient actions and their comments based on patient id:::::::");

		if (patientActions == null || patientActions.getPatientActions() == null ||  patientActions.getPatientActions().isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_ACTIONS.name(), false);
		}

		return patientActions;
	}
}