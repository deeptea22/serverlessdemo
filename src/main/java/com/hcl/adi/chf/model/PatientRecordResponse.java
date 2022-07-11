package com.hcl.adi.chf.model;

import java.util.Date;
import java.util.List;

/**
 * Model class to return lists of different derived data along with data points
 * corresponding to baseline reading and patient watchlist status. And those
 * lists of different derived data and data points corresponding to baseline
 * reading would be used to render different graphs on UI
 *
 * @author AyushRa
 */
public final class PatientRecordResponse {
	private List<Double> supineTidalVol;
	private List<Double> supineRespirationRate;
	private List<Double> supineRRTv;
	private List<Double> fowlerTidalVol;
	private List<Double> fowlerRespirationRate;
	private List<Double> fowlerRRTv;
	private List<Double> deltaZ;
	private List<Double> s3Energy;
	private List<Double> thoracicImpedance;
	private List<Double> heartRate;
	private List<Date> readingDate;
	private List<AlertData> alertDataList;
	private Double supineTidalVolBaseLine;
	private Double supineRespirationRateBaseLine;
	private Double supineRRTvBaseLine;
	private Double fowlerTidalVolBaseLine;
	private Double fowlerRespirationRateBaseLine;
	private Double fowlerRRTvBaseLine;
	private Double deltaZBaseLine;
	private Double s3EnergyBaseLine;
	private Double thoracicImpedanceBaseLine;
	private Double heartRateBaseLine;
	private Date readingDateBaseLine;
	private boolean isPatientInClinicianWatchlist;

	/**
	 * @return the supineTidalVol
	 */
	public List<Double> getSupineTidalVol() {
		return supineTidalVol;
	}

	/**
	 * @param supineTidalVol
	 *            the supineTidalVol to set
	 */
	public void setSupineTidalVol(List<Double> supineTidalVol) {
		this.supineTidalVol = supineTidalVol;
	}

	/**
	 * @return the supineRespirationRate
	 */
	public List<Double> getSupineRespirationRate() {
		return supineRespirationRate;
	}

	/**
	 * @param supineRespirationRate
	 *            the supineRespirationRate to set
	 */
	public void setSupineRespirationRate(List<Double> supineRespirationRate) {
		this.supineRespirationRate = supineRespirationRate;
	}

	/**
	 * @return the supineRRTv
	 */
	public List<Double> getSupineRRTv() {
		return supineRRTv;
	}

	/**
	 * @param supineRRTv
	 *            the supineRRTv to set
	 */
	public void setSupineRRTv(List<Double> supineRRTv) {
		this.supineRRTv = supineRRTv;
	}

	/**
	 * @return the fowlerTidalVol
	 */
	public List<Double> getFowlerTidalVol() {
		return fowlerTidalVol;
	}

	/**
	 * @param fowlerTidalVol
	 *            the fowlerTidalVol to set
	 */
	public void setFowlerTidalVol(List<Double> fowlerTidalVol) {
		this.fowlerTidalVol = fowlerTidalVol;
	}

	/**
	 * @return the fowlerRespirationRate
	 */
	public List<Double> getFowlerRespirationRate() {
		return fowlerRespirationRate;
	}

	/**
	 * @param fowlerRespirationRate
	 *            the fowlerRespirationRate to set
	 */
	public void setFowlerRespirationRate(List<Double> fowlerRespirationRate) {
		this.fowlerRespirationRate = fowlerRespirationRate;
	}

	/**
	 * @return the fowlerRRTv
	 */
	public List<Double> getFowlerRRTv() {
		return fowlerRRTv;
	}

	/**
	 * @param fowlerRRTv
	 *            the fowlerRRTv to set
	 */
	public void setFowlerRRTv(List<Double> fowlerRRTv) {
		this.fowlerRRTv = fowlerRRTv;
	}

	/**
	 * @return the deltaZ
	 */
	public List<Double> getDeltaZ() {
		return deltaZ;
	}

	/**
	 * @param deltaZ
	 *            the deltaZ to set
	 */
	public void setDeltaZ(List<Double> deltaZ) {
		this.deltaZ = deltaZ;
	}

	/**
	 * @return the s3Energy
	 */
	public List<Double> getS3Energy() {
		return s3Energy;
	}

