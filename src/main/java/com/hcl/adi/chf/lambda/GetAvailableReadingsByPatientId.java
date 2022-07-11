package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientReadingResponse;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will return list of all available readings of a patient
 *
 * @author DivyaAg
 */
public final class GetAvailableReadingsByPatientId implements RequestHandler<Map<String, Integer>, PatientReadingResponse> {
	private static final Logger LOGGER = LogManager.getLogger(GetAvailableReadingsByPatientId.class.getName());
	private PatientReadingResponse patientReadingResponse = null;

	@Override
	public PatientReadingResponse handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get reading dates from device_data based on patient id:::::::");
		LOGGER.info("Input: " + input);

		int patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);
		int pageStartIndex = input.get(Constants.QUERY_PARAM_PAGE_START_INDEX);
		int pageCount = input.get(Constants.QUERY_PARAM_PAGE_COUNT);

		ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
		patientReadingResponse = clinicianDashboardServiceObj.getDeviceDataByPatientId(patientId, pageStartIndex, pageCount);

		LOGGER.info(":::::::Request completed to get reading dates from device_data based on patient id:::::::");

		if (patientReadingResponse== null || patientReadingResponse.getDeviceDataList() == null || patientReadingResponse.getDeviceDataList().isEmpty() || patientReadingResponse.getTotalReadingsAvailable() == 0) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_AVAILABLE_READINGS_BY_PATIENT_ID.name(), false);
		}

		return patientReadingResponse;
	}
}