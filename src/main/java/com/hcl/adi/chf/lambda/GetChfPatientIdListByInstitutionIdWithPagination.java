package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the list of patientId, its attributes
 * and count of total patients of an institution from patient_phi table
 * based on institution id
 *
 * @author DivyaAg
 */
public final class GetChfPatientIdListByInstitutionIdWithPagination implements RequestHandler<Map<String, Integer>, ChfPatientListResponseForMobileApp> {
	private static final Logger LOGGER = LogManager.getLogger(GetChfPatientIdListByInstitutionIdWithPagination.class.getName());
	private ChfPatientListResponseForMobileApp chfPatientListResponseForMobileApp = new ChfPatientListResponseForMobileApp();

	@Override
	public ChfPatientListResponseForMobileApp handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get the list of patientId, chfPatientId, mrNumber and count of total patients from patient_phi table based on institution id :::::::");
		LOGGER.info("Input: " + input);

		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);
		int pageStartIndex = input.get(Constants.QUERY_PARAM_PAGE_START_INDEX);
		int pageCount = input.get(Constants.QUERY_PARAM_PAGE_COUNT);

		PatientService patientServiceObj = new PatientService();
		chfPatientListResponseForMobileApp = patientServiceObj.getChfPatientIdListBasedOnInstitutionId(institutionId, pageStartIndex, pageCount);

		LOGGER.info(":::::::Request completed to get the list of patientId, chfPatientId, mrNumber and count of total patients from patient_phi table based on institution id :::::::");

		if (chfPatientListResponseForMobileApp.getChfPatientIdList().isEmpty() && chfPatientListResponseForMobileApp.getPatientCount() == 0) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_CHF_PATIENT_ID_LIST_BY_INSTITUTION_ID_WITH_PAGINATION.name(), false);
		}

		return chfPatientListResponseForMobileApp;
	}
}