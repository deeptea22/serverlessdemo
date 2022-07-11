package com.hcl.adi.chf.lambda;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Clinician;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.model.Tnc;
import com.hcl.adi.chf.model.UserDetails;
import com.hcl.adi.chf.service.ClinicianService;
import com.hcl.adi.chf.service.InstitutionService;
import com.hcl.adi.chf.service.MaxDetailService;
import com.hcl.adi.chf.util.CognitoUtil;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return tnc details only instead of maximum details
 * of an user(admin/clinician) based on email id
 *
 * @author Shivendra
 */
public final class GetTncDetailsByEmailId implements RequestHandler<Map<String, String>, Tnc> {
	private static final Logger LOGGER = LogManager.getLogger(GetTncDetailsByEmailId.class.getName());

	@Override
	public Tnc handleRequest(final Map<String, String> input, final Context context) {
		Tnc tnc = new Tnc();
		UserDetails userDetails = null;
		boolean isFirstLogin = false;
		boolean isPwdUpdated = false;

		LOGGER.info(":::::::Request start to get tnc details only of an user(admin/clinician) based on email id:::::::");
		LOGGER.info("Input: " + input);
		
		String emailId = input.get(Constants.QUERY_PARAM_EMAIL_ID);
		
		ClinicianService clinicianServiceObj = new ClinicianService();
		Clinician clinician = clinicianServiceObj.getClinicianByEmailId(emailId);

		if (clinician != null && clinician.getEmailId() != null) {
			// Rest call for ADI module to get institution specific details - Lambda Name(GetInstitutionDetailsByInstitutionId)
			
			InstitutionService institutionServiceObj = new InstitutionService();
			Institution institution = institutionServiceObj.getInstitutionDetailsByInstitutionId(clinician.getInstitutionId());

			if (institution != null && institution.getInstitutionName() != null) {
				if (clinician.getLastLoginTimestamp() == null) {
					isFirstLogin = true;
				}

				isPwdUpdated = !(Constants.COGNITO_POOL_USER_STATUS_FORCE_CHANGE_PASSWORD.equalsIgnoreCase(CognitoUtil.getUser(clinician.getEmailId()).getUserStatus()));

				tnc = setTncDetails(clinician.getEmailId(), clinician.getType(), clinician.getIsTncAccepted(), institution.getTnc(), isFirstLogin, isPwdUpdated);
			}

		} else {
			// Rest call for ADI module to get maximum details of admin by emailId - Lambda Name(GetMaximumDetailsOfAdminByEmailId)
			MaxDetailService maxDetailServiceObj = new MaxDetailService();
			userDetails = maxDetailServiceObj.getMaximumPossibleDetailsOfAdminByEmailId(emailId);

			if (userDetails != null && userDetails.getEmailId() != null) {
				if (userDetails.getLastLoginTimestamp() == null) {
					isFirstLogin = true;
				}

				isPwdUpdated = !(Constants.COGNITO_POOL_USER_STATUS_FORCE_CHANGE_PASSWORD.equalsIgnoreCase(CognitoUtil.getUser(userDetails.getEmailId()).getUserStatus()));

				tnc = setTncDetails(userDetails.getEmailId(), userDetails.getType(), userDetails.getIsTncAccepted(), userDetails.getTnc(), isFirstLogin, isPwdUpdated);
			}
		}

		if (tnc == null || tnc.getEmailId() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_TNC_DETAILS_BY_EMAIL_ID.name(), false);
		}

		LOGGER.info(":::::::Response to return from GetTncDetailsByEmailId:::::::" + tnc);
		LOGGER.info(":::::::Request completed to get tnc details only of an user(admin/clinician) based on email id:::::::");

		return tnc;
	}

	/**
	 * This method will set the required TNC details to be returned through API
	 *
	 * @param emailId
	 * @param userType
	 * @param isTncAccepted
	 * @param termsAndConditions
	 * @param isFirstLogin
	 * @param isPwdUpdated
	 * @return
	 */
	private final Tnc setTncDetails(final String emailId, final String userType, final String isTncAccepted,
			final TermsAndConditions termsAndConditions, final boolean isFirstLogin, final boolean isPwdUpdated) {
		Tnc tnc = new Tnc();

		tnc.setEmailId(emailId);
		tnc.setUserType(userType);
		tnc.setIsTncAccepted(isTncAccepted);
		tnc.setIsFirstLogin(isFirstLogin);
		tnc.setIsPwdUpdated(isPwdUpdated);

		if (termsAndConditions != null) {
			Date createdTimestamp = termsAndConditions.getCreatedTimestamp();
			Date updatedTimestamp = termsAndConditions.getUpdatedTimestamp();

			tnc.setTncText(termsAndConditions.getTncText());

			if ("N".equalsIgnoreCase(isTncAccepted) && !isFirstLogin) {
				tnc.setIsTncUpdated(updatedTimestamp.getTime() != createdTimestamp.getTime() ? "Y" : "N");
			} else {
				tnc.setIsTncUpdated("N");
			}
		}

		return tnc;
	}
}