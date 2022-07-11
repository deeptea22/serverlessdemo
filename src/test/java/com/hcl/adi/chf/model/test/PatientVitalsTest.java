package com.hcl.adi.chf.model.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.model.PatientVitals.CustomVitals;

public class PatientVitalsTest {
	@Test
	public void testGetPatientVitalsDetails() {
		
		CustomVitals customVitals = new CustomVitals();
		customVitals.setLabel("abc");
		customVitals.setValue("2");
		customVitals.getLabel();
		customVitals.getValue();
		customVitals.toString();
		
		PatientVitals patientVitals = new PatientVitals();
		List<CustomVitals> customVitalsL = new ArrayList<CustomVitals>();
		customVitalsL.add(customVitals);
		patientVitals.setCustomVitalsList(customVitalsL);
		patientVitals.setPatientVitalsId(1);
		patientVitals.setPatientId(12);
		patientVitals.setSystolicBP(100.1F);
		patientVitals.setDiastolicBP(129F);
		patientVitals.setWeight(56F);
		patientVitals.setTemperature(29.1F);
		patientVitals.setCustomVitals("Apolo");
		patientVitals.setCreatedBy("test@hcl.com");
		patientVitals.setCreatedTimeStamp(new Date());
		patientVitals.setUpdatedBy("test@hcl.com");
		patientVitals.setUpdatedTimeStamp(new Date());
		patientVitals.setReadingDate(new Date());
		
		patientVitals.getCustomVitalsList();
		patientVitals.getPatientVitalsId();
		patientVitals.getPatientId();
		patientVitals.getSystolicBP();
		patientVitals.getDiastolicBP();
		patientVitals.getWeight();
		patientVitals.getTemperature();
		patientVitals.getCustomVitals();
		patientVitals.getCreatedBy();
		patientVitals.getCreatedTimeStamp();
		patientVitals.getUpdatedBy();
		patientVitals.getUpdatedTimeStamp();
		patientVitals.getReadingDate();
		patientVitals.toString();
	}
}
