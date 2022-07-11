package com.hcl.adi.chf.model.test;

import org.junit.Test;

import com.hcl.adi.chf.model.PatientClinicianMapping;

public class PatientClinicianMappingTest {
	@Test
	public void testPatientClinicianMapping() {
		PatientClinicianMapping clinicianMapping = new PatientClinicianMapping();
		clinicianMapping.setMapId(null);
		clinicianMapping.setPatientId(null);
		clinicianMapping.setClinicianId(null);
		clinicianMapping.setAction(null);
		clinicianMapping.setCreatedBy(null);
		clinicianMapping.setCreatedTimestamp(null);
		clinicianMapping.setUpdatedBy(null);
		clinicianMapping.setUpdatedTimestamp(null);
				
		clinicianMapping.getMapId();
		clinicianMapping.getPatientId();
		clinicianMapping.getClinicianId();
		clinicianMapping.getAction();
		clinicianMapping.getCreatedBy();
		clinicianMapping.getCreatedTimestamp();
		clinicianMapping.getUpdatedBy();
		clinicianMapping.getUpdatedTimestamp();
		clinicianMapping.toString();
		clinicianMapping.hashCode();
		clinicianMapping.equals(clinicianMapping);
		
	}
}
