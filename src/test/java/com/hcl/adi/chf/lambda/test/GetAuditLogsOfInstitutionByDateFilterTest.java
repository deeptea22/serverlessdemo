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
import com.hcl.adi.chf.lambda.GetAuditLogsOfInstitutionByDateFilter;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.util.Constants;

public class GetAuditLogsOfInstitutionByDateFilterTest {
	private static final Logger LOGGER = LogManager.getLogger(GetAuditLogsOfInstitutionByDateFilterTest.class.getName());
	private static Map<String, String> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, String>();
		input.put(Constants.QUERY_PARAM_INSTITUTION_ID, "1");
		input.put(Constants.QUERY_PARAM_START_DATE, "2019-04-20");
		input.put(Constants.QUERY_PARAM_END_DATE, "2019-04-23");
		input.put(Constants.QUERY_PARAM_EMAIL_ID, "institutionadmin@max.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testGetAuditLogsOfInstitutionByDateFilter() {
		GetAuditLogsOfInstitutionByDateFilter handler = new GetAuditLogsOfInstitutionByDateFilter();
		Context ctx = createContext();

		List<AuditLog> auditLogList = handler.handleRequest(input, ctx);
		LOGGER.info(auditLogList);

		// validate output here.
		Assert.assertNotNull(auditLogList);
	}
}