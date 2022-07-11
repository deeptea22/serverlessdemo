package com.hcl.adi.chf.lambda.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetDerivedDataByPatientId;
import com.hcl.adi.chf.model.PatientRecordResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetDerivedDataByPatientIdTest {
	private static final Logger LOGGER = LogManager.getLogger(GetDerivedDataByPatientIdTest.class.getName());
	private static Map<String,String> input;


	@BeforeClass
	public static void createInput() throws ParseException {
		// set up your sample input object here.
		
		input = new HashMap<String,String>();
		input.put("patientId", "1");
		input.put("clinicianId", "1");
		SimpleDateFormat tsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startDate = tsdf.format(new Date().getYear()- 1);
		String endDate = tsdf.format(new Date());

		input.put("startDate", startDate);
		input.put("endDate", endDate);
	}

    private Context createContext() {
        TestContext ctx = new TestContext();
        // TODO: customize your context here if needed. 
        ctx.setFunctionName("Your Function Name");
        return ctx;
    }

    @Test
    public void getDerivedDataByPatientIdTest() {
    	GetDerivedDataByPatientId handler = new GetDerivedDataByPatientId();

    	PatientRecordResponse output = handler.handleRequest(input, createContext());
        LOGGER.info(output);

		// validate output here.
		Assert.assertNotNull(output);
    }
}
