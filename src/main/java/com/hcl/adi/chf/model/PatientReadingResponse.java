package com.hcl.adi.chf.model;

import java.util.List;



/**
 * Model class for passing response of available
 * device readings
 *
 * @author DivyaAg
 */
public final class PatientReadingResponse {

	private List<DeviceData> deviceDataList;
	private int totalReadingsAvailable;
	/**
	 * @return the deviceDataList
	 */
	public List<DeviceData> getDeviceDataList() {
		return deviceDataList;
	}
	/**
	 * @param deviceDataList the deviceDataList to set
	 */
	public void setDeviceDataList(List<DeviceData> deviceDataList) {
		this.deviceDataList = deviceDataList;
	}
	/**
	 * @return the totalReadingsAvailable
	 */
	public int getTotalReadingsAvailable() {
		return totalReadingsAvailable;
	}
	/**
	 * @param totalReadingsAvailable the totalReadingsAvailable to set
	 */
	public void setTotalReadingsAvailable(int totalReadingsAvailable) {
		this.totalReadingsAvailable = totalReadingsAvailable;
	}
	
	@Override
	public String toString() {
		return "PatientReadingResponse [deviceDataList=" + deviceDataList + ", totalReadingsAvailable="
				+ totalReadingsAvailable + "]";
	}
	
	
	
}
