package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientActions;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will update 'status' as CLOSED in patient_actions table
 * for the specified action id. Also, it will update 'is_action_open' flag as 0
 * in patient_phi table for the specified patient id
 *
 * @author Shivendra Singh
 */
public final class UpdatePatientAction implements RequestHandler<PatientActions, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientAction.class.getName());
	CustomResponse customResponse = new CustomResponse();

	@Override
	public CustomResponse handleRequest(final PatientActions patientActions, final Context context) {
		LOGGER.info(":::::::Request start to update status as CLOSED in patient_actions table:::::::");
		LOGGER.info("Input: " + patientActions);

		PatientService patientServiceObj = new PatientService();
		customResponse = patientServiceObj.updatePatientAction(patientActions);

		LOGGER.info(":::::::Request completed to update status as CLOSED in patient_actions table:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.UPDATE_PATIENT_ACTION.name(), true);
	}
}