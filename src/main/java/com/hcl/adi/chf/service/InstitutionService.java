package com.hcl.adi.chf.service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.InstitutionDao;
import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.util.Constants;

/**
 * Service class for lambda functions related to Institution
 *
 * @author AyushRa
 */
public class InstitutionService {
	private static final Logger LOGGER = LogManager.getLogger(InstitutionService.class.getName());

	/**
	 * This method will invoke InstitutionDao to get institution list
	 *
	 * @return
	 */
	public final List<Institution> getInstitutions() {
		List<Institution> institutionList = null;

		try {
			institutionList = InstitutionDao.fetchInstitutionList();

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institutionList;
	}

	/**
	 * This method will invoke InstitutionDao to get the list of all activated
	 * institution
	 *
	 * @return
	 */
	public final List<Institution> getActivatedInstitutions() {
		List<Institution> institutionList = null;

		try {
			institutionList = InstitutionDao.fetchAllActivatedInstitutionList();

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institutionList;
	}

	/**
	 * This method will invoke InstitutionDao to get an institution, its pwd
	 * policy, its data archival policy and its terms and conditions by
	 * institution id
	 *
	 * @param institutionId
	 * @return
	 */
	public final Institution getInstitutionDetailsByInstitutionId(final int institutionId) {
		Institution institution = null;

		try {
			institution = InstitutionDao.fetchInstitutionDetailsBasedOnInstitutionID(institutionId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institution;
	}

	/**
	 * This method will invoke InstitutionDao to update an institution detail
	 *
	 * @param institution
	 * @return
	 */
	public final CustomResponse updateInstitution(final Institution institution) {
		CustomResponse response = new CustomResponse();

		try {
			if (institution != null && institution.getAction() != null) {
				response = InstitutionDao.updateInstitution(institution, institution.getAction());

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();

					if (Actions.DEACTIVATE.name().equalsIgnoreCase(institution.getAction())) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
								Activity.INSTITUTION_DEACTIVATED.name(), institution.getUpdatedBy());
					} else if (Actions.ACTIVATE.name().equalsIgnoreCase(institution.getAction())) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
								Activity.INSTITUTION_ACTIVATED.name(), institution.getUpdatedBy());
					} else if (Actions.DELETE.name().equalsIgnoreCase(institution.getAction())) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
								Activity.INSTITUTION_DELETED.name(), institution.getUpdatedBy());
					} else {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
								Activity.INSTITUTION_UPDATED.name(), institution.getUpdatedBy());
					}
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

	/**
	 * This method will invoke InstitutionDao to create an institution in DB
	 *
	 * @param institution
	 * @return
	 */
	public final CustomResponse createInstitution(final Institution institution) {
		CustomResponse response = new CustomResponse();

		try {
			if (institution != null) {
				response = InstitutionDao.insertRecordIntoInstitution(institution);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
							Activity.INSTITUTION_CREATED.name(), institution.getCreatedBy());
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