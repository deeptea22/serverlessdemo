package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will persist patient's mandatory information in DB
 *
 * @author Ravi
 */
public final class PersistPHIDataFromCSV implements RequestHandler<Map<String, String>, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(PersistPHIData.class.getName());
	private CustomResponse customResponse = null;

	@Override
	public CustomResponse handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start for bulk persist PHI data:::::::\n Input objectKey::"
				+ input.get(Constants.FILE_OBJECT_KEY) + " createdBy::" + input.get(Constants.CREATED_BY)
				+ " institutionId::" + input.get(Constants.QUERY_PARAM_INSTITUTION_ID));

		PatientService patientServiceObj = new PatientService();
		customResponse = patientServiceObj.persistPatientDataFromCSV(input);

		LOGGER.info(":::::::Request completed for bulk persist PHI data:::::::");

		return ResponseGenerator.generateResponse(customResponse, ApiErrorKey.PERSIST_PHI_DATA.name(), true);
	}
}