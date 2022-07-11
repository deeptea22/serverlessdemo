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
import com.hcl.adi.chf.lambda.GetReadingDataByPacketId;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.util.Constants;

public class GetReadingDataByPacketIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetReadingDataByPacketIdTest.class.getName());
	private static Map<String, Integer> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, Integer>();
		input.put(Constants.QUERY_PARAM_PACKET_ID, 1);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetReadingDataByPacketId() {
		GetReadingDataByPacketId handler = new GetReadingDataByPacketId();
		Context ctx = createContext();

		DeviceData deviceData = handler.handleRequest(input, ctx);
		LOGGER.info(deviceData);

		// validate output here.
		Assert.assertNotNull(deviceData);
	}
}