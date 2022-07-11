package com.hcl.adi.chf.lambda;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.IADashboardResponse;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.service.InstitutionService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to provide all information related to
 * Institution Admin Dashboard by institution id
 *
 * @author AyushRa
 */
public final class GetIADashboardDataByInstitutionId
		implements RequestHandler<Map<String, Integer>, IADashboardResponse> {
	private static final Logger LOGGER = LogManager.getLogger(GetIADashboardDataByInstitutionId.class.getName());
	private IADashboardResponse iaDashboardResponse = new IADashboardResponse();
	private Institution institution = null;
	private List<Admins> adminList = null;
	private List<Clinician> clinicianList = null;

	@Override
	public IADashboardResponse handleRequest(final Map<String, Integer> input, final Context context) {
		try {
			LOGGER.info(
					":::::::Request start to get an institution admin dashboard data based on institution id:::::::");
			LOGGER.info("Input: " + input);

			//ObjectMapper mapper = new ObjectMapper();
			int institutionId = input.get(Constants.QUERY_PARAM_INSTITUTION_ID);
			InstitutionService institutionServiceObj = new InstitutionService();

			// Rest call for ADI module to get institution specific details - Lambda
			// Name(GetInstitutionDetailsByInstitutionId)
			// institution = (Institution)
			// RestClient.invokeGetAPI(Constants.URL_TO_GET_INSTITUTION_DETAILS_BY_INSTITUTION_ID,
			// input, Institution.class);

			institution = institutionServiceObj.getInstitutionDetailsByInstitutionId(institutionId);

			if (institution != null && institution.getInstitutionId() != null && institution.getInstitutionId() != 0) {
				iaDashboardResponse.setInstitution(institution);

				// Rest call for ADI module to get list of admins by institution id - Lambda
				// Name(GetAdminListByInstitutionId)
				// String adminListResponseJson =
				// RestClient.invokeGetAPI(Constants.URL_TO_GET_ADMIN_LIST_BY_INSTITUTION_ID,
				// input);
				AdminService adminServiceObj = new AdminService();
				adminList = adminServiceObj.getListOfAdminsByInstitutionId(institutionId);

				if (adminList != null) {
					// adminList = (List<Admins>)
					// Arrays.asList(mapper.readValue(adminListResponseJson, Admins[].class));
					iaDashboardResponse.setAdmins(adminList);
				}

				// String clinicianListResponseJson =
				// LambdaUtil.invokeLambda(Constants.CURRENT_REGION,
				// Constants.LAMBDA_GET_CLINICIAN_LIST_BY_INSTITUTION_ID,
				// JsonUtil.createInputPayload(input));
				ClinicianService clinicianServiceObj = new ClinicianService();
				clinicianList = clinicianServiceObj.getListOfCliniciansByInstitutionId(institutionId);
				if (clinicianList != null) {
					// clinicianList = (List<Clinician>)
					// Arrays.asList(mapper.readValue(clinicianListResponseJson,
					// Clinician[].class));
					iaDashboardResponse.setClinician(clinicianList);
				}

			}

			LOGGER.info(
					":::::::Response to return from GetIADashboardDataByInstitutionId:::::::" + iaDashboardResponse);
			LOGGER.info(
					":::::::Request completed to get an institution admin dashboard data based on institution id:::::::");

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		if (iaDashboardResponse == null || iaDashboardResponse.getInstitution() == null
				|| iaDashboardResponse.getInstitution().getInstitutionId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_IA_DASHBOARD_DATA_BY_INSTITUTION_ID.name(), false);
		}

		return iaDashboardResponse;
	}
}