package com.hcl.adi.chf.service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.OrganizationDao;
import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.enums.Organization_Sub_Type;
import com.hcl.adi.chf.enums.Organization_Type;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Organization;
import com.hcl.adi.chf.util.Constants;

/**
 * Service class for lambda functions related to Organization
 *
 * @author SandeepSingh
 */
public class OrganizationService {
	private static final Logger LOGGER = LogManager.getLogger(OrganizationService.class.getName());

	/**
	 * This method will invoke OrganizationDao to get organization list
	 *
	 * @return
	 */
	public final List<Organization> getOrganizations() {
		List<Organization> institutionList = null;

		try {
			institutionList = OrganizationDao.fetchOrganizationList();

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institutionList;
	}

	/**
	 * This method will invoke OrganizationDao to get the list of all activated
	 * institution
	 *
	 * @return
	 */
	public final List<Organization> getActivatedOrganizations() {
		List<Organization> institutionList = null;

		try {
			institutionList = OrganizationDao.fetchAllActivatedOrganizationList();

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institutionList;
	}

	/**
	 * This method will invoke OrganizationDao to get an organization, its pwd
	 * policy, its data archival policy and its terms and conditions by
	 * institution id
	 *
	 * @param organizationId
	 * @return
	 */
	public final Organization getOrganizationDetailsByOrganizationId(final int organizationId) {
		Organization institution = null;

		try {
			institution = OrganizationDao.fetchOrganizationDetailsBasedOnOrganizationID(organizationId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institution;
	}

	/**
	 * This method will invoke OrganizationDao to update an institution detail
	 *
	 * @param institution
	 * @return
	 */
	public final CustomResponse updateOrganization(final Organization organization) {
		CustomResponse response = new CustomResponse();

		try {
			if (organization != null && organization.getAction() != null) {
				response = OrganizationDao.updateOrganization(organization, organization.getAction());

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();

					if (Actions.DEACTIVATE.name().equalsIgnoreCase(organization.getAction())) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_ORGANIZATION_ID, AdminType.SA.name(),
								Activity.ORGANIZATION_DEACTIVATED.name(), organization.getUpdatedBy());
					} else if (Actions.ACTIVATE.name().equalsIgnoreCase(organization.getAction())) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_ORGANIZATION_ID, AdminType.SA.name(),
								Activity.ORGANIZATION_ACTIVATED.name(), organization.getUpdatedBy());
					} else if (Actions.DELETE.name().equalsIgnoreCase(organization.getAction())) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_ORGANIZATION_ID, AdminType.SA.name(),
								Activity.ORGANIZATION_DELETED.name(), organization.getUpdatedBy());
					} else {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_ORGANIZATION_ID, AdminType.SA.name(),
								Activity.ORGANIZATION_UPDATED.name(), organization.getUpdatedBy());
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
	 * This method will invoke OrganizationDao to create an organization in DB
	 *
	 * @param organization
	 * @return
	 */
	public final CustomResponse createOrganization(final Organization organization) {
		CustomResponse response = new CustomResponse();

		try {
			if (organization != null && EnumUtils.isValidEnum(Organization_Type.class, organization.getOrganizationType()) && EnumUtils.isValidEnum(Organization_Sub_Type.class, organization.getOrganizationSubType())) {
				response = OrganizationDao.insertRecordIntoOrganization(organization);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.insertRecordIntoAuditLogs(Constants.SA_ORGANIZATION_ID, AdminType.SA.name(),
							Activity.ORGANIZATION_CREATED.name(), organization.getCreatedBy());
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
	 * This method will invoke OrganizationDao to get an organization, its pwd
	 * policy, its data archival policy and its terms and conditions by
	 * institution id
	 *
	 * @param organizationId
	 * @return
	 */
	public final List<Organization> getOrganizationListByType(final String organizationType) {
		List<Organization> organizationList = null;

		try {
			if (organizationType != null && EnumUtils.isValidEnum(Organization_Type.class, organizationType)) {
				organizationList = OrganizationDao.getOrganizationListByType(organizationType);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return organizationList;
	}
	
	/**
	 * This method will invoke OrganizationDao to map an Organization,partner organization and provider organization in CPM system
	 *
	 * @param organization
	 * @return
	 */
	public final CustomResponse mapOrgnizationToPartnerOrProvider(final Organization organization) {
		CustomResponse response = new CustomResponse();

		try {
			if (organization != null && EnumUtils.isValidEnum(Organization_Type.class, organization.getMapOrganizationType())) {
				
				response = OrganizationDao.mapOrgnizationToPartnerOrProvider(organization);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();
					auditLogService.insertRecordIntoAuditLogs(Constants.SA_ORGANIZATION_ID, AdminType.SA.name(),
							Activity.ORGANIZATION_MAPPED_TO_PROVIDER_OR_PARTNER.name(), organization.getCreatedBy());
					
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