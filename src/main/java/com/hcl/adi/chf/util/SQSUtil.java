package com.hcl.adi.chf.util;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.hcl.adi.chf.model.CustomResponse;

/**
 * This utility class will perform operations on AWS SQS
 *
 * @author AyushRa
 */
public final class SQSUtil {
	private static final Logger LOGGER = LogManager.getLogger(SQSUtil.class.getName());
	private static AmazonSQS sqsClient = null;

	private SQSUtil() {
		// private constructor
	}

	/**
	 * This method will return the SQS URL to perform operations on the
	 * specified queue
	 *
	 * @param region
	 * @param queueName
	 * @return
	 */
	private static String getQueueURL(final Regions region, final String queueName) {
		sqsClient = AmazonSQSClientBuilder.standard().withRegion(region).build();
		String queueURL = sqsClient.getQueueUrl(queueName).getQueueUrl();
		return queueURL;
	}

	/**
	 * This method will send the input message into specified AWS SQS
	 *
	 * @param region
	 * @param queueName
	 * @param message
	 * @return
	 */
	public static CustomResponse sendMessageToSQS(final Regions region, final String queueName, final String message) {
		CustomResponse response = new CustomResponse();

		try {
			SendMessageRequest sendMessageRequest = new SendMessageRequest()
					.withQueueUrl(getQueueURL(region, queueName)).withMessageBody(message);

			LOGGER.info("SendMessageRequest: " + sendMessageRequest);

			sqsClient.sendMessage(sendMessageRequest);

			response.setStatusCode(Status.OK.getStatusCode());
			response.setDescription(Status.OK.toString());

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * This method will read the messages from specified AWS SQS and will return
	 * the list of messages
	 *
	 * @param region
	 * @param queueName
	 * @return
	 */
	public static List<Message> readMessagesFromSQS(final Regions region, final String queueName) {
		List<Message> messages = null;

		try {
			ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
					.withQueueUrl(getQueueURL(region, queueName)).withWaitTimeSeconds(20).withMaxNumberOfMessages(10);

			LOGGER.info("ReceiveMessageRequest: " + receiveMessageRequest);

			messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();

			LOGGER.info("Total messages read from the queue: " + messages.size());
			LOGGER.info("List of messages: " + messages);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return messages;
	}

	/**
	 * This method will return the body of the specified message
	 *
	 * @param message
	 * @return
	 */
	public static String getSQSMessageBody(final Message message) {
		if (message != null) {
			LOGGER.info("Message Body: " + message.getBody());
			return message.getBody();
		}

		return null;
	}

	/**
	 * This method will delete the specified message from the specified queue
	 *
	 * @param region
	 * @param queueName
	 * @param message
	 * @return
	 */
	public static void deleteSQSMessage(final Regions region, final String queueName, final Message message) {
		if (message != null) {
			sqsClient.deleteMessage(getQueueURL(region, queueName), message.getReceiptHandle());
		}
	}
}