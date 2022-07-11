package com.hcl.adi.chf.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.hcl.adi.chf.enums.ApiErrorKey;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.AuditLogService;
import com.hcl.adi.chf.util.ResponseGenerator;

/**
 * This lambda function will insert the record into audit_logs table
 *
 * @author AyushRa
 */
public final class InsertIntoAuditLogs implements RequestHandler<AuditLog, CustomResponse> {
	private static final Logger LOGGER = LogManager.getLogger(InsertIntoAuditLogs.class.getName());
	private CustomResponse response = null;

	@Override
	public CustomResponse handleRequest(final AuditLog auditLog, final Context context) {
		LOGGER.info(":::::::Request start to insert the record into audit_logs table:::::::");
		LOGGER.info("Input: " + auditLog);

		AuditLogService auditLogServiceObj = new AuditLogService();
		response = auditLogServiceObj.insertRecordIntoAuditLogs(auditLog);

		LOGGER.info(":::::::Request completed to insert the record into audit_logs table:::::::");

		return ResponseGenerator.generateResponse(response, ApiErrorKey.INSERT_INTO_AUDIT_LOGS.name(), true);
	}
}