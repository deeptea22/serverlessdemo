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
import com.hcl.adi.chf.util.SESUtil;

/**
 * This lambda function will be used to update the value of "pwd_updated_date"
 * column for the specified email id and its type into the corresponding DB
 *
 * @author AyushRa
 */
public final class UpdatePwdUpdatedDate implements RequestHandler<Map<String, String>, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePwdUpdatedDate.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(
				":::::::Request start to update the value of pwd_updated_date column for the specified email id and its type into the corresponding DB:::::::");
		LOGGER.info("Input: " + input);

		String type = input.get(Constants.QUERY_PARAM_TYPE);
		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);
		
		try {
			if (ClinicianType.CL.name().equalsIgnoreCase(type) && emailId != null && !emailId.isEmpty()) {
				ClinicianService clinicianServiceObj = new ClinicianService();
				customResponse = clinicianServiceObj.updatePwdUpdatedDateColumnInClinicianByEmailId(emailId);

			} else if(emailId != null && !emailId.isEmpty()){
				// Rest call for ADI module to update pwd_updated_date field in admins table by
				// emailId - Lambda Name(UpdatePwdUpdatedDateInAdmins)
				AdminService adminServiceObj = new AdminService();
				customResponse = adminServiceObj.updatePwdUpdatedDateColumnInAdminsByEmailId(emailId);
			}

			if (customResponse != null && customResponse.getStatusCode() == 200) {
				sendPwdUpdatedMailToUser(input);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		LOGGER.info(
				":::::::Request completed to update the value of pwd_updated_date column for the specified email id and its type into the corresponding DB:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.UPDATE_PWD_UPDATED_DATE.name(), true);
	}

	private static final void sendPwdUpdatedMailToUser(final Map<String, String> input) {
		LOGGER.info(":::::::Request start to send pwd updated mail to user::::::: ");

		String toMail = input.get(Constants.QUERY_PARAM_EMAIL_ID);

		try {
			SESUtil.sendEmailToSpecifiedEmailID(Constants.PASSWORD_CHANGE_FROM_EMAIL_ID, toMail, null,
					Constants.PASSWORD_CHANGE_EMAIL_SUBJECT, Constants.PASSWORD_CHANGE_EMAIL_BODY);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		LOGGER.info(":::::::Request completed to send pwd updated mail to user::::::: ");
	}
}