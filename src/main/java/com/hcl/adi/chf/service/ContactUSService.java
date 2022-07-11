package com.hcl.adi.chf.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.ContactUSDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.model.ContactUS;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.SESUtil;

/**
 * Service class for lambda functions related to contact us screen
 *
 * @author Shivendra Singh
 */
public class ContactUSService {
	private static final Logger LOGGER = LogManager.getLogger(ContactUSService.class.getName());

	/**
	 * This method will invoke SESUtil to send email to specified mailIds and
	 * will invoke ContactUSDao to persist contact-us form's data in DB
	 *
	 * @param contactUS
	 * @return
	 */
	public final CustomResponse performContactUSRequest(final ContactUS contactUS) {
		CustomResponse response = new CustomResponse();
		String sesMsgId = null;
		String customSubject = null;
		String customBody = null;

		try {
			if (contactUS != null) {
				customSubject = Constants.CONTACT_US_CUSTOM_SUBJECT;
				customBody = Constants.CONTACT_US_CUSTOM_BODY;

				if (customSubject != null) {
					customSubject = customSubject.replace(Constants.CONTACT_US_USERID_PREFIX, contactUS.getCreatedBy());
					customSubject = customSubject.replace(Constants.CONTACT_US_SUBJECT_PREFIX, contactUS.getSubject());
				} else {
					customSubject = contactUS.getSubject();
				}

				if (customBody != null) {
					customBody = customBody.replace(Constants.CONTACT_US_SUBJECT_PREFIX, contactUS.getSubject());
					customBody = customBody.replace(Constants.CONTACT_US_BODY_PREFIX, contactUS.getMessage());
				} else {
					customBody = contactUS.getMessage();
				}

				if (contactUS.getIsCopyRequested()) {
					sesMsgId = SESUtil.sendEmailToSpecifiedEmailID(Constants.CONTACT_US_FROM_EMAIL,
							Constants.CONTACT_US_TO_EMAIL, contactUS.getCcEmail(), customSubject, customBody);
				} else {
					sesMsgId = SESUtil.sendEmailToSpecifiedEmailID(Constants.CONTACT_US_FROM_EMAIL,
							Constants.CONTACT_US_TO_EMAIL, null, customSubject, customBody);
				}

				response = ContactUSDao.persistContactUSData(contactUS, sesMsgId);

				/**
				 * Now insert/send the record for corresponding audit_logs table
				 * based on user type
				 */
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					AuditLogService auditLogService = new AuditLogService();

					if (AdminType.SA.name().equalsIgnoreCase(contactUS.getUserType())) {
						auditLogService.insertRecordIntoAuditLogs(Constants.SA_INSTITUTION_ID, AdminType.SA.name(),
								Activity.CONTACT_US_REQUEST.name(), contactUS.getCreatedBy());
					} else {
						auditLogService.insertRecordIntoAuditLogs(contactUS.getInstitutionId(), contactUS.getUserType(),
								Activity.CONTACT_US_REQUEST.name(), contactUS.getCreatedBy());						
						//auditLogService.sendMessageToAuditLogQueue(contactUS.getInstitutionId(), contactUS.getUserType(), Activity.CONTACT_US_REQUEST.name(), contactUS.getCreatedBy());
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
	 * This method will invoke ContactUSDao to get list of available subjects for
	 * contact us page
	 *
	 * @return
	 */
	public final Map<String,List<ContactUS>>  getContactUsSubjectList() {
		Map<String,List<ContactUS>> contactUsSubjectListMap = new HashMap<String, List<ContactUS>>();
		List<ContactUS> subjectList = null;
		try {
			subjectList = ContactUSDao.fetchContactUsSubjectList();
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
		if(!subjectList.isEmpty()) {
			contactUsSubjectListMap.put("subjectList",subjectList);
		}
		return contactUsSubjectListMap;
	}
}


