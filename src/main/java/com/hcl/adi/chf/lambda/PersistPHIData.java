package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will persist patient's mandatory information in DB
 *
 * @author AyushRa
 */
public final class PersistPHIData implements RequestHandler<PatientPhi, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(PersistPHIData.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final PatientPhi patientPhi, final Context context) {
		LOGGER.info(":::::::Request start for persist PHI data:::::::");

		PatientService patientServiceObj = new PatientService();
		customResponse = patientServiceObj.persistPatientData(patientPhi);

		LOGGER.info(":::::::Request completed for persist PHI data:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.PERSIST_PHI_DATA.name(), true);
	}
}