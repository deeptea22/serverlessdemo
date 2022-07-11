package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.RemovePatientProvider;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.PatientProvider;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RemovePatientProviderTest {
	private static final Logger LOGGER = LogManager.getLogger(RemovePatientProviderTest.class.getName());
	private static PatientProvider patientProvider;

    @BeforeClass
    public static void createInput() throws IOException {
    	patientProvider = new PatientProvider();
    	patientProvider.setProviderId(5);
    	patientProvider.setChfPatientId("c5");
    	patientProvider.setUpdatedBy("admin@admin.com");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testRemovePatientProvider() {
        RemovePatientProvider handler = new RemovePatientProvider();
        Context ctx = createContext();

        CustomResponse customResponse = handler.handleRequest(patientProvider, ctx);
        LOGGER.info(customResponse);
        // TODO: validate output here if needed.
        Assert.assertEquals(200, customResponse.getStatusCode());
    }
}
