package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.service.ClinicianDashboardService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function is used to fetch ECg and heartsound url 
 * details of a particular reading using packet id
 * 
 * @author DivyaAg
 */
public final class GetReadingDataByPacketId implements RequestHandler<Map<String, Integer>, DeviceData> {
	private static final Logger LOGGER = LogManager.getLogger(GetReadingDataByPacketId.class.getName());
	private DeviceData deviceData = null;


	@Override
	public DeviceData handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get device data of a particular deivce reading  based on packet id:::::::");
		LOGGER.info("Input: " + input);

		int packetId = input.get(Constants.QUERY_PARAM_PACKET_ID);
		
		
		ClinicianDashboardService clinicianDashboardServiceObj = new ClinicianDashboardService();
		deviceData = clinicianDashboardServiceObj.getDeviceDataDetailsByPacketId(packetId);

		LOGGER.info(":::::::Request completed to get device data of a particular deivce reading  based on packet id:::::::");
		
		if (deviceData.getHeartSoundUrl() == null) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_AVAILABLE_READING_DATA_BY_PACKET_ID.name(), false);
		}

		return deviceData;
	}
}