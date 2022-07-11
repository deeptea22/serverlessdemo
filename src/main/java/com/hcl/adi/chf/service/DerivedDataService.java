package com.hcl.adi.chf.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.DerivedDataDao;
import com.hcl.adi.chf.model.PatientRecordResponse;

/**
 * Service class for lambda functions related to derived data
 *
 * @author AyushRa
 */
public class DerivedDataService {
	private static final Logger LOGGER = LogManager.getLogger(DerivedDataService.class.getName());

	/**
	 * This method will invoke DerivedDataDao to get the lists of different
	 * derived data for the readings that specified patient took along with data
	 * points corresponding to baseline reading in a single object named as
	 * PatientRecordResponse
	 *
	 * @param patientId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public final PatientRecordResponse getDerivedDataByPatientId(final Integer patientId, final Date startDate,
			final Date endDate) {
		PatientRecordResponse patientRecordResponse = null;

		try {
			patientRecordResponse = DerivedDataDao.fetchDerivedDataByPatientId(patientId, startDate, endDate);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

		}

		return patientRecordResponse;
	}
}