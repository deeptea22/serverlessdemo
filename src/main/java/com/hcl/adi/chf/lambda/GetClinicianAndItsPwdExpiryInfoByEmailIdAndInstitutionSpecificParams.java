package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return a clinician and its pwd expiry info detail
 * based on email id and institution specific parameters(pwdExpireInDays &
 * pwdExpiryNotificationStartInDays)
 *
 * @author AyushRa
 */
public final class GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParams implements RequestHandler<Map<String, String>, UserDetails> {
	private static final Logger LOGGER = LogManager.getLogger(GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParams.class.getName());
	private UserDetails userDetails = null;

	@Override
	public UserDetails handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get a clinician and its pwd expiry info detail based on email id and institution specific parameters(pwdExpireInDays & pwdExpiryNotificationStartInDays):::::::");
		LOGGER.info("Input: " + input);

		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);
		int pwdExpireInDays = Integer.parseInt(input.get(Constants.QUERY_PARAM_PWD_EXPIRE_IN_DAYS));
		int pwdExpiryNotificationStartInDays = Integer.parseInt(input.get(Constants.QUERY_PARAM_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS));

		ClinicianService clinicianServiceObj = new ClinicianService();
		userDetails = clinicianServiceObj.getClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters(emailId, pwdExpireInDays, pwdExpiryNotificationStartInDays);

		LOGGER.info(":::::::Response to return from GetClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParams:::::::" + userDetails);
		LOGGER.info(":::::::Request completed to get a clinician and its pwd expiry info detail based on email id and institution specific parameters(pwdExpireInDays & pwdExpiryNotificationStartInDays):::::::");

		if (userDetails == null || userDetails.getCreatedBy() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_CLINICIAN_AND_PWD_EXPIRY_INFO_BY_EMAIL_ID_AND_INSTITUTION_SPECIFIC_PARAM.name(), false);
		}

		return userDetails;
	}
}