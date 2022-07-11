package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be triggered through cloudwatch cron job and it
 * will fetch the list of those admins/clinicians whose password is about to expire and
 * notification email need to be send to those users based on
 * pwd_expiry_notification_start_in_days. And, it will send that list into SQS
 *
 * @author DivyaAg
 */
public final class SendPwdExpiryInfoListToSQS implements RequestHandler<Object, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(SendPwdExpiryInfoListToSQS.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Object input, final Context context) {
		LOGGER.info(
				":::::::SendPwdExpiryInfoListToSQS lambda function triggered through cloudwatch cron job to fetch the list of those admins whom password is about to expire and send that list into SQS:::::::");

		AdminService adminServiceObj = new AdminService();
		response = adminServiceObj.getPwdExpiryInfoListOfEligibleAdminsAndSendThatListToSQS();
		// Send the clinician list to pwd expiry info as well
		ClinicianService clinicianServiceObj = new ClinicianService();
		response = clinicianServiceObj.getPwdExpiryInfoListOfEligibleCliniciansAndSendThatListToSQS();

		LOGGER.info(
				":::::::SendPwdExpiryInfoListToSQS lambda function completed which was triggered through cloudwatch cron job to fetch the list of those admins whom password is about to expire and that list has been sent into SQS:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.SEND_PWD_EXPIRY_INFO_LIST_TO_SQS.name(), true);
	}
}