package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientComments;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will insert a comment of patient by clinician
 *
 * @author Sandeep Singh
 */
public final class InsertPatientComment implements RequestHandler<PatientComments, PatientComments> {
	private static final Logger LOGGER = LogManager.getLogger(InsertPatientComment.class.getName());
	private PatientComments response = new PatientComments();

	@Override
	public PatientComments handleRequest(final PatientComments bean, final Context context) {
		LOGGER.info(":::::::Request start to insert the record into clinician_comments table:::::::");
		LOGGER.info("Input: " + bean);

		if (bean != null) {
			PatientService service = new PatientService();
			response = service.savePatientComment(bean);
		}

		LOGGER.info(":::::::Request completed to insert the record into patient_threshold table:::::::");

		return ResponseGenerator.generateResponseCustom(response, ApiErrorKey.INSERT_PATIENT_COMMENT.name(), true);
	}
}