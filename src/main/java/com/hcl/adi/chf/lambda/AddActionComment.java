package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientActionComments;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will persist comments corresponding to a patient action
 *
 * @author Shivendra Singh
 */
public final class AddActionComment implements RequestHandler<PatientActionComments, PatientActionComments> {
	private static final Logger LOGGER = LogManager.getLogger(AddActionComment.class.getName());
	PatientActionComments patientActionComment = new PatientActionComments();

	@Override
	public PatientActionComments handleRequest(final PatientActionComments patientActionComments,
			final Context context) {
		LOGGER.info(":::::::Request start to persist comments corresponding to a patient action:::::::");
		LOGGER.info("Input: " + patientActionComments);

		PatientService patientService = new PatientService();
		patientActionComment = patientService.addActionComment(patientActionComments);

		LOGGER.info(":::::::Request completed to persist comments corresponding to a patient action:::::::");

		return ResponseGenerator.generateResponseCustom(patientActionComment, ApiErrorKey.ADD_ACTION_COMMENT.name(), true);
	}
}