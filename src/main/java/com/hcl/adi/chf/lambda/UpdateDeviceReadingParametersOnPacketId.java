package com.hcl.adi.chf.lambda;

import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function is used to update the
 * device reading parameters like baseline status, active/Inactive
 * based on packet id and type of action
 * @author DivyaAg
 */
public class UpdateDeviceReadingParametersOnPacketId implements RequestHandler<Map<String, String>, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(UpdateDeviceReadingParametersOnPacketId.class.getName());
	private CustomResponse response = null;


	@Override
	public CustomResponse handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to discard device reading based on packet id:::::::");
		LOGGER.info("Input: " + input);
		int packetId = Integer.parseInt(input.get(Constants.QUERY_PARAM_PACKET_ID));
		String action = input.get(Constants.QUERY_PARAM_READING_ACTIONS);
		String updatedBy = input.get(Constants.QUERY_PARAM_UPDATED_BY);
		if(packetId > 0 && action != null) {
			ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
			response = clinicianDashboardServiceObj.updateDeviceReadingParameter(packetId, action, updatedBy);

		}
		
		LOGGER.info(":::::::Request completed to discard device reading based on packet id:::::::");

		if (Status.OK.getStatusCode() != response.getStatusCode()) {
			return ResponseGenerator.generateResponse(response, ApiErrorKey.UPDATE_DEVICE_READING_PARAMETER_ON_PACKET_ID.name(), true);
		}

		return response;
	}
}