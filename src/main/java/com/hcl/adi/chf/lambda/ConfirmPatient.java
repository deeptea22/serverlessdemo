package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will be used to confirm patient along with provider deatils
 * from mobile App
 *
 * @author DivyaAg
 * 
 */
public final class ConfirmPatient implements RequestHandler<PatientProvider, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(ConfirmPatient.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final PatientProvider patientProvider, final Context context) {
		LOGGER.info(
				":::::::Request start to confirm patient along with provider details:::::::");
		LOGGER.info("Input: " + patientProvider);
		if(patientProvider.getChfPatientId() != null && patientProvider.getInstitutionId() != null) {
			PatientService patientservice = new PatientService();
			response = patientservice.confirmPatient(patientProvider);
		}
		
		LOGGER.info(":::::::Request completed to confirm patient along with provider details:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.PATIENT_CONFIRM.name(), true);
	}
}