package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateCompliancePolicy;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;

public class UpdateCompliancePolicyTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateCompliancePolicyTest.class.getName());
	private static CompliancePolicy compliancePolicy;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		compliancePolicy = new CompliancePolicy();
		compliancePolicy.setCompliancePeriod(1);
		compliancePolicy.setUpdatedBy("varshney2222@gmail.com");
		compliancePolicy.setPkId(1);
		compliancePolicy.setInstitutionId(12);
		compliancePolicy.setStatus("A");
		compliancePolicy.setCreatedBy("vartika");
		
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateCompliancePolicy() {
		UpdateCompliancePolicy handler = new UpdateCompliancePolicy();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(compliancePolicy, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}