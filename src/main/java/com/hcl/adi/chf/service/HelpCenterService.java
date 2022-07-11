package com.hcl.adi.chf.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.HelpCenterDao;
import com.hcl.adi.chf.model.HelpCenterPageText;
import com.hcl.adi.chf.model.HelpCenterResponse;

/**
 * Service class for lambda functions related to help center
 *
 * @author SandeepSingh
 */
public class HelpCenterService {
	private static final Logger LOGGER = LogManager.getLogger(HelpCenterService.class.getName());

	/**
	 * This method will invoke HelpCenterDao to get list of available contents for
	 * help center page
	 *
	 * @return
	 */
	public final HelpCenterResponse getHelpCenterPageTextList() {
		List<HelpCenterPageText> helpCenterPageTextList = null;
		HelpCenterResponse helpCenterResponse = new HelpCenterResponse();

		try {
			helpCenterPageTextList = HelpCenterDao.getHelpCenterPageTextList();
			helpCenterResponse.setHelpCenterList(helpCenterPageTextList);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return helpCenterResponse;
	}
}