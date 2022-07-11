package com.hcl.adi.chf.lambda;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.AdminService;
import com.hcl.adi.chf.util.JsonUtil;
import com.hcl.adi.chf.util.LambdaUtil;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be used to update the admin details
 *
 * @author AyushRa
 */
public final class UpdateAdmin implements RequestHandler<Admins, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateAdmin.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final Admins admin, final Context context) {
		LOGGER.info(":::::::Request start to update admin details:::::::");
		LOGGER.info("Input: " + admin);

		AdminService adminServiceObj = new AdminService();
		response = adminServiceObj.updateAdmin(admin);

		LOGGER.info(":::::::Request completed to update admin details:::::::");	
		/*
		 * Temperory solution for update profile log in institution side Later it will delete
		 * Remove Start
		 */
		if(response.getStatusCode() == 200 && "IA".equalsIgnoreCase(admin.getType())) {
		  temCreateLogInInstitutionSide(admin,Activity.INSTITUTION_ADMIN_PROFILE_UPDATED.toString());
		}		
		//Remove End
		
		return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_ADMIN.name(), true);
	}
	
	
	/*
	 * Temperory solution for add update profile log in institution side Later it will delete
	 */
	public void temCreateLogInInstitutionSide(Admins admin,String status) {
		
		try {
			
			String currentRegion = System.getenv("TEMP_AWS_REGION");
			String tempLambdaName = System.getenv("TEMP_LAMBDA_NAME");
			Map<String, String> payl = new HashMap<String, String>();			
			payl.put("institutionId",String.valueOf(admin.getInstitutionId()));			
			payl.put("userType",admin.getType());
			payl.put("activity",status);
			payl.put("createdBy",admin.getCreatedBy());
			
			String value = (String)LambdaUtil.invokeLambda(Regions.fromName(currentRegion), tempLambdaName, JsonUtil.createInputPayload(payl), String.class);
			LOGGER.info("Add Log status in institution db : "+value);
		}catch(Exception e) {
			LOGGER.info("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
	}
}