	/**
	 * @param s3Energy
	 *            the s3Energy to set
	 */
	public void setS3Energy(List<Double> s3Energy) {
		this.s3Energy = s3Energy;
	}

	/**
	 * @return the thoracicImpedance
	 */
	public List<Double> getThoracicImpedance() {
		return thoracicImpedance;
	}

	/**
	 * @param thoracicImpedance
	 *            the thoracicImpedance to set
	 */
	public void setThoracicImpedance(List<Double> thoracicImpedance) {
		this.thoracicImpedance = thoracicImpedance;
	}

	/**
	 * @return the heartRate
	 */
	public List<Double> getHeartRate() {
		return heartRate;
	}

	/**
	 * @param heartRate
	 *            the heartRate to set
	 */
	public void setHeartRate(List<Double> heartRate) {
		this.heartRate = heartRate;
	}

	/**
	 * @return the readingDate
	 */
	public List<Date> getReadingDate() {
		return readingDate;
	}

	/**
	 * @param readingDate
	 *            the readingDate to set
	 */
	public void setReadingDate(List<Date> readingDate) {
		this.readingDate = readingDate;
	}

	/**
	 * @return the supineTidalVolBaseLine
	 */
	public Double getSupineTidalVolBaseLine() {
		return supineTidalVolBaseLine;
	}

	/**
	 * @param supineTidalVolBaseLine
	 *            the supineTidalVolBaseLine to set
	 */
	public void setSupineTidalVolBaseLine(Double supineTidalVolBaseLine) {
		this.supineTidalVolBaseLine = supineTidalVolBaseLine;
	}

	/**
	 * @return the supineRespirationRateBaseLine
	 */
	public Double getSupineRespirationRateBaseLine() {
		return supineRespirationRateBaseLine;
	}

	/**
	 * @param supineRespirationRateBaseLine
	 *            the supineRespirationRateBaseLine to set
	 */
	public void setSupineRespirationRateBaseLine(Double supineRespirationRateBaseLine) {
		this.supineRespirationRateBaseLine = supineRespirationRateBaseLine;
	}

	/**
	 * @return the supineRRTvBaseLine
	 */
	public Double getSupineRRTvBaseLine() {
		return supineRRTvBaseLine;
	}

	/**
	 * @param supineRRTvBaseLine
	 *            the supineRRTvBaseLine to set
	 */
	public void setSupineRRTvBaseLine(Double supineRRTvBaseLine) {
		this.supineRRTvBaseLine = supineRRTvBaseLine;
	}

	/**
	 * @return the fowlerTidalVolBaseLine
	 */
	public Double getFowlerTidalVolBaseLine() {
		return fowlerTidalVolBaseLine;
	}

	/**
	 * @param fowlerTidalVolBaseLine
	 *            the fowlerTidalVolBaseLine to set
	 */
	public void setFowlerTidalVolBaseLine(Double fowlerTidalVolBaseLine) {
		this.fowlerTidalVolBaseLine = fowlerTidalVolBaseLine;
	}

	/**
	 * @return the fowlerRespirationRateBaseLine
	 */
	public Double getFowlerRespirationRateBaseLine() {
		return fowlerRespirationRateBaseLine;
	}

	/**
	 * @param fowlerRespirationRateBaseLine
	 *            the fowlerRespirationRateBaseLine to set
	 */
	public void setFowlerRespirationRateBaseLine(Double fowlerRespirationRateBaseLine) {
		this.fowlerRespirationRateBaseLine = fowlerRespirationRateBaseLine;
	}

	/**
	 * @return the fowlerRRTvBaseLine
	 */
	public Double getFowlerRRTvBaseLine() {
		return fowlerRRTvBaseLine;
	}

	/**
	 * @param fowlerRRTvBaseLine
	 *            the fowlerRRTvBaseLine to set
	 */
	public void setFowlerRRTvBaseLine(Double fowlerRRTvBaseLine) {
		this.fowlerRRTvBaseLine = fowlerRRTvBaseLine;
	}

	/**
	 * @return the deltaZBaseLine
	 */
	public Double getDeltaZBaseLine() {
		return deltaZBaseLine;
	}

	/**
	 * @param deltaZBaseLine
	 *            the deltaZBaseLine to set
	 */
	public void setDeltaZBaseLine(Double deltaZBaseLine) {
		this.deltaZBaseLine = deltaZBaseLine;
	}

