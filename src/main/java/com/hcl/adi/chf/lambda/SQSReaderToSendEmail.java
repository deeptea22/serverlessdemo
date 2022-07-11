package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.service.SQSReaderService;

/**
 * This lambda function will be triggered through cloudwatch cron job and it
 * will fetch the messages(if any) from configured SQS and from each message it
 * will fetch the emailID and pwdExpiryDate to send email
 *
 * @author DivyaAg
 */
public final class SQSReaderToSendEmail implements RequestHandler<Object, String> {
	private static final Logger LOGGER = LogManager.getLogger(SQSReaderToSendEmail.class.getName());

	@Override
	public String handleRequest(final Object input, final Context context) {
		LOGGER.info(
				":::::SQSReaderToSendEmail lambda function triggered through cloudwatch cron job to fetch messages from SQS and to send email:::::");

		SQSReaderService sqsReaderServiceObj = new SQSReaderService();
		sqsReaderServiceObj.readMessagesFromSQSAndSendEmail();

		LOGGER.info(
				":::::SQSReaderToSendEmail lambda function completed which was triggered through cloudwatch cron job to fetch messages from SQS and to send email:::::");

		return "Success";
	}
}