package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateDeviceReadingParametersOnPacketId;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UpdateDeviceReadingParametersOnPacketIdTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateDeviceReadingParametersOnPacketIdTest.class.getName());
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_PACKET_ID, "1");
		
		input.put(Constants.QUERY_PARAM_READING_ACTIONS, "markAsBaseline");
		input.put(Constants.QUERY_PARAM_UPDATED_BY, "cliniican@hcl.com");
	
		
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateDeviceReadingParametersOnPacketId() {
		UpdateDeviceReadingParametersOnPacketId handler = new UpdateDeviceReadingParametersOnPacketId();
		Context ctx = createContext();

		CustomResponse response = handler.handleRequest(input, ctx);
		LOGGER.info(response);

		// validate output here.
		Assert.assertNotNull(response);
	}
}