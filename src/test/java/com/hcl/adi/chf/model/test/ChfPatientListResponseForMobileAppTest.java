package com.hcl.adi.chf.model.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp.ChfPatientId;

public class ChfPatientListResponseForMobileAppTest {
	@Test
	public void testChfPatientListResponseForMobileApp() {
		ChfPatientId chfPatientId = new ChfPatientId();
		chfPatientId.setAddress(null);
		chfPatientId.setChfPatientId(null);
		chfPatientId.setContactNo(null);
		chfPatientId.setDob(null);
		chfPatientId.setFirstName(null);
		chfPatientId.setGender(null);
		chfPatientId.setLastName(null);
		chfPatientId.setMrNumber(null);
		chfPatientId.setPatientDetailsJson(null);
		chfPatientId.setPatientId(0);
		chfPatientId.setSsn(null);
		chfPatientId.setZip(null);
		
		chfPatientId.getAddress();
		chfPatientId.getChfPatientId();
		chfPatientId.getContactNo();
		chfPatientId.getDob();
		chfPatientId.getFirstName();
		chfPatientId.getGender();
		chfPatientId.getLastName();
		chfPatientId.getMrNumber();
		chfPatientId.getPatientDetailsJson();
		chfPatientId.getPatientId();
		chfPatientId.getSsn();
		chfPatientId.getZip();
		chfPatientId.toString();
		
		List<ChfPatientId> cl = new ArrayList<ChfPatientListResponseForMobileApp.ChfPatientId>();
		cl.add(chfPatientId);
		ChfPatientListResponseForMobileApp obj = new ChfPatientListResponseForMobileApp();
		obj.setChfPatientIdList(cl);
		obj.setPatientCount(0);
		
		obj.getChfPatientIdList();
		obj.getPatientCount();
		
		obj.toString();
		
		
		
	}
}
