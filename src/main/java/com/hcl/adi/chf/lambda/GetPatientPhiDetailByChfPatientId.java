package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the PatientPhi details based on chfPatientId
 *
 * @author DivyaAg
 */
public final class GetPatientPhiDetailByChfPatientId implements RequestHandler<Map<String, String>, PatientPhi> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientPhiDetailByChfPatientId.class.getName());
	private PatientPhi patientPhi = null;

	@Override
	public PatientPhi handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to fetch patient phi details based on chfPatientId:::::::");
		LOGGER.info("Input: " + input);

		String chfPatientId = input.get(Constants.QUERY_PARAM_CHF_PATIENT_ID);

		PatientService patientServiceObj = new PatientService();
		patientPhi = patientServiceObj.getPatientPhiDetailsBasedOnChfPatientId(chfPatientId);

		if (patientPhi == null || patientPhi.getChfPatientId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_PHI_DETAIL_BY_CHF_PATIENT_ID.name(), false);
		}

		LOGGER.info(":::::::Request start to fetch patient phi details based on chfPatientId:::::::");

		return patientPhi;
	}
}