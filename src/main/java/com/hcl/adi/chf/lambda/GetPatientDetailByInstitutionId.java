package com.hcl.adi.chf.lambda;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.PatientRecord;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return a patient detail based by institution id
 *
 * @author Sandeep Singh
 */
public final class GetPatientDetailByInstitutionId implements RequestHandler<Map<String, String>, List<PatientRecord>> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientDetailByInstitutionId.class.getName());

	@Override
	public List<PatientRecord> handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get the list of patient based on institution id:::::::");
		
		List<PatientRecord> dataList = null;

		if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_INSTITUTION_ID))) {
			PatientService service = new PatientService();
			dataList = service.getPatientDetailByInstitutionId(input);
		}

		LOGGER.info(":::::::Request completed to get the list of patient based on institution id:::::::");

		if (dataList == null || dataList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_DETAIL_BY_INSTITUTION_ID.name(), false);
		}

		return dataList;
	}
}
