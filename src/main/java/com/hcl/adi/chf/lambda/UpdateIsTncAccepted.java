package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.enums.ClinicianType;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update the value of "is_tnc_accepted"
 * column for the specified email id and its type into the corresponding DB
 *
 * @author AyushRa
 */
public final class UpdateIsTncAccepted implements RequestHandler<Map<String, String>, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateIsTncAccepted.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(
				":::::::Request start to update the value of is_tnc_accepted column for the specified email id and its type into the corresponding DB:::::::");
		LOGGER.info("Input: " + input);

		String type = input.get(Constants.QUERY_PARAM_TYPE);
		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);

		try {
			if (ClinicianType.CL.name().equalsIgnoreCase(type)) {
				ClinicianService clinicianServiceObj = new ClinicianService();
				customResponse = clinicianServiceObj.updateIsTncAcceptedColumnInClinicianByEmailId(emailId);
				
			} else {
				// Rest call for ADI module to update is_tnc_accepted field in admins table by emailId - Lambda Name(UpdateIsTncAcceptedInAdmins)
				AdminService adminServiceObj = new AdminService();
				customResponse = adminServiceObj.updateIsTncAcceptedColumnInAdminsByEmailId(emailId);
				
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		LOGGER.info(
				":::::::Request completed to update the value of is_tnc_accepted column for the specified email id and its type into the corresponding DB:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.UPDATE_IS_TNC_ACCEPTED.name(), true);
	}
}