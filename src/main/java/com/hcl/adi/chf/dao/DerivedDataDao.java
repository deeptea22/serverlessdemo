package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.AlertData;
import com.hcl.adi.chf.model.PatientRecordResponse;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on derived_data table
 *
 * @author AyushRa
 */
public final class DerivedDataDao {
	private static final Logger LOGGER = LogManager.getLogger(DerivedDataDao.class.getName());

	private DerivedDataDao() {
		// private constructor
	}

	/**
	 * Fetch lists of different derived data for the readings that specified
	 * patient took along with data points corresponding to baseline reading in
	 * a single object named as PatientRecordResponse
	 *
	 * @param patientId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static PatientRecordResponse fetchDerivedDataByPatientId(final Integer patientId, final Date startDate,
			final Date endDate) {
		List<Double> supineTidalVolList = new ArrayList<Double>();
		List<Double> supineRespirationRateList = new ArrayList<Double>();
		List<Double> supineRRTvList = new ArrayList<Double>();
		List<Double> fowlerTidalVolList = new ArrayList<Double>();
		List<Double> fowlerRespirationRateList = new ArrayList<Double>();
		List<Double> fowlerRRTvList = new ArrayList<Double>();
		List<Double> deltaZList = new ArrayList<Double>();
		List<Double> s3EnergyList = new ArrayList<Double>();
		List<Double> thoracicImpedanceList = new ArrayList<Double>();
		List<Double> heartRateList = new ArrayList<Double>();
		List<Date> readingDateList = new ArrayList<Date>();
		List<AlertData> alertDataList = new ArrayList<AlertData>();

		Double supineTidalVol = null;
		Double supineRespirationRate = null;
		Double supineRRTv = null;
		Double fowlerTidalVol = null;
		Double fowlerRespirationRate = null;
		Double fowlerRRTv = null;
		Double deltaZ = null;
		Double s3Energy = null;
		Double thoracicImpedance = null;
		Double heartRate = null;
		Date readingDate = null;

		PreparedStatement pstmt = null;
		PatientRecordResponse patientRecordResponse = new PatientRecordResponse();

		try (Connection connection = DBConnection.getConnection();) {
			if (startDate != null && endDate != null) {
				pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("SELECT_DERIVED_DATA_BY_PATIENT_ID_AND_DATE_FILTER"));
				pstmt.setInt(1, patientId);
				pstmt.setDate(2, new java.sql.Date(startDate.getTime()));
				pstmt.setDate(3, new java.sql.Date(endDate.getTime()));

			} else {
				pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_DERIVED_DATA_BY_PATIENT_ID"));
				pstmt.setInt(1, patientId);
			}

			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					supineTidalVol = rs.getDouble(TableConstants.DERIVED_DATA_SUPINE_TIDAL_VOL);
					supineRespirationRate = rs.getDouble(TableConstants.DERIVED_DATA_SUPINE_RESPIRATION_RATE);
					supineRRTv = rs.getDouble(TableConstants.DERIVED_DATA_SUPINE_RR_TV);
					fowlerTidalVol = rs.getDouble(TableConstants.DERIVED_DATA_FOWLER_TIDAL_VOL);
					fowlerRespirationRate = rs.getDouble(TableConstants.DERIVED_DATA_FOWLER_RESPIRATION_RATE);
					fowlerRRTv = rs.getDouble(TableConstants.DERIVED_DATA_FOWLER_RR_TV);
					deltaZ = rs.getDouble(TableConstants.DERIVED_DATA_DELTA_Z);
					s3Energy = rs.getDouble(TableConstants.DERIVED_DATA_S3_ENERGY);
					thoracicImpedance = rs.getDouble(TableConstants.DERIVED_DATA_THORACIC_IMPEDANCE);
					heartRate = rs.getDouble(TableConstants.DERIVED_DATA_HEART_RATE);
					readingDate = rs.getTimestamp(TableConstants.DEVICE_DATA_READING_DATE);

					if (rs.getBoolean(TableConstants.DEVICE_DATA_IS_BASELINE_READING)) {
						patientRecordResponse.setSupineTidalVolBaseLine(supineTidalVol);
						patientRecordResponse.setSupineRespirationRateBaseLine(supineRespirationRate);
						patientRecordResponse.setSupineRRTvBaseLine(supineRRTv);
						patientRecordResponse.setFowlerTidalVolBaseLine(fowlerTidalVol);
						patientRecordResponse.setFowlerRespirationRateBaseLine(fowlerRespirationRate);
						patientRecordResponse.setFowlerRRTvBaseLine(fowlerRRTv);
						patientRecordResponse.setDeltaZBaseLine(deltaZ);
						patientRecordResponse.setS3EnergyBaseLine(s3Energy);
						patientRecordResponse.setThoracicImpedanceBaseLine(thoracicImpedance);
						patientRecordResponse.setHeartRateBaseLine(heartRate);
						patientRecordResponse.setReadingDateBaseLine(readingDate);
					}

					supineTidalVolList.add(supineTidalVol);
					supineRespirationRateList.add(supineRespirationRate);
					supineRRTvList.add(supineRRTv);
					fowlerTidalVolList.add(fowlerTidalVol);
					fowlerRespirationRateList.add(fowlerRespirationRate);
					fowlerRRTvList.add(fowlerRRTv);
					deltaZList.add(deltaZ);
					s3EnergyList.add(s3Energy);
					thoracicImpedanceList.add(thoracicImpedance);
					heartRateList.add(heartRate);
					readingDateList.add(readingDate);

					// Alert Data Changes
					if(rs.getString(TableConstants.ALERT_DATA_ID) != null) {
						AlertData alertData = new AlertData();
						alertData.setAlertDataId(rs.getString(TableConstants.ALERT_DATA_ID));
						alertData.setBreachParam(rs.getString(TableConstants.ALERT_DATA_BREACH_PARAM));
						alertData.setIsActive(rs.getString(TableConstants.ALERT_DATA_IS_ACTIVE));

						if (StringUtils.isNoneBlank(alertData.getAlertDataId())) {
							alertData.setReadingDate(readingDate);
						}

						alertDataList.add(alertData);
					}
				}

				patientRecordResponse.setSupineTidalVol(supineTidalVolList);
				patientRecordResponse.setSupineRespirationRate(supineRespirationRateList);
				patientRecordResponse.setSupineRRTv(supineRRTvList);
				patientRecordResponse.setFowlerTidalVol(fowlerTidalVolList);
				patientRecordResponse.setFowlerRespirationRate(fowlerRespirationRateList);
				patientRecordResponse.setFowlerRRTv(fowlerRRTvList);
				patientRecordResponse.setDeltaZ(deltaZList);
				patientRecordResponse.setS3Energy(s3EnergyList);
				patientRecordResponse.setThoracicImpedance(thoracicImpedanceList);
				patientRecordResponse.setHeartRate(heartRateList);
				patientRecordResponse.setReadingDate(readingDateList);
				patientRecordResponse.setAlertDataList(alertDataList);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientRecordResponse;
	}
}