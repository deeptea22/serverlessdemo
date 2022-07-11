package com.hcl.adi.chf.lambda;

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
 * This lambda function will return CPM Trends of a particular patient by
 * patient id
 *
 * @author Sandeep Singh
 */
public final class GetCPMTrends implements RequestHandler<Map<String, String>, PatientRecord> {
	private static final Logger LOGGER = LogManager.getLogger(GetCPMTrends.class.getName());

	@Override
	public PatientRecord handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get the CPM trends based on patient id:::::::");
		PatientRecord data = null;

		if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_PATIENT_ID))) {
			PatientService service = new PatientService();
			data = service.getCPMTrends(input.get(Constants.QUERY_PARAM_PATIENT_ID));
		}

		LOGGER.info(":::::::Request completed to get the CPM trends based on patient id:::::::");

		if (data == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_CPM_TRENDS.name(), false);
		}

		return data;
	}
}