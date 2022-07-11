package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientActions;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will persist patient action details in DB. Also, it will
 * update 'is_action_open' flag as 1 in patient_phi table for the specified
 * patient id
 *
 * @author Shivendra Singh
 */
public final class AddPatientAction implements RequestHandler<PatientActions, PatientActions> {
	private static final Logger LOGGER = LogManager.getLogger(AddPatientAction.class.getName());
	PatientActions patientAction = new PatientActions();

	@Override
	public PatientActions handleRequest(final PatientActions patientActions, final Context context) {
		LOGGER.info(":::::::Request start to persist new patient action :::::::");
		LOGGER.info("Input: " + patientActions);

		PatientService patientServiceObj = new PatientService();
		patientAction = patientServiceObj.addPatientActions(patientActions);

		LOGGER.info(":::::::Request completed to persist new patient action:::::::");

		return ResponseGenerator.generateResponseCustom(patientAction, ApiErrorKey.ADD_PATIENT_ACTION.name(), true);
	}
}