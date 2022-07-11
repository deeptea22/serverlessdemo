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
 * This lambda function will remove(soft delete) the patient provider from patient provider mapping.
 *
 * @author Shivendra
 */
public final class RemovePatientProvider implements RequestHandler<PatientProvider, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(RemovePatientProvider.class.getName());
	private CustomResponse response = null;
	
    @Override
    public CustomResponse handleRequest(PatientProvider patientProvider, Context context) {
    	LOGGER.info(":::::::Request start to remove patient provider:::::::");
		LOGGER.info("Input: " + patientProvider);
		PatientService patientService = new PatientService();
		
		response = patientService.removePatientProvider(patientProvider);

		LOGGER.info(":::::::Request end to remove patient provider:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.REMOVE_PATIENT_PROVIDER.name(), true); 
    }

}
