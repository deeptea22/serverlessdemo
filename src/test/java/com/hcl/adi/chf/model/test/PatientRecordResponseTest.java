package com.hcl.adi.chf.model.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.hcl.adi.chf.model.AlertData;
import com.hcl.adi.chf.model.PatientRecordResponse;

public class PatientRecordResponseTest {
	@Test
	public void testPatientRecordResponse() {
		AlertData alertD= new AlertData();
		alertD.setAlertDataId(null);
		alertD.setBreachParam(null);
		alertD.setBreachText(null);
		alertD.setCreatedBy(null);
		alertD.setCreatedTimestamp(null);
		alertD.setIsActive(null);
		alertD.setMsg(null);
		alertD.setMsgId(null);
		alertD.setReadingDate(null);
		alertD.setSeverity(null);
		alertD.setUpdatedBy(null);
		alertD.setUpdatedBy(null);
		alertD.setUpdatedTimestamp(null);
		
		alertD.getAlertDataId( );
		alertD.getBreachParam( );
		alertD.getBreachText( );
		alertD.getCreatedBy( );
		alertD.getCreatedTimestamp( );
		alertD.getIsActive( );
		alertD.getMsg( );
		alertD.getMsgId( );
		alertD.getReadingDate( );
		alertD.getSeverity( );
		alertD.getUpdatedBy( );
		alertD.getUpdatedBy( );
		alertD.getUpdatedTimestamp( );
		
		alertD.toString();
		
		List<AlertData> al = new ArrayList<AlertData>();
		al.add(alertD);
		PatientRecordResponse patientRecordResponse = new PatientRecordResponse();
		patientRecordResponse.setAlertDataList(al);
		patientRecordResponse.setDeltaZ(null);
		patientRecordResponse.setDeltaZBaseLine(null);
		patientRecordResponse.setFowlerRespirationRate(null);
		patientRecordResponse.setFowlerRespirationRateBaseLine(null);
		patientRecordResponse.setFowlerRRTv(null);
		patientRecordResponse.setFowlerRRTvBaseLine(null);
		patientRecordResponse.setFowlerTidalVol(null);
		patientRecordResponse.setFowlerTidalVolBaseLine(null);
		patientRecordResponse.setHeartRate(null);
		patientRecordResponse.setHeartRateBaseLine(null);
		patientRecordResponse.setPatientInClinicianWatchlist(false);
		patientRecordResponse.setReadingDate(null);
		patientRecordResponse.setReadingDateBaseLine(null);
		patientRecordResponse.setS3Energy(null);
		patientRecordResponse.setS3EnergyBaseLine(null);
		patientRecordResponse.setSupineRespirationRate(null);
		patientRecordResponse.setSupineRespirationRate(null);
		patientRecordResponse.setSupineRespirationRateBaseLine(null);
		patientRecordResponse.setSupineRRTv(null);
		patientRecordResponse.setSupineRRTvBaseLine(null);
		patientRecordResponse.setSupineTidalVol(null);
		patientRecordResponse.setSupineTidalVolBaseLine(null);
		patientRecordResponse.setThoracicImpedance(null);
		patientRecordResponse.setThoracicImpedanceBaseLine(null);
		
		patientRecordResponse.getAlertDataList();
		patientRecordResponse.getDeltaZ( );
		patientRecordResponse.getDeltaZBaseLine( );
		patientRecordResponse.getFowlerRespirationRate( );
		patientRecordResponse.getFowlerRespirationRateBaseLine( );
		patientRecordResponse.getFowlerRRTv( );
		patientRecordResponse.getFowlerRRTvBaseLine( );
		patientRecordResponse.getFowlerTidalVol( );
		patientRecordResponse.getFowlerTidalVolBaseLine( );
		patientRecordResponse.getHeartRate( );
		patientRecordResponse.getHeartRateBaseLine( );
		patientRecordResponse.isPatientInClinicianWatchlist();
		patientRecordResponse.getReadingDate( );
		patientRecordResponse.getReadingDateBaseLine( );
		patientRecordResponse.getS3Energy( );
		patientRecordResponse.getS3EnergyBaseLine( );
		patientRecordResponse.getSupineRespirationRate( );
		patientRecordResponse.getSupineRespirationRate( );
		patientRecordResponse.getSupineRespirationRateBaseLine( );
		patientRecordResponse.getSupineRRTv( );
		patientRecordResponse.getSupineRRTvBaseLine( );
		patientRecordResponse.getSupineTidalVol( );
		patientRecordResponse.getSupineTidalVolBaseLine( );
		patientRecordResponse.getThoracicImpedance( );
		patientRecordResponse.getThoracicImpedanceBaseLine( );
		patientRecordResponse.toString();
		
		
	}
}
