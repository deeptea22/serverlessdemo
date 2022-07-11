package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.PersistCHFPatientIdPolicy;
import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.ChfPatientIdPolicy.Policy;
import com.hcl.adi.chf.model.CustomResponse;

public class PersistCHFPatientIdPolicyTest {
	private static final Logger LOGGER = LogManager.getLogger(PersistCHFPatientIdPolicyTest.class.getName());
	private static ChfPatientIdPolicy chfPatientIdPolicy;
	private static List<Policy> policyList;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		chfPatientIdPolicy = new ChfPatientIdPolicy();
		policyList = new ArrayList<ChfPatientIdPolicy.Policy>();

		chfPatientIdPolicy.setInstitutionId(1);
		chfPatientIdPolicy.setIsEMREHREnabled("N");
		chfPatientIdPolicy.setCreatedBy("ds1326@hcl.com");

		ChfPatientIdPolicy.Policy policy1 = new ChfPatientIdPolicy.Policy();
		policy1.setLabel("First Name");
		policy1.setLabelRule("Only first initial");
		policy1.setLabelSeparator("None");
		policy1.setLabelSequence((short) 1);

		ChfPatientIdPolicy.Policy policy2 = new ChfPatientIdPolicy.Policy();
		policy2.setLabel("Last Name");
		policy2.setLabelRule("Last four letters");
		policy2.setLabelSeparator("Hyphen");
		policy2.setLabelSequence((short) 2);

		ChfPatientIdPolicy.Policy policy3 = new ChfPatientIdPolicy.Policy();
		policy3.setLabel("Date of Birth");
		policy3.setLabelRule("MMDDYYYY");
		policy3.setLabelSeparator("None");
		policy3.setLabelSequence((short) 3);

		policyList.add(policy1);
		policyList.add(policy2);
		policyList.add(policy3);

		chfPatientIdPolicy.setPolicyList(policyList);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testPersistCHFPatientIdPolicy() {
		PersistCHFPatientIdPolicy handler = new PersistCHFPatientIdPolicy();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(chfPatientIdPolicy, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}