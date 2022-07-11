package com.hcl.adi.chf.lambda;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.HelpCenterResponse;
import com.hcl.adi.chf.service.HelpCenterService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will be triggered when user want to visit Help Center
 *
 * @author SandeepSingh
 */
public final class GetHelpCenterPageTextList implements RequestHandler<Map<String, Integer>, HelpCenterResponse> {
	private static final Logger LOGGER = LogManager.getLogger(GetHelpCenterPageTextList.class.getName());
	private HelpCenterResponse helpCenterResponse = null;

	@Override
	public HelpCenterResponse handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get the list of avilable content for help center page:::::::");
		LOGGER.info("Input: " + input);

		HelpCenterService helpCenterServiceObj = new HelpCenterService();
		helpCenterResponse = helpCenterServiceObj.getHelpCenterPageTextList();

		LOGGER.info(":::::::Request completed to get the list of avilable content for help center page:::::::");

		if (helpCenterResponse.getHelpCenterList() == null || helpCenterResponse.getHelpCenterList().isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_HELP_CENTER_PAGE_TEXT_LIST.name(), false);
		}

		return helpCenterResponse;
	}
}