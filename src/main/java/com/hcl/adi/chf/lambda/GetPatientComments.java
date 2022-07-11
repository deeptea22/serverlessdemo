package com.hcl.adi.chf.lambda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientComments;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the list of comments that has been added by
 * a clinician for the specified patient
 *
 * @author Sandeep Singh
 */
public final class GetPatientComments implements RequestHandler<Map<String, Integer>, Map<String, Object>> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientComments.class.getName());
	private List<PatientComments> patientCommentsList = null;
	private Map<String, Object> patientCommentsResponse = new HashMap<String, Object>();

	@Override
	public Map<String, Object> handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get the list of comments for the specified patient:::::::");
		LOGGER.info("Input: " + input);

		Integer patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);

		if (patientId != null) {
			PatientService patientService = new PatientService();
			patientCommentsList = patientService.getPatientComments(patientId);
		}

		LOGGER.info(":::::::Request completed to get the list of comments for the specified patient:::::::");

		if (patientCommentsList == null || patientCommentsList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_COMMENTS.name(), false);
		}else {
			patientCommentsResponse.put(Constants.RESPONSE_PATIENT_REORD_COUNT, patientCommentsList.size());
			patientCommentsResponse.put(Constants.RESPONSE_PATIENT_COMMENTS_LIST, patientCommentsList);
		}

		return patientCommentsResponse;
	}
}