	/**
	 * @return the s3EnergyBaseLine
	 */
	public Double getS3EnergyBaseLine() {
		return s3EnergyBaseLine;
	}

	/**
	 * @param s3EnergyBaseLine
	 *            the s3EnergyBaseLine to set
	 */
	public void setS3EnergyBaseLine(Double s3EnergyBaseLine) {
		this.s3EnergyBaseLine = s3EnergyBaseLine;
	}

	/**
	 * @return the thoracicImpedanceBaseLine
	 */
	public Double getThoracicImpedanceBaseLine() {
		return thoracicImpedanceBaseLine;
	}

	/**
	 * @param thoracicImpedanceBaseLine
	 *            the thoracicImpedanceBaseLine to set
	 */
	public void setThoracicImpedanceBaseLine(Double thoracicImpedanceBaseLine) {
		this.thoracicImpedanceBaseLine = thoracicImpedanceBaseLine;
	}

	/**
	 * @return the heartRateBaseLine
	 */
	public Double getHeartRateBaseLine() {
		return heartRateBaseLine;
	}

	/**
	 * @param heartRateBaseLine
	 *            the heartRateBaseLine to set
	 */
	public void setHeartRateBaseLine(Double heartRateBaseLine) {
		this.heartRateBaseLine = heartRateBaseLine;
	}

	/**
	 * @return the readingDateBaseLine
	 */
	public Date getReadingDateBaseLine() {
		return readingDateBaseLine;
	}

	/**
	 * @param readingDateBaseLine
	 *            the readingDateBaseLine to set
	 */
	public void setReadingDateBaseLine(Date readingDateBaseLine) {
		this.readingDateBaseLine = readingDateBaseLine;
	}

	/**
	 * @return the isPatientInClinicianWatchlist
	 */
	public boolean isPatientInClinicianWatchlist() {
		return isPatientInClinicianWatchlist;
	}

	/**
	 * @param isPatientInClinicianWatchlist
	 *            the isPatientInClinicianWatchlist to set
	 */
	public void setPatientInClinicianWatchlist(boolean isPatientInClinicianWatchlist) {
		this.isPatientInClinicianWatchlist = isPatientInClinicianWatchlist;
	}

	/**
	 * @return the alertDataList
	 */
	public List<AlertData> getAlertDataList() {
		return alertDataList;
	}

	/**
	 * @param alertDataList
	 *            the alertDataList to set
	 */
	public void setAlertDataList(List<AlertData> alertDataList) {
		this.alertDataList = alertDataList;
	}

	@Override
	public String toString() {
		return "PatientRecordResponse [supineTidalVol=" + supineTidalVol + ", supineRespirationRate="
				+ supineRespirationRate + ", supineRRTv=" + supineRRTv + ", fowlerTidalVol=" + fowlerTidalVol
				+ ", fowlerRespirationRate=" + fowlerRespirationRate + ", fowlerRRTv=" + fowlerRRTv + ", deltaZ="
				+ deltaZ + ", s3Energy=" + s3Energy + ", thoracicImpedance=" + thoracicImpedance + ", heartRate="
				+ heartRate + ", readingDate=" + readingDate + ", alertDataList=" + alertDataList
				+ ", supineTidalVolBaseLine=" + supineTidalVolBaseLine + ", supineRespirationRateBaseLine="
				+ supineRespirationRateBaseLine + ", supineRRTvBaseLine=" + supineRRTvBaseLine
				+ ", fowlerTidalVolBaseLine=" + fowlerTidalVolBaseLine + ", fowlerRespirationRateBaseLine="
				+ fowlerRespirationRateBaseLine + ", fowlerRRTvBaseLine=" + fowlerRRTvBaseLine + ", deltaZBaseLine="
				+ deltaZBaseLine + ", s3EnergyBaseLine=" + s3EnergyBaseLine + ", thoracicImpedanceBaseLine="
				+ thoracicImpedanceBaseLine + ", heartRateBaseLine=" + heartRateBaseLine + ", readingDateBaseLine="
				+ readingDateBaseLine + ", isPatientInClinicianWatchlist=" + isPatientInClinicianWatchlist + "]";
	}
}