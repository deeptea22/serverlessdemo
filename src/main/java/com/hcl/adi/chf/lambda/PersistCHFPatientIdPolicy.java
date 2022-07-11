package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.ChfPatientIdPolicyService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to persist chf patient id policy in DB
 *
 * @author AyushRa
 */
public final class PersistCHFPatientIdPolicy implements RequestHandler<ChfPatientIdPolicy, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(PersistCHFPatientIdPolicy.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final ChfPatientIdPolicy chfPatientIdPolicy, final Context context) {
		LOGGER.info(":::::::Request start to persist chf patient id policy in DB:::::::");
		LOGGER.info("Input: " + chfPatientIdPolicy);

		ChfPatientIdPolicyService chfPatientIdPolicyServiceObj = new ChfPatientIdPolicyService();
		response = chfPatientIdPolicyServiceObj.persistChfPatientIdPolicy(chfPatientIdPolicy);

		LOGGER.info(":::::::Request completed to persist chf patient id policy in DB:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.PERSIST_CHF_PATIENT_ID_POLICY.name(), true);
	}
}