package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will return the patient provider details by chf_patient_id
 *
 * @author DivyaAg
 */
public final class GetPatientProviderDetailsByChfPatientId implements RequestHandler<Map<String, String>, PatientProvider> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientProviderDetailsByChfPatientId.class.getName());
	private PatientProvider patientProvider = null;

	@Override
	public PatientProvider handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get patient provider details of a specified patient:::::::");
		LOGGER.info("Input: " + input);

		String chfPatientId = input.get(Constants.QUERY_PARAM_CHF_PATIENT_ID);

		PatientService patientServiceObj = new PatientService();
		patientProvider = patientServiceObj.getPatientProviderDetailsByChfPatientId(chfPatientId);

		if (patientProvider.getChfPatientId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_PROVIDERS_BY_CHF_PATIENT_ID.name(), false);
		}

		LOGGER.info(":::::::Request completed to get patient provider details of a specified patient:::::::");

		return patientProvider;
	}
}