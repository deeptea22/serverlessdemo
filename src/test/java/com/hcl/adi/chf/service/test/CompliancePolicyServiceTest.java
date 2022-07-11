package com.hcl.adi.chf.service.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.CompliancePolicyService;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CompliancePolicyServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(CompliancePolicyServiceTest.class.getName());
	private static CompliancePolicy compliancePolicy;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		compliancePolicy = new CompliancePolicy();
		compliancePolicy.setPkId(1);
		compliancePolicy.setInstitutionId(6);
		
	}

    @Test
    public void getCompliancePolicy() {
    	CompliancePolicyService compliancePolicyService = new CompliancePolicyService();
    	CompliancePolicy output = compliancePolicyService.getCompliancePolicy(compliancePolicy.getInstitutionId());
        LOGGER.info(output);
		Assert.assertNotNull(output);
    }
    
    @Test
    public void updateCompliancePolicy() {
    	CompliancePolicyService compliancePolicyService = new CompliancePolicyService();
    	CustomResponse output = compliancePolicyService.updateCompliancePolicy(compliancePolicy);
        LOGGER.info(output);
		Assert.assertNotNull(output);
    }
    
    
}
