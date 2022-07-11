package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.s3.AmazonS3URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.model.DeviceData.Ecg;
import com.hcl.adi.chf.model.PatientReadingResponse;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.model.PatientVitals.CustomVitals;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.S3Util;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on device_data, patient_vitals table
 * for clinician Dashboard
 *
 * @author DivyaAg
 */
public final class ClinicianDashBoardDao {

	private static final Logger LOGGER = LogManager.getLogger(ClinicianDashBoardDao.class.getName());

	private ClinicianDashBoardDao() {
		// private constructor
	}


	/**
	 * This method will fetch the patient id status 
	 *
	 * @param patientId
	 * @return
	 */
	public static List<DeviceData> fetchEcgDataBasedOnPatientId(final Integer patientId) {
		List<DeviceData> deviceDataList = new ArrayList<DeviceData>();
		String baselineLatestTimeStamp = null;
		String normalLatestTimeStamp = null;
		String secondNormalLatestTimeStamp = null;
		String chfPatientId = null;


		/**
		 * First, fetch the latest timeStamp of baseline reading and normal
		 * reading and then fetch the data accordingly as the requirement is to
		 * show the data also along with reading_dates for following 3 readings:
		 * one is for latest baseline and other two for latest normal readings
		 */
		try (Connection connection = DBConnection.getConnection();) {
			
			
			
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_CHF_PATIENT_ID_FROM_PATIENT_PHI"));
			pstmt.setInt(1, patientId); 
			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				chfPatientId = rs.getString(TableConstants.CHF_PATIENT_ID);
			}
			if(chfPatientId != null) {
				pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("SELECT_LATEST_TIMESTAMPS_FROM_DEVICE_DATA"));
				pstmt.setString(1, chfPatientId);
				pstmt.setString(2, chfPatientId);
				pstmt.setString(3, chfPatientId);
				pstmt.setString(4, chfPatientId);

				rs = pstmt.executeQuery();

				if (rs != null && rs.next()) {
					baselineLatestTimeStamp = rs.getString(TableConstants.BASELINE_LATEST_TIMESTAMP);
					normalLatestTimeStamp = rs.getString(TableConstants.NORMAL_LATEST_TIMESTAMP);
					secondNormalLatestTimeStamp = rs.getString(TableConstants.SECOND_NORMAL_LATEST_TIMESTAMP);
					if(baselineLatestTimeStamp == null){
						baselineLatestTimeStamp = "";
					}
					if(normalLatestTimeStamp == null){
						normalLatestTimeStamp = "";
					}
					if(secondNormalLatestTimeStamp == null){
						secondNormalLatestTimeStamp = "";
					}
				}

				if (baselineLatestTimeStamp != null && normalLatestTimeStamp != null
						&& secondNormalLatestTimeStamp != null) {
					pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_ECG_FROM_DERIVED_DATA_ON_PATIENT_ID"));
					pstmt.setString(1, baselineLatestTimeStamp);
					pstmt.setString(2, normalLatestTimeStamp);
					pstmt.setString(3, secondNormalLatestTimeStamp);

					rs = pstmt.executeQuery();

					if (rs != null) {
						while (rs.next()) {
							DeviceData deviceData = new DeviceData();
							deviceData.setPkId(rs.getInt(TableConstants.DEVICE_PACKET_ID));
							deviceData.setStatus(rs.getString(TableConstants.DEVICE_DATA_STATUS));
							deviceData.setBaseLineReading(rs.getBoolean(TableConstants.DEVICE_DATA_IS_BASELINE_READING));
							deviceData.setReadingDate(rs.getTimestamp(TableConstants.DEVICE_DATA_READING_DATE));
							
							// first fetch the ecg url from derived_data json of derived_data table
							String ecgUrl = rs.getString(TableConstants.DEVICE_DATA_ECG_URL);
							if(ecgUrl != null) {
								AmazonS3URI s3URI = new AmazonS3URI(ecgUrl.trim());
								// Now fetch content of ECG from S3 bucket
								Ecg values = S3Util.readContentsFromS3CSVAndWriteIntoLocalFile(s3URI.getBucket(), s3URI.getKey());
								deviceData.setEcg(values);
							}
							deviceDataList.add(deviceData);	
						}
					}


				}
			}
			

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return deviceDataList;
	}

	/**
	 * This method will fetch the patient id status 
	 *
	 * @param patientId
	 * @return
	 */
	public static CustomResponse fetchReadingStatusBasedOnPatientId(final int patientId) {
		CustomResponse response = new CustomResponse();

		PreparedStatement pstmt = null;

		try (Connection connection = DBConnection.getConnection();) {

			pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_DEVICE_DATA_FOR_BASELINE_STATUS"));
			pstmt.setInt(1, patientId);
			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				response.setStatusCode(Status.OK.getStatusCode());
				response.setDescription(Status.OK.toString());
			} else {
				response.setStatusCode(Status.NO_CONTENT.getStatusCode());
				response.setDescription(Status.NO_CONTENT.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}
		return response;
	}

	/**
	 * This method will fetch the list of reading dates from device_data table
	 * for specified patient
	 *
	 * @param patientId
	 * @return
	 */
	public static PatientReadingResponse fetchDeviceDataBasedOnPatientId(final Integer patientId, 
			final Integer pageStartIndex, final Integer pageCount) {
		List<DeviceData> deviceDataList = new ArrayList<DeviceData>();
		PatientReadingResponse patientReadingResponse = new PatientReadingResponse();
		String baselineLatestTimeStamp = null;
		String normalLatestTimeStamp = null;
		String secondNormalLatestTimeStamp = null;
		int totalReadingsAvailable = 0;
		String chfPatientId = null;


		/**First, fetch the chfPatientId from patientPhi table based on patient i
		 * 
		 * First, fetch the latest timeStamp of baseline reading and normal
		 * reading and then fetch the data accordingly as the requirement is to
		 * show the data also along with reading_dates for following 3 readings:
		 * one is for latest baseline and other two for latest normal readings
		 */
		try (Connection connection = DBConnection.getConnection();) {
			
			
			
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_CHF_PATIENT_ID_FROM_PATIENT_PHI"));
			pstmt.setInt(1, patientId); 
			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				chfPatientId = rs.getString(TableConstants.CHF_PATIENT_ID);
			}
			if(chfPatientId != null) {
				pstmt  = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_LATEST_TIMESTAMPS_FROM_DEVICE_DATA"));
				pstmt.setString(1, chfPatientId);
				pstmt.setString(2, chfPatientId);
				pstmt.setString(3, chfPatientId);
				pstmt.setString(4, chfPatientId);

			 rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				baselineLatestTimeStamp = rs.getString(TableConstants.BASELINE_LATEST_TIMESTAMP);
				normalLatestTimeStamp = rs.getString(TableConstants.NORMAL_LATEST_TIMESTAMP);
				secondNormalLatestTimeStamp = rs.getString(TableConstants.SECOND_NORMAL_LATEST_TIMESTAMP);
				
				if(baselineLatestTimeStamp == null){
					baselineLatestTimeStamp = "";
				}
				if(normalLatestTimeStamp == null){
					normalLatestTimeStamp = "";
				}
				if(secondNormalLatestTimeStamp == null){
					secondNormalLatestTimeStamp = "";
				}
			}

		
				// set the page offset manually as UI will pass page number in pageStartIndex
				int offset = (pageStartIndex - 1) * pageCount;
				pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_DEVICE_DATA_ON_PATIENT_ID"));
				pstmt.setString(1, baselineLatestTimeStamp);
				pstmt.setString(2, normalLatestTimeStamp);
				pstmt.setString(3, secondNormalLatestTimeStamp);
				pstmt.setString(4, chfPatientId);
				pstmt.setInt(5, offset);
				pstmt.setInt(6, pageCount);
				System.out.println(pstmt);
				rs = pstmt.executeQuery();

				if (rs != null) {
					while (rs.next()) {
						DeviceData deviceData = new DeviceData();
						deviceData.setPkId(rs.getInt(TableConstants.DEVICE_PACKET_ID));
						deviceData.setReadingDate(rs.getTimestamp(TableConstants.DEVICE_DATA_READING_DATE));
						deviceData.setStatus(rs.getString(TableConstants.DEVICE_DATA_STATUS));
						deviceData.setBaseLineReading(rs.getBoolean(TableConstants.DEVICE_DATA_IS_BASELINE_READING));
						deviceData.setSeverity(rs.getString(TableConstants.ALERT_DATA_SEVERITY));
						deviceData.setHeartSoundUrl(rs.getString(TableConstants.DEVICE_DATA_HEART_SOUND_URL));
						deviceDataList.add(deviceData);
					}
				}
				
				

				// Now count total number of patients of the institution
				pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("COUNT_NO_OF_READINGS_AVAILABLE_BASED_ON_PATIENT_ID"));
				pstmt.setString(1, chfPatientId);

				rs = pstmt.executeQuery();

				if (rs != null && rs.next()) {
					totalReadingsAvailable = rs.getInt(TableConstants.PATIENT_READING_COUNT);
				}

				patientReadingResponse.setDeviceDataList(deviceDataList);
				patientReadingResponse.setTotalReadingsAvailable(totalReadingsAvailable);

				
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientReadingResponse;
	}


	/**
	 * This method will fetch required details as ecg, heartsound url from derived_data table for a
	 * patient based on packet id
	 *
	 * @param packetId
	 * @return
	 */
	public static DeviceData fetchReadingDetailsBasedOnPacketId(final Integer packetId) {
		DeviceData deviceData = new DeviceData();
		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_DATA_ON_DEVICE_DATA_BASED_ON_PACKET_ID"));
			pstmt.setInt(1, packetId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				String ecgUrl = rs.getString(TableConstants.DEVICE_DATA_ECG_URL);
				if(ecgUrl != null) {
					AmazonS3URI s3URI = new AmazonS3URI(ecgUrl.trim());
					// Now fetch content of ECG from S3 bucket
					
					Ecg values = S3Util.readContentsFromS3CSVAndWriteIntoLocalFile(s3URI.getBucket(), s3URI.getKey());
					deviceData.setEcg(values);
					deviceData.setHeartSoundUrl(rs.getString(TableConstants.DEVICE_DATA_HEART_SOUND_URL));
				}
			}


		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return deviceData;
	}


	/**
	 * This method will update the status of patient reading to Inactive 
	 * if the clinician discard it
	 *
	 * @param packetId
	 * @return
	 */
	public static CustomResponse updateDeviceReadingStatusByPacketId(final Integer packetId, final String action, final String updatedBy) {
		CustomResponse response = new CustomResponse();

		if (Constants.QUERY_PARAM_MARK_AS_BASELINE.equalsIgnoreCase(action)) {
			response = updateBaselineStatusForDeviceReading(packetId, updatedBy);
		} else {
			int numberOfRecordsUpdated = 0;
			PreparedStatement pstmt = null;
			try (Connection connection = DBConnection.getConnection();) {
				connection.setAutoCommit(false);

				pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_DEVICE_READING_STATUS_IN_DEVICE_DATA"));
				
				pstmt.setString(1, updatedBy);
				pstmt.setInt(2, packetId);
				

				numberOfRecordsUpdated = pstmt.executeUpdate();
				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}
				connection.setAutoCommit(true);

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
			}
		}
		return response;
	}
	/**
	 * This method will update is_active flag to false in threshold_breach_count table based on
	 * patient_id for clear threshold count functionality
	 *
	 * @param patientId
	 * @return
	 */
	public static CustomResponse updateAlertStatusByPatientId(final int patientId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;
		PreparedStatement pstmt = null;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);
				pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_IS_ACTIVE_ON_THRESHOLD_BREACH_COUNT_BY_PATIENT_ID"));
				pstmt.setInt(1, patientId);
				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

				if (connection != null && !connection.isClosed()) {
					connection.rollback();
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}
	
	/**
	 * This method will update the vitals in patient_vitals table based on
	 * patient_vitals_id
	 *
	 * @param patientVitals
	 * @return
	 */
	public static CustomResponse updatePatientVitals(final PatientVitals patientVitals) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_PATIENT_VITALS_ON_PATIENT_VITALS_ID"));
				if(patientVitals.getPatientVitalsId() != null) {
					
					if (patientVitals.getSystolicBP() != null) {
						pstmt.setFloat(1, patientVitals.getSystolicBP());
					} else {
						pstmt.setNull(1, Types.FLOAT);
					}
					if (patientVitals.getDiastolicBP() != null) {
						pstmt.setFloat(2, patientVitals.getDiastolicBP());
					} else {
						pstmt.setNull(2, Types.FLOAT);
					}
					
					
					if (patientVitals.getWeight() != null) {
						pstmt.setFloat(3, patientVitals.getWeight());
					} else {
						pstmt.setNull(3, Types.FLOAT);
					}
					if (patientVitals.getReadingDate() != null) {
						Timestamp reading_date = new Timestamp(patientVitals.getReadingDate().getTime());
						pstmt.setTimestamp(4, reading_date);
						
					} else {
						pstmt.setNull(4, Types.TIMESTAMP);
					}

					if (patientVitals.getTemperature() != null) {
						pstmt.setFloat(5, patientVitals.getTemperature());
					} else {
						pstmt.setNull(5, Types.FLOAT);
					}

					pstmt.setString(6, patientVitals.getCustomVitals());
					pstmt.setString(7, patientVitals.getUpdatedBy());
					pstmt.setInt(8, patientVitals.getPatientVitalsId());

					numberOfRecordsUpdated = pstmt.executeUpdate();

					if (numberOfRecordsUpdated > 0) {
						response.setStatusCode(Status.OK.getStatusCode());
						response.setDescription(Status.OK.toString());

					} else {
						response.setStatusCode(Status.NO_CONTENT.getStatusCode());
						response.setDescription(Status.NO_CONTENT.toString());
					}

					connection.setAutoCommit(true);
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}
				

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

				if (connection != null && !connection.isClosed()) {
					connection.rollback();
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	
	
	/**
	 * This method will insert the patient vitals into patient_vitals table
	 * 
	 * @param patientVitals
	 * @return
	 */
	public static PatientVitals insertIntoPatientVitals(final PatientVitals patientVitals) {
		PatientVitals patientVitalsResponse = new PatientVitals();
		int numberOfRecordsInserted = 0;
		

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);
				
				PreparedStatement pstmt = connection
							.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_VITALS"));
					pstmt.setInt(1, patientVitals.getPatientId());
					if (patientVitals.getReadingDate() != null) {
						Timestamp reading_date = new Timestamp(patientVitals.getReadingDate().getTime());
						pstmt.setTimestamp(2, reading_date);
						
					} else {
						pstmt.setNull(2, Types.TIMESTAMP);
					}

					/*Timestamp reading_date = new Timestamp(patientVitals.getReadingDate().getTime());
					pstmt.setTimestamp(2, reading_date);*/
					
					if (patientVitals.getSystolicBP() != null) {
						pstmt.setFloat(3, patientVitals.getSystolicBP());
					} else {
						pstmt.setNull(3, Types.FLOAT);
					}
					if (patientVitals.getDiastolicBP() != null) {
						pstmt.setFloat(4, patientVitals.getDiastolicBP());
					} else {
						pstmt.setNull(4, Types.FLOAT);
					}
					if (patientVitals.getWeight() != null) {
						pstmt.setFloat(5, patientVitals.getWeight());
					} else {
						pstmt.setNull(5, Types.FLOAT);
					}

					if (patientVitals.getTemperature() != null) {
						pstmt.setFloat(6, patientVitals.getTemperature());
					} else {
						pstmt.setNull(6, Types.FLOAT);
					}

					pstmt.setString(7, patientVitals.getCustomVitals());
					pstmt.setString(8, patientVitals.getCreatedBy());
					pstmt.setString(9, patientVitals.getCreatedBy());

					numberOfRecordsInserted = pstmt.executeUpdate();

					LOGGER.info("Number of records inserted into patient_vitals table: " + numberOfRecordsInserted);
					// Now return the response back with inserted data
					pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_VITALS_ON_PATIENT_ID"));
					pstmt.setInt(1, patientVitals.getPatientId());
					

					ResultSet rs = pstmt.executeQuery();

					if (rs != null && rs.next()) {
						patientVitalsResponse.setPatientVitalsId(rs.getInt(TableConstants.PATIENT_VITALS_ID));
						patientVitalsResponse.setPatientId(rs.getInt(TableConstants.PATIENT_VITALS_PATIENT_ID));
						patientVitalsResponse.setSystolicBP(rs.getFloat(TableConstants.PATIENT_VITALS_SYSTOLIC_BP));
						patientVitalsResponse.setDiastolicBP(rs.getFloat(TableConstants.PATIENT_VITALS_DIASTOLIC_BP));
						patientVitalsResponse.setWeight(rs.getFloat(TableConstants.PATIENT_VITALS_WEIGHT));
						patientVitalsResponse.setTemperature(rs.getFloat(TableConstants.PATIENT_VITALS_TEMPERATURE));
						patientVitalsResponse.setReadingDate(rs.getDate(TableConstants.DEVICE_DATA_READING_DATE));
						patientVitalsResponse.setCustomVitals(rs.getString(TableConstants.PATIENT_VITALS_CUSTOM_VITALS));
						patientVitalsResponse.setCreatedBy(rs.getString(TableConstants.PATIENT_VITALS_CREATED_BY));
						patientVitalsResponse
								.setCreatedTimeStamp(rs.getTimestamp(TableConstants.PATIENT_VITALS_CREATED_TIMESTAMP));
						patientVitalsResponse.setUpdatedBy(rs.getString(TableConstants.PATIENT_VITALS_UPDATED_BY));
						patientVitalsResponse
								.setUpdatedTimeStamp(rs.getTimestamp(TableConstants.PATIENT_VITALS_UPDATED_TIMESTAMP));
						// now convert the customVitals string to json and store in
						// json array
						ObjectMapper objectMapper = new ObjectMapper();
						List<CustomVitals> customVitalsList = objectMapper.readValue(patientVitals.getCustomVitals(),
								objectMapper.getTypeFactory().constructCollectionType(List.class, CustomVitals.class));
						patientVitals.setCustomVitalsList(customVitalsList);

						patientVitalsResponse.setStatusCode(Status.OK.getStatusCode());
						patientVitalsResponse.setDescription(Status.OK.toString());

						connection.setAutoCommit(true);

					} else {
						patientVitalsResponse.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
						patientVitalsResponse.setDescription(Status.INTERNAL_SERVER_ERROR.toString());

						if (connection != null && !connection.isClosed()) {
							connection.rollback();
						}
					}
				

				
			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
					patientVitalsResponse.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
					patientVitalsResponse.setDescription(
							Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
				} else {
					patientVitalsResponse.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
					patientVitalsResponse
							.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
				}
				if (connection != null && !connection.isClosed()) {
					connection.rollback();
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			patientVitalsResponse.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			patientVitalsResponse.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return patientVitalsResponse;
	}

	
	/**
	 * This method will fetch the latest vitals of specified patient from
	 * patient_vitals table
	 *
	 * @param patientId
	 * @return
	 */
	public static PatientVitals fetchPatientVitalsBasedOnPatientId(final Integer patientId) {
		PatientVitals patientVitals = new PatientVitals();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_VITALS_ON_PATIENT_ID"));
			pstmt.setInt(1, patientId);
			

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				patientVitals.setPatientVitalsId(rs.getInt(TableConstants.PATIENT_VITALS_ID));
				patientVitals.setPatientId(patientId);
				patientVitals.setSystolicBP(rs.getFloat(TableConstants.PATIENT_VITALS_SYSTOLIC_BP));
				patientVitals.setDiastolicBP(rs.getFloat(TableConstants.PATIENT_VITALS_DIASTOLIC_BP));
				patientVitals.setWeight(rs.getFloat(TableConstants.PATIENT_VITALS_WEIGHT));
				patientVitals.setTemperature(rs.getFloat(TableConstants.PATIENT_VITALS_TEMPERATURE));
				patientVitals.setCustomVitals(rs.getString(TableConstants.PATIENT_VITALS_CUSTOM_VITALS));
				patientVitals.setCreatedBy(rs.getString(TableConstants.PATIENT_VITALS_CREATED_BY));
				patientVitals.setCreatedTimeStamp(rs.getTimestamp(TableConstants.PATIENT_VITALS_CREATED_TIMESTAMP));
				patientVitals.setUpdatedBy(rs.getString(TableConstants.PATIENT_VITALS_UPDATED_BY));
				patientVitals.setUpdatedTimeStamp(rs.getTimestamp(TableConstants.PATIENT_VITALS_UPDATED_TIMESTAMP));
				patientVitals.setReadingDate(rs.getDate(TableConstants.DEVICE_DATA_READING_DATE));
				// now convert the customVitals string to json and store in
				// json array
				ObjectMapper objectMapper = new ObjectMapper();
				List<CustomVitals> customVitalsList = objectMapper.readValue(patientVitals.getCustomVitals(),
						objectMapper.getTypeFactory().constructCollectionType(List.class, CustomVitals.class));
				patientVitals.setCustomVitalsList(customVitalsList);

			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientVitals;
	}


	/**
	 * This method will update the requested packet id as baseline reading
	 * and update the previous baseline reaidng as normal reading
	 *
	 * @param packetId
	 * @param updatedBy
	 * @return
	 */
	public static CustomResponse updateBaselineStatusForDeviceReading(final Integer packetId, final String updatedBy) {
		CustomResponse response = new CustomResponse();


		int numberOfRecordsUpdated = 0;
		PreparedStatement pstmt = null;
		try (Connection connection = DBConnection.getConnection();) {
			connection.setAutoCommit(false);



			pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_BASELINE_STATUS_OF_DEVICE_READING_TO_FALSE"));

			pstmt.setString(1, updatedBy);
			pstmt.setInt(2, packetId);
			numberOfRecordsUpdated = pstmt.executeUpdate();
			if (numberOfRecordsUpdated > 0) {
				pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_BASELINE_STATUS_OF_DEVICE_READING_TO_TRUE"));

				pstmt.setString(1, updatedBy);
				pstmt.setInt(2, packetId);
				numberOfRecordsUpdated = pstmt.executeUpdate();
				if(numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				}


			} else {
				response.setStatusCode(Status.NO_CONTENT.getStatusCode());
				response.setDescription(Status.NO_CONTENT.toString());
			}
			connection.setAutoCommit(true);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}
	


}