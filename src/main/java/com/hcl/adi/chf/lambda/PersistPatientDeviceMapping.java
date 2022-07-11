package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientDeviceMapping;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to add patient and device mapping in
 * patient_device_mapping table
 *
 * @author DivyaAg
 */
public final class PersistPatientDeviceMapping implements RequestHandler<PatientDeviceMapping, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(PersistPatientDeviceMapping.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final PatientDeviceMapping pdm, final Context context) {
		LOGGER.info(":::::::Request start to persist patient and device mapping:::::::");

		
		PatientService patientServiceObj = new PatientService();
		customResponse = patientServiceObj.persistPatientDeviceMapping(pdm);

		LOGGER.info(":::::::Request completed to persist patient and device mapping:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.PERSIST_PATIENT_DEVICE_MAPPING.name(), true);
	}
}