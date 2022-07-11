package com.hcl.adi.chf.model.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.model.ThresholdPolicy.Controls;
import com.hcl.adi.chf.model.ThresholdPolicy.Threshold;
import com.hcl.adi.chf.model.ThresholdPolicy.Validations;

public class ThresholdPolicyTest {
	@Test
	public void testGetThresholdPolicyByInstitutionId() {
		
		Validations validations = new Validations();
		validations.setMax("10");
		validations.setMin("2");
		validations.setUnit("km");
		validations.getMax();
		validations.getMin();
		validations.getUnit();
		Controls control = new Controls();
		control.setUnit("");
		control.setUnitDesc("");
		control.setUnitName("");
		control.setUnitValue("");
		control.getUnit();
		control.getUnitDesc();
		control.getUnitName();
		control.getUnitValue();
		control.toString();
		Threshold threshold = new Threshold();
		List<Controls> controlL = new ArrayList<Controls>();
		controlL.add(control);
		threshold.setControls(controlL);
		threshold.setParamDesc("");
		threshold.setParamName("");
		threshold.setValidations(validations);
		threshold.getControls();
		threshold.getParamDesc();
		threshold.getParamName();
		threshold.getValidations();
		
		ThresholdPolicy thresholdPolicy = new ThresholdPolicy();
		thresholdPolicy.setCreatedBy("");
		thresholdPolicy.setCreatedTimestamp(new Date());
		thresholdPolicy.setInstitutionId(1);
		ArrayList<Threshold> TL = new ArrayList<ThresholdPolicy.Threshold>();
		TL.add(threshold);
		thresholdPolicy.setThreshold(TL);
		thresholdPolicy.setThresholdPolicyId(1);
		thresholdPolicy.setUpdatedBy("");
		thresholdPolicy.setUpdatedTimestamp(null);
		thresholdPolicy.toString();
		thresholdPolicy.getCreatedBy();
		thresholdPolicy.getCreatedTimestamp();
		thresholdPolicy.getInstitutionId();
		thresholdPolicy.getThreshold();
		thresholdPolicy.getThresholdPolicyId();
		thresholdPolicy.getUpdatedBy();
		thresholdPolicy.getUpdatedTimestamp();
	}
}
