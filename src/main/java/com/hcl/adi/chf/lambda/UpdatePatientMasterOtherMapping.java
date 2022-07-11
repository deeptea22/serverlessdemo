package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientMasterMapping;
import com.hcl.adi.chf.service.PatientService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will Insert, Delete the All Medication, Comoborties, Patient History and there respective Other.
 *
 * @author Shivendra
 */
public final class UpdatePatientMasterOtherMapping implements RequestHandler<PatientMasterMapping,CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdatePatientMasterOtherMapping.class.getName());
	private CustomResponse response = null;
	
    @Override
    public final CustomResponse handleRequest(final PatientMasterMapping patientMasterOtherMapping, final Context context) {    	
    	LOGGER.info(":::::::Request start to update patient master other mappings:::::::");
		LOGGER.info("Input: " + patientMasterOtherMapping);
		
		PatientService patientService = new PatientService();
		response = patientService.updatePatientMasterOtherMapping(patientMasterOtherMapping);

		LOGGER.info(":::::::Request completed to uupdate patient master other mappings:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_PATIENT_MASTER_OTHER_MAPPING.name(), true);     
    }
    
  
}
