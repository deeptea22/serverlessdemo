package com.hcl.adi.chf.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.sqs.model.Message;
import com.hcl.adi.chf.model.PwdExpiryInfo;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.JsonUtil;
import com.hcl.adi.chf.util.SESUtil;
import com.hcl.adi.chf.util.SQSUtil;

/**
 * Service class for lambda functions related to SQS Reader
 *
 * @author AyushRa
 */
public class SQSReaderService {
	private static final Logger LOGGER = LogManager.getLogger(SQSReaderService.class.getName());

	/**
	 * This method will invoke SQSUtil to read the messages from the queue and
	 * from each message it will fetch the emailID and pwdExpiryDate and will
	 * pass those values to SESUtil to send email
	 */
	public void readMessagesFromSQSAndSendEmail() {
		String mailContent = null;

		try {
			// Read messages from the queue
			List<Message> messages = SQSUtil.readMessagesFromSQS(Constants.CURRENT_REGION,
					Constants.QUEUE_NAME_FOR_PWD_EXPIRY_INFO);

			if (messages != null && !messages.isEmpty()) {
				for (Message message : messages) {
					// Fetch message body
					String messageBody = SQSUtil.getSQSMessageBody(message);

					PwdExpiryInfo pwdExpiryInfo = (PwdExpiryInfo) JsonUtil.jsonStringToJavaObject(messageBody,
							PwdExpiryInfo.class);

					mailContent = Constants.MAILBODY_STATIC + pwdExpiryInfo.getPwdExpiryDate();

					// Now, send email to the specified emailID
					SESUtil.sendEmailToSpecifiedEmailID(Constants.FROM, pwdExpiryInfo.getEmailId(), null,
							Constants.SUBJECT, mailContent);

					// Now, delete the message from the queue
					SQSUtil.deleteSQSMessage(Constants.CURRENT_REGION, Constants.QUEUE_NAME_FOR_PWD_EXPIRY_INFO,
							message);
				}
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
	}
}