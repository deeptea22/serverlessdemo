package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.InsertIntoAuditLogs;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.model.CustomResponse;

public class InsertIntoAuditLogsTest {
	private static final Logger LOGGER = LogManager.getLogger(InsertIntoAuditLogsTest.class.getName());
	private static AuditLog auditLog;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		auditLog = new AuditLog();
		auditLog.setInstitutionId(0);
		auditLog.setUserType("SA");
		auditLog.setActivity("PDF_DOWNLOADED");
		auditLog.setCreatedBy("superadmin@adi.com");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testInsertIntoAuditLogs() {
		InsertIntoAuditLogs handler = new InsertIntoAuditLogs();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(auditLog, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}