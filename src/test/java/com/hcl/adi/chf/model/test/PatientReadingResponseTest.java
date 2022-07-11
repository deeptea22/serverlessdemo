package com.hcl.adi.chf.model.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.model.DeviceData.Ecg;
import com.hcl.adi.chf.model.PatientReadingResponse;

public class PatientReadingResponseTest {
	@Test
	public void testPatientReadingResponse() {
		
		Ecg ecg = new Ecg();
		ecg.setStartTime(0);
		ecg.setEndTime(1);
		ecg.setInterval(0);
		List<Float> value = new ArrayList<Float>();
		value.add(10.2F);
		ecg.setEcgValue(value);
		
		ecg.getStartTime();
		ecg.getEndTime();
		ecg.getInterval();
		ecg.getEcgValue();
		ecg.toString();
		
		DeviceData deviceData = new DeviceData();
		deviceData.setPkId(null);
		deviceData.setMeasurementId(null);
		deviceData.setInstitutionId(null);
		deviceData.setDeviceId(null);
		deviceData.setPatientId(null);
		deviceData.setEcg(ecg);
		deviceData.setStatus(null);
		deviceData.setImpedance(null);
		deviceData.setTemperature(null);
		deviceData.setHeartSoundUrl(null);
		deviceData.setReadingDate(null);
		deviceData.setBaseLineReading(false);
		deviceData.setSeverity(null);
		
		deviceData.getPkId();
		deviceData.getMeasurementId();
		deviceData.getInstitutionId();
		deviceData.getDeviceId();
		deviceData.getPatientId();
		deviceData.getEcg();
		deviceData.getStatus();
		deviceData.getImpedance();
		deviceData.getTemperature();
		deviceData.getHeartSoundUrl();
		deviceData.getReadingDate();
		deviceData.getSeverity();
		deviceData.isBaseLineReading();
		
		PatientReadingResponse patientResponse = new PatientReadingResponse();
		List<DeviceData> device = new ArrayList<DeviceData>();
		device.add(deviceData);
		patientResponse.setDeviceDataList(device);
		patientResponse.setTotalReadingsAvailable(0);
		
		patientResponse.getDeviceDataList();
		patientResponse.getTotalReadingsAvailable();
		patientResponse.toString();
	}
}
