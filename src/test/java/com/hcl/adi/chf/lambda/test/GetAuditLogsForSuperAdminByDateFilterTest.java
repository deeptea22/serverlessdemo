package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetAuditLogsForSuperAdminByDateFilter;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.util.Constants;

public class GetAuditLogsForSuperAdminByDateFilterTest {
	private static final Logger LOGGER = LogManager.getLogger(GetAuditLogsForSuperAdminByDateFilterTest.class.getName());
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_START_DATE, "2019-04-22");
		input.put(Constants.QUERY_PARAM_END_DATE, "2019-04-24");
		input.put(Constants.QUERY_PARAM_EMAIL_ID, "superadmin@adi.com");
		input.put(Constants.QUERY_PARAM_LOG_TYPE, Constants.SUPER_ADMIN_ONLY_LOGS);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetAuditLogsOfInstitutionByDateFilter() {
		GetAuditLogsForSuperAdminByDateFilter handler = new GetAuditLogsForSuperAdminByDateFilter();
		Context ctx = createContext();

		List<AuditLog> auditLogList = handler.handleRequest(input, ctx);
		LOGGER.info(auditLogList);

		// validate output here.
		Assert.assertNotNull(auditLogList);
	}
}