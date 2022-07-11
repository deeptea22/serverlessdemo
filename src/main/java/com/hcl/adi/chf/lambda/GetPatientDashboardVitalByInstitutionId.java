package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.DashboardVitals;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return a patient dashboard vitals based on
 * institution id
 *
 * @author Sandeep Singh
 */
public final class GetPatientDashboardVitalByInstitutionId implements RequestHandler<Map<String, String>, DashboardVitals> {
	private static final Logger LOGGER = LogManager.getLogger(GetPatientDashboardVitalByInstitutionId.class.getName());

	@Override
	public DashboardVitals handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get the detail of vital by institution id:::::::");
		DashboardVitals data = new DashboardVitals();
		String institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);
		if (StringUtils.isNotBlank(institutionId)) {
			PatientService service = new PatientService();
			data.setInstitutionId(institutionId);
			data = service.getPatientDashboardVitalByInstitutionId(input, data);
		}

		LOGGER.info(":::::::Request completed to get the list of patient dashboard vital on institution id:::::::");

		if (data == null || data.getInstitutionId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_PATIENT_DASHBOARD_VITAL_BY_INSTITUTION_ID.name(), false);
		}

		return data;
	}
}