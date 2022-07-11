package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientMasterMapping;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the All Patient History list with selected Admissions, Procedures, Implants and other Admissions, Implants (add from mobile app).
 *
 * @author Shivendra
 */
public class GetPatientHistoryList implements RequestHandler<Map<String, Integer>, PatientMasterMapping> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientHistoryList.class.getName());
	private PatientMasterMapping patientMasterMappings = null;
	
    @Override
    public final PatientMasterMapping handleRequest(final Map<String, Integer> input, final Context context) {
    	LOGGER.info(":::::::Request start to fetch patient history details based on Patient ID:::::::");
		LOGGER.info("Input: " + input);

		int patientId = input.get(Constants.QUERY_PARAM_PATIENT_ID);
		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);

		PatientService patientServiceObj = new PatientService();
		patientMasterMappings = patientServiceObj.getPatientHistoryList(institutionId, patientId);

		if (patientMasterMappings == null || patientMasterMappings.getPatientId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_HISTORY_LIST.name(),
					false);
		}
		LOGGER.info(":::::::Request end to fetch patient history details based on Patient ID:::::::");

		return patientMasterMappings;
    }

}
