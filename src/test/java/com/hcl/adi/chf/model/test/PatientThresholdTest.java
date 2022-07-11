package com.hcl.adi.chf.model.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.model.PatientThreshold.Controls;
import com.hcl.adi.chf.model.PatientThreshold.Threshold;

public class PatientThresholdTest {
	@Test
	public void testGetPatientThresholdTest() {
		
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
		
		threshold.getControls();
		threshold.getParamDesc();
		threshold.getParamName();
		threshold.toString();
				
		PatientThreshold patientThreshold = new PatientThreshold();		
		patientThreshold.setThresholdId(1);
		patientThreshold.setPatientId(1);
		ArrayList<Threshold> TL = new ArrayList<PatientThreshold.Threshold>();
		TL.add(threshold);
		patientThreshold.setThreshold(TL);
		patientThreshold.setCreatedBy("");
		patientThreshold.setCreatedTimestamp(new Date());
		patientThreshold.setUpdatedBy("");
		patientThreshold.setUpdatedTimestamp(null);
		
		patientThreshold.getThresholdId();
		patientThreshold.getPatientId();
		patientThreshold.getThreshold();
		patientThreshold.getCreatedBy();
		patientThreshold.getCreatedTimestamp();
		patientThreshold.getUpdatedBy();
		patientThreshold.getUpdatedTimestamp();
		patientThreshold.toString();
	}
}
