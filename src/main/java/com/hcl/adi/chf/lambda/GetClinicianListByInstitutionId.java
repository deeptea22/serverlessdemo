package com.hcl.adi.chf.lambda;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.util.Constants;

/**
 * This lambda function will return the list of clinicians based on institution
 * id
 *
 * @author AyushRa
 */
public final class GetClinicianListByInstitutionId implements RequestHandler<Map<String, Integer>, List<Clinician>> {
	private static final Logger LOGGER = LogManager.getLogger(GetClinicianListByInstitutionId.class.getName());
	private List<Clinician> clinicianList = null;

	@Override
	public List<Clinician> handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get the list of clinicians based on institution id:::::::");
		LOGGER.info("Input: " + input);

		int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);

		ClinicianService clinicianServiceObj = new ClinicianService();
		clinicianList = clinicianServiceObj.getListOfCliniciansByInstitutionId(institutionId);

		LOGGER.info(":::::::Request completed to get the list of clinicians based on institution id:::::::");


		return clinicianList;
	}
}