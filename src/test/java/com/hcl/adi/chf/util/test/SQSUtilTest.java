package com.hcl.adi.chf.util.test;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.model.Message;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.SQSUtil;

public class SQSUtilTest {
	private static final Logger LOGGER = LogManager.getLogger(SQSUtilTest.class.getName());
	private static Message message = null;
	private static SQSEvent sqsEvent;
	private static String queName;

	@BeforeClass
	public static void createInput() throws IOException {
		message = new Message();
		sqsEvent = new SQSEvent();
		queName = "testQue";
	}

	@Test
	public void deleteSQSMessage() {
		SQSUtil.deleteSQSMessage(Constants.CURRENT_REGION, queName, message);
	}

	@Test
	public void getSQSMessageBody() {
		String messageBody = SQSUtil.getSQSMessageBody(message);
		LOGGER.info(messageBody);
		Assert.assertNotNull(messageBody);
	}

	@Test
	public void readMessagesFromSQS() {
		List<Message> messages = SQSUtil.readMessagesFromSQS(Constants.CURRENT_REGION, queName);
		LOGGER.info(messages);
		Assert.assertNotNull(messages);
	}

	@Test
	public void sendMessageToSQS() {
		CustomResponse response = SQSUtil.sendMessageToSQS(Constants.CURRENT_REGION, queName, "Test Message");
		LOGGER.info(response);
		Assert.assertNotNull(response);
	}
}