package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdBreachRequest;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will be used to mark isActive status of all 
 * alert in alert_data table to false, when user click the "clear count"
 * option on UI
 *
 * @author DivyaAg
 */
public class ClearThresholdBreachCount implements RequestHandler<ThresholdBreachRequest, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(ClearThresholdBreachCount.class.getName());
	private CustomResponse customResponse = null;
	
	@Override
	public CustomResponse handleRequest(final ThresholdBreachRequest thresholdBreachRequest, final Context context) {
		LOGGER.info(":::::::Request start to clear teh threshold breach count based on patient id:::::::");
		LOGGER.info("input :" + thresholdBreachRequest);

		ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
		customResponse = clinicianDashboardServiceObj.updateAlertStatusByPatientId(thresholdBreachRequest);

		LOGGER.info(":::::::Request completed to clear teh threshold breach count based on patient id:::::::");
		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.CLEAR_THRESHOLD_BREACH.name(), true);
	}
}
