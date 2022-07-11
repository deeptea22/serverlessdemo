package com.hcl.adi.chf.util;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import com.hcl.adi.chf.model.CustomResponse;

/**
 * This class will generate the response to be returned for UI. Either the same
 * customResponse would be returned or specified error would be thrown based on
 * condition
 *
 * @author AyushRa
 */
public final class ResponseGenerator {

	private ResponseGenerator() {
		// private constructor
	}

	/**
	 * This method will generate the response for UI. Either the same
	 * customResponse would be returned or specified error would be thrown based
	 * on condition
	 *
	 * @param customResponse
	 * @param invokedAPI
	 * @param isPostOrPutAPI
	 * @return
	 */
	public static CustomResponse generateResponse(final CustomResponse customResponse, final String invokedAPI,
			final boolean isPostOrPutAPI) {
		String message = null;
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("apiErrorKey", invokedAPI);

		if (isPostOrPutAPI) {
			if (customResponse == null
					|| Status.INTERNAL_SERVER_ERROR.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.INTERNAL_SERVER_ERROR.getStatusCode());
				errorData.put("description", Status.INTERNAL_SERVER_ERROR.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);

			} else if (Status.BAD_REQUEST.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.BAD_REQUEST.getStatusCode());
				errorData.put("description", Status.BAD_REQUEST.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);

			} else if (Status.PRECONDITION_FAILED.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.PRECONDITION_FAILED.getStatusCode());
				errorData.put("description", Status.PRECONDITION_FAILED.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);
				
			} else if (Status.NO_CONTENT.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.NO_CONTENT.getStatusCode());
				errorData.put("description", Status.NO_CONTENT.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);
			}

		} else {
			errorData.put("statusCode", Status.NO_CONTENT.getStatusCode());
			errorData.put("description", Status.NO_CONTENT.toString());

			message = JsonUtil.javaObjectToJsonString(errorData);
		}

		if (message == null) {
			return customResponse;
		} else {
			throw new RuntimeException(message);
		}
	}
	
	/**
	 * This method will generate the response for UI. Either the same
	 * customResponse would be returned or specified error would be thrown based
	 * on condition
	 *
	 * @param customResponse
	 * @param invokedAPI
	 * @param isPostOrPutAPI
	 * @return
	 */
	public static <T extends CustomResponse> T  generateResponseCustom(final T customResponse, final String invokedAPI,
			final boolean isPostOrPutAPI) {
		String message = null;
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("apiErrorKey", invokedAPI);

		if (isPostOrPutAPI) {
			if (customResponse == null
					|| Status.INTERNAL_SERVER_ERROR.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.INTERNAL_SERVER_ERROR.getStatusCode());
				errorData.put("description", Status.INTERNAL_SERVER_ERROR.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);

			} else if (Status.BAD_REQUEST.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.BAD_REQUEST.getStatusCode());
				errorData.put("description", Status.BAD_REQUEST.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);

			} else if (Status.PRECONDITION_FAILED.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.PRECONDITION_FAILED.getStatusCode());
				errorData.put("description", Status.PRECONDITION_FAILED.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);
				
			} else if (Status.NO_CONTENT.getStatusCode() == customResponse.getStatusCode()) {
				errorData.put("statusCode", Status.NO_CONTENT.getStatusCode());
				errorData.put("description", Status.NO_CONTENT.toString());

				message = JsonUtil.javaObjectToJsonString(errorData);
			}

		} else {
			errorData.put("statusCode", Status.NO_CONTENT.getStatusCode());
			errorData.put("description", Status.NO_CONTENT.toString());

			message = JsonUtil.javaObjectToJsonString(errorData);
		}

		if (message == null) {
			return customResponse;
		} else {
			throw new RuntimeException(message);
		}
	}
}