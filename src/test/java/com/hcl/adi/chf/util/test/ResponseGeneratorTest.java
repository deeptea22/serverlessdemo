package com.hcl.adi.chf.util.test;

import java.io.IOException;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.ResponseGenerator;

public class ResponseGeneratorTest {
	private static final Logger LOGGER = LogManager.getLogger(ResponseGeneratorTest.class.getName());
	private static CustomResponse customResponse = null;

	@BeforeClass
	public static void createInput() throws IOException {
		customResponse = new CustomResponse();

	}

	@Test
	public void generateResponseWithNull() {
		CustomResponse customResponse = ResponseGenerator.generateResponse(null, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void generateResponseNoContent() {
		customResponse.setStatusCode(Status.NO_CONTENT.getStatusCode());
		CustomResponse customResponse = ResponseGenerator.generateResponse(ResponseGeneratorTest.customResponse, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void generateResponseBadRequest() {
		customResponse.setStatusCode(Status.BAD_REQUEST.getStatusCode());
		CustomResponse customResponse = ResponseGenerator.generateResponse(ResponseGeneratorTest.customResponse, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void invokeLambdaReturnStringPreConditionFailed() {
		customResponse.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
		CustomResponse customResponse = ResponseGenerator.generateResponse(ResponseGeneratorTest.customResponse, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void generateResponse() {
		customResponse.setStatusCode(Status.ACCEPTED.getStatusCode());
		CustomResponse customResponse = ResponseGenerator.generateResponse(ResponseGeneratorTest.customResponse, null, false);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	
	@Test
	public void generateResponseWithNullCustom() {
		CustomResponse customResponse = ResponseGenerator.generateResponseCustom(null, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
	@Test
	public void generateResponseNoContentCustom() {
		customResponse.setStatusCode(Status.NO_CONTENT.getStatusCode());
		customResponse = ResponseGenerator.generateResponseCustom(ResponseGeneratorTest.customResponse, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void generateResponseBadRequestCustom() {
		customResponse.setStatusCode(Status.BAD_REQUEST.getStatusCode());
		customResponse = ResponseGenerator.generateResponseCustom(ResponseGeneratorTest.customResponse, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void invokeLambdaReturnStringPreConditionFailedCustom() {
		customResponse.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
		customResponse = ResponseGenerator.generateResponseCustom(ResponseGeneratorTest.customResponse, null, true);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void generateResponseCustom() {
		customResponse.setStatusCode(Status.ACCEPTED.getStatusCode());
		customResponse = ResponseGenerator.generateResponseCustom(ResponseGeneratorTest.customResponse, null, false);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}
}