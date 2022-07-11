package com.hcl.adi.chf.util;

import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

/**
 * This utility class will be used to perform AWS lambda related operations
 *
 * @author AyushRa
 */
public final class LambdaUtil {
	private static final Logger LOGGER = LogManager.getLogger(LambdaUtil.class.getName());

	private LambdaUtil() {
		// private constructor
	}

	/**
	 * This method will invoke specified lambda function of specified region.
	 * And, will return the output json as specified class object
	 *
	 * @param region
	 * @param lambdaFunctionName
	 * @param payload
	 * @param responseObjType
	 * @return
	 */
	public static Object invokeLambda(final Regions region, final String lambdaFunctionName, final String payload,
			final Class<?> classObj) {
		Object object = JsonUtil.jsonStringToJavaObject(invokeLambda(region, lambdaFunctionName, payload), classObj);
		return object;
	}

	/**
	 * This method will invoke specified lambda function of specified region.
	 * And, will return the output json as string
	 *
	 * @param region
	 * @param lambdaFunctionName
	 * @param payload
	 * @return
	 */
	public static String invokeLambda(final Regions region, final String lambdaFunctionName, final String payload) {
		String responseJson = null;

		try {
			AWSLambda awsLambda = AWSLambdaClientBuilder.standard().withRegion(region).build();

			InvokeRequest invokeRequest = new InvokeRequest().withFunctionName(lambdaFunctionName).withPayload(payload);
			InvokeResult invokeResult = awsLambda.invoke(invokeRequest);

			ByteBuffer byteBuffer = invokeResult.getPayload();
			responseJson = new String(byteBuffer.array(), "UTF-8");

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return responseJson;
	}
}