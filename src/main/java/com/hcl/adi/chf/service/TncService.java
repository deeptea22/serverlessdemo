package com.hcl.adi.chf.service;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.TncDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.TermsAndConditions;

/**
 * Service class for lambda functions related to terms n conditions
 *
 * @author AyushRa
 */
public class TncService {
	private static final Logger LOGGER = LogManager.getLogger(TncService.class.getName());

	/**
	 * This method will invoke TncDao to update terms n conditions
	 *
	 * @param tnc
	 * @return
	 */
	public final CustomResponse updateTermsNConditions(final TermsAndConditions tnc) {
		CustomResponse response = new CustomResponse();

		try {
			if (tnc != null) {
				response = TncDao.updateTermsNConditions(tnc);

				/**
				 * Now invoke method that will fetch admin details by emaild and
				 * will insert/send the record for corresponding audit_logs
				 * table based on admin type
				 */
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(tnc.getUpdatedBy(),
							Activity.TNC_UPDATED.name());
				}
			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			response.setDescription(Status.BAD_REQUEST.toString() + ": " + e.getMessage());
		}

		return response;
	}
}