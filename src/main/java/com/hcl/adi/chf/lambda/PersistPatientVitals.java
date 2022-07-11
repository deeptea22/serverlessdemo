package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will persist patient vitals information in DB
 *
 * @author DivyaAg
 */
public final class PersistPatientVitals implements RequestHandler<PatientVitals, PatientVitals> {
	private static final Logger LOGGER = LogManager.getLogger(PersistPatientVitals.class.getName());
	private PatientVitals patientVitalsResponse = null;

	@Override
	public PatientVitals handleRequest(final PatientVitals patientVitals, final Context context) {
		LOGGER.info(":::::::Request start to persist patient vitals:::::::");
		LOGGER.info("Input: " + patientVitals);

		ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
		patientVitalsResponse = clinicianDashboardServiceObj.persistPatientVitals(patientVitals);

		LOGGER.info(":::::::Request completed to persist patient vitals:::::::");
		
		return ResponseGenerator.generateResponseCustom(patientVitalsResponse, ApiErrorKey.PERSIST_PATIENT_VITALS.name(), true);

	}
}