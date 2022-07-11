package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.ClearThresholdBreachCount;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdBreachRequest;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ClearThresholdBreachCountTest {

	private static final Logger LOGGER = LogManager.getLogger(ClearThresholdBreachCountTest.class.getName());
	private static ThresholdBreachRequest thresholdBreachRequest;
	

    @BeforeClass
    public static void createInput() throws IOException {
    	// set up your sample input object here.
    	thresholdBreachRequest = new ThresholdBreachRequest();
    	
    	thresholdBreachRequest.setEmailId("divya@test.com");
    	thresholdBreachRequest.setInstitutionId(2);
    	thresholdBreachRequest.setPatientId(2);
    	
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testUpdateAlertStatusByPatientId() {
        ClearThresholdBreachCount handler = new ClearThresholdBreachCount();
        Context ctx = createContext();

        CustomResponse customResponse = handler.handleRequest(thresholdBreachRequest, ctx);
        
		LOGGER.info(customResponse);

		// validate output here.
		Assert.assertEquals("OK", customResponse.getDescription());

       
    }
}
