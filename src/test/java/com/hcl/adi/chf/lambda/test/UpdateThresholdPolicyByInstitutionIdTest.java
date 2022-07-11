package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateThresholdPolicyByInstitutionId;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.model.ThresholdPolicy.Controls;
import com.hcl.adi.chf.model.ThresholdPolicy.Threshold;
import com.hcl.adi.chf.model.ThresholdPolicy.Validations;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UpdateThresholdPolicyByInstitutionIdTest  {
	private static final Logger LOGGER = LogManager.getLogger(UpdateThresholdPolicyByInstitutionIdTest.class.getName());
	private static ThresholdPolicy thresholdPolicy;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		Validations validations = new Validations();
		validations.setMax("10");
		validations.setMin("2");
		validations.setUnit("km");
		Controls control = new Controls();
		control.setUnit("meter");
		control.setUnitDesc("meter");
		control.setUnitName("meter");
		control.setUnitValue("53");
		Threshold threshold = new Threshold();
		List controlL = new ArrayList<Controls>();
		controlL.add(control);
		threshold.setControls(controlL);
		threshold.setParamDesc("km");
		threshold.setParamName("da");
		threshold.setValidations(validations);
		
		thresholdPolicy = new ThresholdPolicy();

		thresholdPolicy.setThresholdPolicyId(1);
		thresholdPolicy.setInstitutionId(1);
		thresholdPolicy.setAction("Toggle");
		thresholdPolicy.setStatus("A");
		ArrayList<Threshold> TL = new ArrayList<ThresholdPolicy.Threshold>();
		TL.add(threshold);
		thresholdPolicy.setThreshold(TL);
		thresholdPolicy.setCreatedBy("admin@hcl.com");
		thresholdPolicy.setCreatedTimestamp(new Date());
		thresholdPolicy.setUpdatedBy("clinician@hcl.com");
		thresholdPolicy.setUpdatedTimestamp(new Date());
	}
	
	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

    @Test
    public void testUpdateThresholdPolicyByInstitutionId() {
        UpdateThresholdPolicyByInstitutionId handler = new UpdateThresholdPolicyByInstitutionId();
        Context ctx = createContext();

        CustomResponse output = handler.handleRequest(thresholdPolicy, ctx);
        LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
    }
}
