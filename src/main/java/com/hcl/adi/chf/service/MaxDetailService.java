package com.hcl.adi.chf.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.MaxDetailDao;
import com.hcl.adi.chf.model.UserDetails;

/**
 * Service class for GetMaximumDetailsOfAdminByEmailId lambda function
 *
 * @author AyushRa
 */
public class MaxDetailService {
	private static final Logger LOGGER = LogManager.getLogger(MaxDetailService.class.getName());

	/**
	 * This method will invoke MaxDetailDao to get the maximum possible details
	 * of an admin based on email id
	 *
	 * @param emailId
	 * @return
	 */
	public final UserDetails getMaximumPossibleDetailsOfAdminByEmailId(final String emailId) {
		UserDetails userDetails = null;

		try {
			userDetails = MaxDetailDao.fetchMaximumPossibleDetailsOfAdminByEmailId(emailId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return userDetails;
	}
}