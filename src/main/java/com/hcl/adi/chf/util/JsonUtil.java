package com.hcl.adi.chf.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This utility class will be used to perform JSON related operations
 *
 * @author AyushRa
 */
public final class JsonUtil {
	private static final Logger LOGGER = LogManager.getLogger(JsonUtil.class.getName());

	private JsonUtil() {
		// private constructor
	}

	/**
	 * This method will return a JSON string corresponds to the input java
	 * object
	 *
	 * @param obj
	 * @return
	 */
	public static String javaObjectToJsonString(final Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String stringObj = null;

		try {
			stringObj = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return stringObj;
	}

	/**
	 * This method will map input JSON string to specified class object and will
	 * return that object
	 *
	 * @param stringObj
	 * @param classObj
	 * @return
	 */
	public static Object jsonStringToJavaObject(final String stringObj, final Class<?> classObj) {
		ObjectMapper mapper = new ObjectMapper();
		Object object = null;

		try {
			object = mapper.readValue(stringObj, classObj);
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return object;
	}

	/**
	 * This method will create and return a json string from specified
	 * parameters
	 *
	 * @param parametersMap
	 * @return
	 */
	public static String createInputPayload(final Map<String, String> parametersMap) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();

		if (parametersMap != null && !parametersMap.isEmpty()) {
			for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
				objectNode.put(entry.getKey(), entry.getValue());
			}
		}

		return objectNode.toString();
	}
	
	
	/**
	 * This method will map input JSON stream to a JSON that will have an array
	 * of jsons and will return that resulted json as Object
	 *
	 * @param inputStream
	 * @param obj
	 * @return
	 */
	public static Object jsonInputStreamToJavaObject(final InputStream inputStream,
			final TypeReference<List<Map<String, Object>>> typeRefObj) {
		ObjectMapper mapper = new ObjectMapper();
		Object object = null;

		try {
			object = mapper.readValue(inputStream, typeRefObj);
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return object;
	}
	
	/**
	 * Convert Map to jsonString
	 *
	 * @param inputStream
	 * @param obj
	 * @return
	 */
	public static String mapToJsonString(final Map<String, String> jsonMap) {
		ObjectMapper mapper = new ObjectMapper();
		 String json = StringUtils.EMPTY;

		try {
            json = mapper.writeValueAsString(jsonMap);
        }catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
		return json;
	}
}