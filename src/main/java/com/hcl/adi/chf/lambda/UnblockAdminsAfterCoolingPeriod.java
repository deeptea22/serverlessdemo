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
 * will fetch the list of those admins/clinician who has been blocked(deactivate) because
 * of unsuccessful attempts allowed for their institution and cooling period has
 * expired for them. Moreover, it will enable those admins in database and
 * cognito
 *
 * @author DivyaAg
 */
public final class UnblockAdminsAfterCoolingPeriod implements RequestHandler<Object, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UnblockAdminsAfterCoolingPeriod.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Object input, final Context context) {
		LOGGER.info(
				":::::::UnblockAdminsAfterCoolingPeriod lambda function triggered through cloudwatch cron job to enable admins whose cooling period has expired:::::::");

		AdminService adminServiceObj = new AdminService();
		adminServiceObj.unblockAdminsWhoseCoolingPeriodHasExpired();
		ClinicianService clinicianServiceObj = new ClinicianService();
		clinicianServiceObj.unblockCliniciansWhoseCoolingPeriodHasExpired();

		LOGGER.info(
				":::::::UnblockAdminsAfterCoolingPeriod lambda function completed which was triggered through cloudwatch cron job to enable admins whose cooling period has expired:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UNBLOCK_ADMINS_AFTER_COOLING_PERIOD.name(), true);
	}
}