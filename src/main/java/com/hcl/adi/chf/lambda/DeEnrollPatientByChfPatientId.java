package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will de-enroll the Patient from patientPHI based on chfPatientId
 *
 * @author DivyaAg
 */
public final class DeEnrollPatientByChfPatientId implements RequestHandler<Map<String, String>, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(DeEnrollPatientByChfPatientId.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to de-enroll the patient based on chfPatientId:::::::");
		LOGGER.info("Input: " + input);

		String chfPatientId = input.get(Constants.QUERY_PARAM_CHF_PATIENT_ID);
		String updatedBy = input.get(Constants.QUERY_PARAM_UPDATED_BY);

		PatientService patientServiceObj = new PatientService();
		customResponse = patientServiceObj.deEnrollPatientBasedOnChfPatientId(chfPatientId, updatedBy);

		
		LOGGER.info(":::::::Request completed to de-enroll the patient based on chfPatientId:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.DEENROLL_PATIENT.name(), true);
	}
}