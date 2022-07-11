package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.model.ChfPatientSearchRequest;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return the list of patientId, chfPatientId,
 * mrNumber and count of total patients of an institution from patient_phi table
 * based on institution id and specific column(pattern based search)
 *
 * @author Shivendra
 */
public final class GetChfPatientIdListBySearchParamWithPagination implements RequestHandler<ChfPatientSearchRequest, ChfPatientListResponseForMobileApp> {
	private static final Logger LOGGER = LogManager.getLogger(GetChfPatientIdListBySearchParamWithPagination.class.getName());
	private ChfPatientListResponseForMobileApp chfPatientListResponseForMobileApp = new ChfPatientListResponseForMobileApp();

	@Override
	public ChfPatientListResponseForMobileApp handleRequest(final ChfPatientSearchRequest chfPatientSearchRequest, final Context context) {
		LOGGER.info(":::::::Request start to get the list of patientId, chfPatientId, mrNumber and count of total patients from patient_phi table based on institution id and specific column(pattern based search):::::::");
		LOGGER.info("Input: " + chfPatientSearchRequest);
		
		PatientService patientServiceObj = new PatientService();
		chfPatientListResponseForMobileApp = patientServiceObj.getChfPatientIdListBySearchParamWithPagination(chfPatientSearchRequest);

		LOGGER.info(":::::::Request completed to get the list of patientId, chfPatientId, mrNumber and count of total patients from patient_phi table based on institution id and specific column(pattern based search) :::::::");

		if (chfPatientListResponseForMobileApp.getChfPatientIdList().isEmpty() && chfPatientListResponseForMobileApp.getPatientCount() == 0) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_CHF_PATIENT_SEARCH_LIST_BY_INSTITUTION_ID_WITH_PAGINATION.name(), false);
		}

		return chfPatientListResponseForMobileApp;
	}
}