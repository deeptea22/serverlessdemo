package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return a clinician detail based on email id
 *
 * @author AyushRa
 */
public final class GetClinicianByEmailId implements RequestHandler<Map<String, String>, Clinician> {
	private static final Logger LOGGER = LogManager.getLogger(GetClinicianByEmailId.class.getName());
	private Clinician clinician = null;

	@Override
	public Clinician handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get a clinician detail based on email id:::::::");
		LOGGER.info("Input: " + input);

		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);

		ClinicianService clinicianServiceObj = new ClinicianService();
		clinician = clinicianServiceObj.getClinicianByEmailId(emailId);

		LOGGER.info(":::::::Response to return from GetClinicianByEmailId:::::::" + clinician);
		LOGGER.info(":::::::Request completed to get a clinician detail based on email id:::::::");

		if (clinician == null || clinician.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_CLINICIAN_BY_EMAIL_ID.name(), false);
		}

		return clinician;
	}
}