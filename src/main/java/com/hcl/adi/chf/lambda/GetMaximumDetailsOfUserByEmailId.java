package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.service.InstitutionService;
import com.hcl.adi.chf.service.MaxDetailService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return maximum possible details of an
 * user(admin/clinician) based on email id
 *
 * @author AyushRa
 */
public final class GetMaximumDetailsOfUserByEmailId implements RequestHandler<Map<String, String>, UserDetails> {
	private static final Logger LOGGER = LogManager.getLogger(GetMaximumDetailsOfUserByEmailId.class.getName());

	@Override
	public UserDetails handleRequest(final Map<String, String> input, final Context context) {
		UserDetails userDetails = null;
		ClinicianService clinicianServiceObj = null;

		LOGGER.info(":::::::Request start to get maximum possible details of an user(admin/clinician) based on email id:::::::");
		LOGGER.info("Input: " + input);
		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);

		clinicianServiceObj = new ClinicianService();
		Clinician clinician = clinicianServiceObj.getClinicianByEmailId(emailId);

		if (clinician != null && clinician.getEmailId() != null) {
			// Rest call for ADI module to get institution specific details - Lambda Name(GetInstitutionDetailsByInstitutionId)			
			InstitutionService institutionServiceObj = new InstitutionService();
			Institution institution = institutionServiceObj.getInstitutionDetailsByInstitutionId(clinician.getInstitutionId());
	
			if (institution != null && institution.getInstitutionName() != null) {				
				userDetails = clinicianServiceObj.getClinicianAndItsPwdExpiryInfoByEmailIdAndInstitutionSpecificParameters(emailId, institution.getPwdExpireInDays(), institution.getPwdExpiryNotificationStartInDays());
				mergeInstitutionDetailsWithUser(institution, userDetails);
				
			}

		} else {
			// Rest call for ADI module to get maximum details of admin by emailId - Lambda Name(GetMaximumDetailsOfAdminByEmailId)
			MaxDetailService maxDetailServiceObj = new MaxDetailService();
			userDetails = maxDetailServiceObj.getMaximumPossibleDetailsOfAdminByEmailId(emailId);
		}

		if (userDetails == null || userDetails.getEmailId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_MAXIMUM_DETAILS_OF_USER_BY_EMAIL_ID.name(), false);
		}

		LOGGER.info(":::::::Response to return from GetMaximumDetailsOfUserByEmailId:::::::" + userDetails);
		LOGGER.info(":::::::Request completed to get maximum possible details of an user(admin/clinician) based on email id:::::::");

		return userDetails;
	}

	private void mergeInstitutionDetailsWithUser(final Institution institution, final UserDetails userDetails) {
		if (institution != null && userDetails != null) {
			userDetails.setInstitutionName(institution.getInstitutionName());
			userDetails.setInstitutionAddress(institution.getAddress());
			userDetails.setPwdExpireInDays(institution.getPwdExpireInDays());
			userDetails.setPwdExpiryNotificationStartInDays(institution.getPwdExpiryNotificationStartInDays());

			userDetails.setPwdPolicy(institution.getPwdPolicy());
			userDetails.setTnc(institution.getTnc());
			userDetails.setDataArchivalPolicy(institution.getDataArchivalPolicy());
		}
	}
}