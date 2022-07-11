package com.hcl.adi.chf.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

/**
 * This utility class will be used to send email through Amazon SES service
 *
 * @author AyushRa
 */
public final class SESUtil {
	private static final Logger LOGGER = LogManager.getLogger(SESUtil.class.getName());

	private SESUtil() {
		// private constructor
	}

	/**
	 * This method will send the email to specified emailID with specified subject
	 * and mailBody
	 *
	 * @param from
	 *            - This address must be verified with Amazon SES
	 * @param to
	 *            - if your account is still in the sandbox, to address must also be
	 *            verified
	 * @param cc
	 *            - if your account is still in the sandbox, cc address must also be
	 *            verified
	 * @param subject
	 * @param mailBody
	 */
	public static String sendEmailToSpecifiedEmailID(final String from, final String to, final String cc,
			final String subject, final String mailBody) {

		try {
			AmazonSimpleEmailService sesClient = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Constants.EMAIL_REGION).build();

			SendEmailRequest sendEmailRequest = null;

			if (cc != null && !cc.isEmpty()) {
				LOGGER.info("Email sent to: " + to + " with mail content as: " + mailBody + " where " + cc
						+ "marked in cc");

				sendEmailRequest = new SendEmailRequest()
						.withDestination(new Destination().withToAddresses(to).withCcAddresses(cc))
						.withMessage(new Message()
								.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(mailBody))
										.withText(new Content().withCharset("UTF-8").withData(mailBody)))
								.withSubject(new Content().withCharset("UTF-8").withData(subject)))
						.withSource(from);
			} else {
				LOGGER.info("Email sent to: " + to + " with mail content as: " + mailBody);

				sendEmailRequest = new SendEmailRequest().withDestination(new Destination().withToAddresses(to))
						.withMessage(new Message()
								.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(mailBody))
										.withText(new Content().withCharset("UTF-8").withData(mailBody)))
								.withSubject(new Content().withCharset("UTF-8").withData(subject)))
						.withSource(from);
			}

			SendEmailResult sendEmailResult = sesClient.sendEmail(sendEmailRequest);

			if (sendEmailResult != null && sendEmailResult.getMessageId() != null) {
				return sendEmailResult.getMessageId();
			} else {
				return null;
			}

		} catch (Exception e) {
			LOGGER.error("Unable to sent email. Error message: " + e.getMessage());
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

			return null;
		}
	}
}