package com.hcl.adi.chf.util.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.util.JsonUtil;

public final class JsonUtilTest {
	private static final Logger LOGGER = LogManager.getLogger(JsonUtilTest.class.getName());

	private static String jsonString = null;
	private static List<Object> jsonObject = null;
	private static Map<String, String> payload = null;

	@BeforeClass
	public static void createInput() throws IOException {
		jsonString = "";
		jsonObject = new ArrayList<Object>();
		payload = new HashMap<String, String>();
	}

	@Test
	public void addUser() {
		String jsonString = JsonUtil.javaObjectToJsonString(jsonObject);
		LOGGER.info(jsonString);
		Assert.assertNotNull(jsonString);

	}

	@Test
	public void createInputPayload() {
		String jsonString = JsonUtil.createInputPayload(payload);
		LOGGER.info(jsonString);
		Assert.assertNotNull(jsonString);

	}

	@Test
	public void jsonStringToJavaObject() {
		Object jsonObject = JsonUtil.jsonStringToJavaObject(jsonString, Object.class);
		LOGGER.info(jsonObject);
		Assert.assertNotNull(jsonObject);
	}

	@Test
	public void addUserNull() {
		String jsonString = JsonUtil.javaObjectToJsonString(null);
		LOGGER.info(jsonString);
	}

	@Test
	public void createInputPayloadNull() {
		String jsonString = JsonUtil.createInputPayload(null);
		LOGGER.info(jsonString);

	}

	@Test
	public void jsonStringToJavaObjectNull() {
		Object jsonObject = JsonUtil.jsonStringToJavaObject(null, null);
		LOGGER.info(jsonObject);
	}
}