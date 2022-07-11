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
 * This lambda function will return audit logs for super admin based on date
 * filter
 *
 * @author AyushRa
 */
public class GetAuditLogsForSuperAdminByDateFilter implements RequestHandler<Map<String, String>, List<AuditLog>> {
	private static final Logger LOGGER = LogManager.getLogger(GetAuditLogsForSuperAdminByDateFilter.class.getName());
	private List<AuditLog> auditLogList = null;

	@Override
	public List<AuditLog> handleRequest(final Map<String, String> input, final Context context) {
		LOGGER.info(":::::::Request start to get audit logs for super admin based on date filter:::::::");
		LOGGER.info("Input: " + input);

		Date startDate = null;
		Date endDate = null;
		String accessedBy = null;
		String logType = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.INPUT_DATE_FORMAT);

			startDate = sdf.parse(input.get(Constants.QUERY_PARAM_START_DATE));
			endDate = sdf.parse(input.get(Constants.QUERY_PARAM_END_DATE));
			accessedBy = input.get(Constants.QUERY_PARAM_EMAIL_ID);
			logType = input.get(Constants.QUERY_PARAM_LOG_TYPE);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		AuditLogService auditLogServiceObj = new AuditLogService();
		auditLogList = auditLogServiceObj.getAuditLogsForSuperAdminByDateFilter(startDate, endDate, accessedBy, logType);

		LOGGER.info("Audit log list contains: " + auditLogList.size() + " record");
		LOGGER.info(":::::::Request completed to get audit logs for super admin based on date filter:::::::");

		if (auditLogList == null || auditLogList.isEmpty()) {
			ResponseGenerator.generateResponse(null, ApiErrorKey.GET_AUDIT_LOGS_FOR_SUPER_ADMIN_BY_DATE_FILTER.name(), false);
		}

		return auditLogList;
	}
}