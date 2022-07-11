package com.hcl.adi.chf.lambda;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will return ecg data for 3 latest readings
 * by patient id
 * @author DivyaAg
 */
public final class GetEcgDataForAvailableReading implements RequestHandler<Map<String, Integer>, List<DeviceData> > {
	private static final Logger LOGGER = LogManager.getLogger(GetEcgDataForAvailableReading.class.getName());
	private List<DeviceData>  ecgList = null;

	@Override
	public List<DeviceData>  handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get ecg data based on patient id:::::::");
		LOGGER.info("Input: " + input);

		int patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);
		
		ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
		ecgList = clinicianDashboardServiceObj.getEcgDataByPatientId(patientId);

		LOGGER.info(":::::::Request completed to get ecg data based on patient id:::::::");

		if (ecgList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_ECG_DATA_BY_FOR_LATEST_AVAILABLE_READINGS_PATIENT_ID.name(), false);
		}

		return ecgList;
	}
}