package com.hcl.adi.chf.lambda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientRecordResponse;
import com.hcl.adi.chf.service.DerivedDataService;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the lists of different derived data(data
 * generated through processor) for the readings that specified patient took in
 * a single object named as PatientRecordResponse and those lists of different
 * derived data would be used to render different graphs on UI. Moreover, that
 * single object will contain the data points corresponding to baseline reading
 * and will have the field for patient watchlist status as well. And, for that
 * purpose following query parameters are required to this lambda function:
 * 
 * patientId-To fetch derived data of the readings that patient took.
 * clinicianId-To identify if patient is there in clinician's watchlist or not.
 * startDate & endDate-If provided will filter the data for the date range.
 *
 * @author AyushRa
 */
public final class GetDerivedDataByPatientId implements RequestHandler<Map<String, String>, PatientRecordResponse> {
	private static final Logger LOGGER = LogManager.getLogger(GetDerivedDataByPatientId.class.getName());
	private PatientRecordResponse patientRecordResponse = new PatientRecordResponse();

	@Override
	public PatientRecordResponse handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(
				":::::::Request start to get the lists of different derived data for the readings that patient took:::::::");
		LOGGER.info("Input: " + input);

		Integer patientId = null;
		Integer clinicianId = null;
		Date startDate = null;
		Date endDate = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.INPUT_DATE_FORMAT);

			patientId = Integer.parseInt(input.get(Constants.QUERY_PARAM_PATIENT_ID));
			clinicianId = Integer.parseInt(input.get(Constants.QUERY_PARAM_CLINICIAN_ID));

			if (input.get(Constants.QUERY_PARAM_START_DATE) != null
					&& input.get(Constants.QUERY_PARAM_END_DATE) != null) {
				startDate = sdf.parse(input.get(Constants.QUERY_PARAM_START_DATE));
				endDate = sdf.parse(input.get(Constants.QUERY_PARAM_END_DATE));
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		DerivedDataService derivedDataService = new DerivedDataService();
		patientRecordResponse = derivedDataService.getDerivedDataByPatientId(patientId, startDate, endDate);

		PatientService patientService = new PatientService();
		boolean isPatientInClinicianWatchlist = patientService.checkPatientClinicianWatchListStatus(patientId,
				clinicianId);

		patientRecordResponse.setPatientInClinicianWatchlist(isPatientInClinicianWatchlist);

		LOGGER.info(
				":::::::Request completed to get the lists of different derived data for the readings that patient took:::::::");

		if (patientRecordResponse == null || patientRecordResponse.getReadingDate() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_DERIVED_DATA_BY_PATIENT_ID.name(), false);
		}

		return patientRecordResponse;
	}
}