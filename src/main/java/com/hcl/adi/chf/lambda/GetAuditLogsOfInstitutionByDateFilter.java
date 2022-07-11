package com.hcl.adi.chf.lambda;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.service.AuditLogService;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will return audit logs of specified institution based on
 * date filter
 *
 * @author DivyaAg
 */
public final class GetAuditLogsOfInstitutionByDateFilter implements RequestHandler<Map<String, String>, List<AuditLog>> {
	private static final Logger LOGGER = LogManager.getLogger(GetAuditLogsOfInstitutionByDateFilter.class.getName());
	private List<AuditLog> auditLogList = null;

	@Override
	public List<AuditLog> handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get audit logs of specified institution based on date filter:::::::");
		LOGGER.info("Input: " + input);

		Integer institutionId = null;
		Date startDate = null;
		Date endDate = null;
		String accessedBy = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.INPUT_DATE_FORMAT);

			institutionId = Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID));
			startDate = sdf.parse(input.get(Constants.QUERY_PARAM_START_DATE));
			endDate = sdf.parse(input.get(Constants.QUERY_PARAM_END_DATE));
			accessedBy = input.get(Constants.QUERY_PARAM_EMAIL_ID);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		AuditLogService auditLogServiceObj = new AuditLogService();
		auditLogList = auditLogServiceObj.getAuditLogsOfSpecifiedInstitutionByDateFilter(institutionId, startDate,
				endDate, accessedBy);

		LOGGER.info("Audit log list contains: " + auditLogList.size() + " record");
		LOGGER.info(":::::::Request completed to get audit logs of specified institution based on date filter:::::::");

		if (auditLogList == null || auditLogList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_AUDIT_LOGS_OF_INSTITUTION_BY_DATE_FILTER.name(), false);
		}

		return auditLogList;
	}
}