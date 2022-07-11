package com.hcl.adi.chf.model.test;

import org.junit.Test;

import com.hcl.adi.chf.model.PatientRecord;

public class PatientRecordTest {
	@Test
	public void testPatientRecord() {
		PatientRecord patientR = new PatientRecord();
		patientR.setAbnormalRhythm(null);
		patientR.setChfPatientId(null);
		patientR.setDailyCompliance(null);
		patientR.setHeartRateTrend(null);
		patientR.setHeartRateTrendValue(null);
		patientR.setInstitutionId(null);
		patientR.setLastRead(null);
		patientR.setMeasurementId(null);
		patientR.setPatientId(null);
		patientR.setPercentage(null);
		patientR.setQrsTrend(null);
		patientR.setQrsWidth(null);
		patientR.setQtTrend(null);
		patientR.setQtWidth(null);
		patientR.setReadingCompliance(null);
		patientR.setRespirationRateTrend(null);
		patientR.setRespirationRateTrendValue(null);
		patientR.setS3Trend(null);
		patientR.setSystemId(null);
		patientR.setThoracicImpedanceTrend(null);
		patientR.setThresholdAlarms(null);
		patientR.setTidalVolumeTrend(null);
		
		patientR.getAbnormalRhythm();
		patientR.getChfPatientId();
		patientR.getDailyCompliance();
		patientR.getHeartRateTrend();
		patientR.getHeartRateTrendValue();
		patientR.getInstitutionId();
		patientR.getLastRead();
		patientR.getMeasurementId();
		patientR.getPatientId();
		patientR.getPercentage();
		patientR.getQrsTrend();
		patientR.getQrsWidth();
		patientR.getQtTrend();
		patientR.getQtWidth();
		patientR.getReadingCompliance();
		patientR.getRespirationRateTrend();
		patientR.getRespirationRateTrendValue();
		patientR.getS3Trend();
		patientR.getSystemId();
		patientR.getThoracicImpedanceTrend();
		patientR.getThresholdAlarms();
		patientR.getTidalVolumeTrend();
		patientR.toString();
		patientR.hashCode();
		patientR.equals(patientR);
		
	}
}
