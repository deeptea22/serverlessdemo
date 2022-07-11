package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.ContactUSService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be triggered when user want to contact ADI through
 * ContactUS screen
 *
 * @author Shivendra Singh
 */
public final class ContactUSRequest implements RequestHandler<ContactUS, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(ContactUSRequest.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final ContactUS contactUS, final Context context) {
		LOGGER.info(
				":::::::Request start to persist contact-us form's data and send email to adi support and specified CC:::::::");
		LOGGER.info("Input: " + contactUS);

		ContactUSService contactUSServiceObj = new ContactUSService();
		response = contactUSServiceObj.performContactUSRequest(contactUS);

		LOGGER.info(
				":::::::Request completed to persist contact-us form's data and send email to adi support and specified CC:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.CONTACT_US_REQUEST.name(), true);
	}
}