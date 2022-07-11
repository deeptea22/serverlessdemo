package com.hcl.adi.chf.lambda;

import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will return a flag to user to determine if  baseline reading is 
 *  already available in DB for a specific patient id or not
 * 
 *
 * @author DivyaAg
 */
public final class IsBaseLineExistsByPatientId implements RequestHandler<Map<String, Integer>, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(IsBaseLineExistsByPatientId.class.getName());
	private CustomResponse response = null;


	@Override
	public CustomResponse handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to fetch baseline reading based on patient id:::::::");
		LOGGER.info("Input: " + input);

		int patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);

		ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
		response = clinicianDashboardServiceObj.isBaseLineExistsByPatientId(patientId);

		LOGGER.info(":::::::Request completed to fetch baseline reading based on patient id:::::::");

		if (Status.OK.getStatusCode() != response.getStatusCode()) {
			return ResponseGenerator.generateResponse(response, ApiErrorKey.GET_BASELINE_READING_STATUS.name(), true);
		}

		return response;
	}
}