package com.hcl.adi.chf.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.Institution;

/**
 * This utility class will be used to perform Rest API calls
 *
 * @author AyushRa
 */
public final class RestClient {
	private static final Logger LOGGER = LogManager.getLogger(RestClient.class.getName());
	private static HttpClient httpclient = null;

	private RestClient() {
		// private constructor
	}

	/**
	 * This method will invoke get api with specified url & will pass specified
	 * parameters as query param. And, will return the output json as specified
	 * class object
	 *
	 * @param url
	 * @param parametersMap
	 * @param classObj
	 * @return
	 */
	public static Object invokeGetAPI(final String url, final Map<String, String> parametersMap,
			final Class<?> classObj) {
		Object object = JsonUtil.jsonStringToJavaObject(invokeGetAPI(url, parametersMap), classObj);
		return object;
	}

	/**
	 * This method will invoke get api with specified url & will pass specified
	 * parameters as query param. And, will return the output json as string
	 *
	 * @param url
	 * @param parametersMap
	 * @return
	 */
	public static String invokeGetAPI(final String url, final Map<String, String> parametersMap) {
		URIBuilder builder = null;
		HttpGet httpGet = null;
		HttpResponse httpResponse = null;
		String responseJson = null;

		try {
			httpclient = HttpClientBuilder.create().build();

			builder = new URIBuilder(url);

			if (parametersMap != null && !parametersMap.isEmpty()) {
				for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
					builder.setParameter(entry.getKey(), entry.getValue());
				}
			}

			httpGet = new HttpGet(builder.build());

			httpResponse = httpclient.execute(httpGet);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				responseJson = EntityUtils.toString(httpResponse.getEntity());
				LOGGER.info("Response returned from API: " + responseJson);

			} else {
				LOGGER.error("Response code returned from API: " + statusCode);
				LOGGER.error(httpResponse.getEntity().getContent());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		} finally {
			httpGet.releaseConnection();
		}

		return responseJson;
	}

	/**
	 * This method will invoke post api with specified url & will pass specified
	 * json as body. And, will return the output json as specified class object
	 *
	 * @param url
	 * @param jsonBody
	 * @param classObj
	 * @return
	 */
	public static Object invokePostAPI(final String url, final String jsonBody, final Class<?> classObj) {
		URIBuilder builder = null;
		HttpPost httpPost = null;
		HttpResponse httpResponse = null;
		Object object = null;
		Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
		// Header oAuthHeader = new BasicHeader("Authorization", "OAuth " + accessToken);

		try {
			httpclient = HttpClientBuilder.create().build();

			builder = new URIBuilder(url);
			httpPost = new HttpPost(builder.build());
			// httpPost.addHeader(oAuthHeader);
			httpPost.addHeader(contentTypeHeader);
			StringEntity entityBody = new StringEntity(jsonBody.toString());
			httpPost.setEntity(entityBody);

			httpResponse = httpclient.execute(httpPost);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				String responseString = EntityUtils.toString(httpResponse.getEntity());
				LOGGER.info("Response returned from API: " + responseString);

				object = JsonUtil.jsonStringToJavaObject(responseString, classObj);

			} else {
				LOGGER.error("Response code returned from API: " + statusCode);
				LOGGER.error(httpResponse.getEntity().getContent());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		} finally {
			httpPost.releaseConnection();
		}

		return object;
	}

	/**
	 * This method will invoke put api with specified url & will pass specified
	 * json as body. And, will return the output json as specified class object
	 *
	 * @param url
	 * @param jsonBody
	 * @param classObj
	 * @return
	 */
	public static Object invokePutAPI(final String url, final String jsonBody, final Class<?> classObj) {
		URIBuilder builder = null;
		HttpPut httpPut = null;
		HttpResponse httpResponse = null;
		Object object = null;
		Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
		// Header oAuthHeader = new BasicHeader("Authorization", "OAuth " + accessToken);

		try {
			httpclient = HttpClientBuilder.create().build();

			builder = new URIBuilder(url);
			httpPut = new HttpPut(builder.build());
			// httpPost.addHeader(oAuthHeader);
			httpPut.addHeader(contentTypeHeader);
			StringEntity entityBody = new StringEntity(jsonBody.toString());
			httpPut.setEntity(entityBody);

			httpResponse = httpclient.execute(httpPut);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				String responseString = EntityUtils.toString(httpResponse.getEntity());
				LOGGER.info("Response returned from API: " + responseString);

				object = JsonUtil.jsonStringToJavaObject(responseString, classObj);

			} else {
				LOGGER.error("Response code returned from API: " + statusCode);
				LOGGER.error(httpResponse.getEntity().getContent());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		} finally {
			httpPut.releaseConnection();
		}

		return object;
	}

	// Just to test the functionality
	public static void main(String[] args) {
		HashMap<String, String> parametersMap = new HashMap<String, String>();
		parametersMap.put(Constants.QUERY_PARAM_INSTITUTION_ID, "1");
		Institution institution = (Institution) invokeGetAPI(
				"https://trqummctb6.execute-api.us-east-1.amazonaws.com/dev/getInstitutionDetailsByInstitutionId",
				parametersMap, Institution.class);
		LOGGER.info(institution);
	}
}