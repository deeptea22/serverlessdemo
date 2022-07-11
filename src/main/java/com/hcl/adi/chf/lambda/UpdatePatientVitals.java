package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update the patient vitals based on
 * patient_vitals_id(PK)
 *
 * @author DivyaAg
 */
public final class UpdatePatientVitals implements RequestHandler<PatientVitals, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientVitals.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final PatientVitals patientVitals, final Context context) {
		LOGGER.info(":::::::Request start to update patient vitals based on patient_vitals_id(PK):::::::");
		LOGGER.info("Input: " + patientVitals);

		ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
		customResponse = clinicianDashboardServiceObj.updatePatientVitalsOnPatientVitalsId(patientVitals);

		LOGGER.info(":::::::Request completed to update patient vitals based on patient_vitals_id(PK):::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.UPDATE_PATIENT_VITALS.name(), true);
	}
}