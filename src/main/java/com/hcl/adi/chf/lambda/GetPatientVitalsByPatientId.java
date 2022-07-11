package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the latest vitals of specified patient from
 * patient_vitals table
 *
 * @author DivyaAg
 */
public final class GetPatientVitalsByPatientId implements RequestHandler<Map<String, Integer>, PatientVitals> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientVitalsByPatientId.class.getName());
	private PatientVitals patientVitals = null;

	@Override
	public PatientVitals handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get latest vitals of specified patient:::::::");
		LOGGER.info("Input: " + input);

		
		
		if(input.get(Constants.QUERY_PARAM_PATIENT_ID) != null){
			int patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);
			ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
			patientVitals = clinicianDashboardServiceObj.getPatientVitalsByPatientId(patientId);

		}
		
		if (patientVitals.getCreatedBy() == null && patientVitals.getReadingDate() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_VITALS_BY_PATIENT_ID.name(), false);
		}

		LOGGER.info(":::::::Request completed to get latest vitals of specified patient:::::::");

		return patientVitals;
	}
}