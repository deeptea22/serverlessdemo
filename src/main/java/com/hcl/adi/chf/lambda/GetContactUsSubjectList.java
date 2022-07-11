package com.hcl.adi.chf.lambda;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.service.ContactUSService;
import com.hcl.adi.chf.util.ResponseGenerator;
/**
 * This lambda function will be used to get the subjects list on
 * contact us page
 *
 * @author DivyaAg
 */

public final class GetContactUsSubjectList implements RequestHandler<Map<String, Integer>, Map<String,List<ContactUS>>> {
	private static final Logger LOGGER = LogManager.getLogger(GetHelpCenterPageTextList.class.getName());
	private Map<String,List<ContactUS>> contactUsSubjectListMap = null;

	@Override
	public Map<String,List<ContactUS>> handleRequest(final Map<String, Integer> input, final Context context) {
		LOGGER.info(":::::::Request start to get the list of available subjects for contact us page:::::::");
		LOGGER.info("Input: " + input);

		ContactUSService contactUSServiceObj = new ContactUSService();
		contactUsSubjectListMap = contactUSServiceObj.getContactUsSubjectList();
		
		LOGGER.info(":::::::Request completed to get the list of avilable subjects for contact us page:::::::");

		if (contactUsSubjectListMap.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_CONTACT_US_SUBJECT_LIST.name(), false);
		}
		
		
		return contactUsSubjectListMap;
	}
}