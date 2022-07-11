package com.hcl.adi.chf.service;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.dao.ClinicianDashBoardDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DeviceData;
import com.hcl.adi.chf.model.PatientReadingResponse;
import com.hcl.adi.chf.model.PatientVitals;
import com.hcl.adi.chf.model.ThresholdBreachRequest;
import com.hcl.adi.chf.util.Constants;

/**
 * Service class for lambda functions related to clinician dashboard page
 *
 * @author DivyaAg
 */
public final class ClinicianDashboardService {
	private static final Logger LOGGER = LogManager.getLogger(ClinicianDashboardService.class.getName());
	private static AuditLogService auditLogService = new AuditLogService();


	/**
	 * This method will invoke ClinicianDashboardDao to find if
	 * any baseline reading is available for specified patient id
	 *
	 * @param patientId
	 * @return
	 */
	public final CustomResponse isBaseLineExistsByPatientId(final Integer patientId) {
		CustomResponse response = null;

		try {
			response = ClinicianDashBoardDao.fetchReadingStatusBasedOnPatientId(patientId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

		}

		return response;
	}

	/**
	 * This method will invoke ClinicianDashboardDao to update the
	 * status of device reading to based on packet id
	 * @param packetId
	 * @param action
	 * @param updatedBy
	 * @return
	 */
	public final CustomResponse updateDeviceReadingParameter(final Integer packetId, final String action, final String updatedBy) {
		CustomResponse response = null;

		try {
			response = ClinicianDashBoardDao.updateDeviceReadingStatusByPacketId(packetId, action, updatedBy);
			// Now insert record into audit_logs table
			if (Status.OK.getStatusCode() == response.getStatusCode()) {
				if(Constants.QUERY_PARAM_MARK_AS_BASELINE.equalsIgnoreCase(action)) {
					auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(updatedBy,
							Activity.BASELINE_TAG_UPDATED_FOR_DEVICE_READING.name());
				} else {
					auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(updatedBy,
							Activity.REMOVED_FROM_TRENDS.name());
				}

			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

		}

		return response;
	}

	/**
	 * This method will invoke ClinicianDashBoardDao to update the is_active status
	 * on alert_data table on reset breach counts based on patient_id
	 * 
	 *
	 * @param patientId
	 * @param emailId
	 * @param institutionId
	 * @return
	 */
	public final CustomResponse updateAlertStatusByPatientId(final ThresholdBreachRequest thresholdBreachRequest) {
		CustomResponse response = new CustomResponse();

		try {
			response = ClinicianDashBoardDao.updateAlertStatusByPatientId(thresholdBreachRequest.getPatientId());
			if (Status.OK.getStatusCode() == response.getStatusCode()) {
				auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(thresholdBreachRequest.getEmailId(),
						Activity.THRESHOLD_BREACH_COUNT_CLEARED.name());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			response.setDescription(Status.BAD_REQUEST.toString() + ": " + e.getMessage());
		}

		return response;
	}
	/**
	 * This method will invoke ClinicianDashBoardDao to insert the patient
	 * vitals into patient_vitals table
	 *
	 * @param patientVitals
	 * @return
	 */
	public final PatientVitals persistPatientVitals(final PatientVitals patientVitals) {
		PatientVitals patientVitalsResponse = new PatientVitals();

		try {
			if (patientVitals != null) {
				patientVitalsResponse = ClinicianDashBoardDao.insertIntoPatientVitals(patientVitals);

			//	 Now insert record into audit_logs table
			if (Status.OK.getStatusCode() == patientVitalsResponse.getStatusCode()) {
				auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(patientVitals.getCreatedBy(),
									Activity.PATIENT_VITALS_CREATED.name());
			}
			} else {
				patientVitalsResponse.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				patientVitalsResponse.setDescription(Status.BAD_REQUEST.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			patientVitalsResponse.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			patientVitalsResponse.setDescription(Status.BAD_REQUEST.toString() + ": " + e.getMessage());
		}

		return patientVitalsResponse;
	}

	/*
	 * This method will invoke ClinicianDashBoardDao to update the patient
	 * vitals based on patient_vitals_id
	 *
	 * @param patientVitals
	 * @return
	 */
	public final CustomResponse updatePatientVitalsOnPatientVitalsId(final PatientVitals patientVitals) {
		CustomResponse response = new CustomResponse();

		try {
			if (patientVitals != null) {
				response = ClinicianDashBoardDao.updatePatientVitals(patientVitals);

				//Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(patientVitals.getCreatedBy(),
							Activity.PATIENT_VITALS_UPDATED.name());
				}
			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
			}

		}catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			response.setDescription(Status.BAD_REQUEST.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * This method will invoke ClinicianDashboardDao to get latest vitals of
	 * specified patient from patient_vitals table
	 *
	 * @param patientId
	 * @return
	 */
	public final PatientVitals getPatientVitalsByPatientId(final Integer patientId) {
		PatientVitals patientVitals = null;

		try {
			patientVitals = ClinicianDashBoardDao.fetchPatientVitalsBasedOnPatientId(patientId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientVitals;
	}

	/**
	 * This method will invoke ClinicianDashboardDao to get the list of readings
	 * date from device_data table for the specified patient
	 *
	 * @param patientId
	 * @param pageStartIndex
	 * @param pageCount
	 * @return
	 */
	public final PatientReadingResponse getDeviceDataByPatientId(final Integer patientId ,
			final Integer pageStartIndex, final Integer pageCount) {
		PatientReadingResponse patientReadingResponse = null;

		try {
			patientReadingResponse = ClinicianDashBoardDao.fetchDeviceDataBasedOnPatientId(patientId,
					pageStartIndex, pageCount);


		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

		}

		return patientReadingResponse;
	}

	/**
	 * This method will invoke ClinicianDashboardDao to get the ECG
	 * data from derived_data  for 3 latest readings
	 *
	 * @param deviceDataId
	 * @return
	 */
	public final List<DeviceData>  getEcgDataByPatientId(final Integer patientId) {
		List<DeviceData>  ecgList = null;

		try {
			ecgList = ClinicianDashBoardDao.fetchEcgDataBasedOnPatientId(patientId);


		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

		}

		return ecgList;
	}




	/**
	 * This method will invoke ClinicianDashBoardDao to get all the details(ecg,
	 * and heart_sound_url etc.) from device_data table
	 * based on packet id
	 *
	 * @param packetId
	 * @return
	 */
	public final DeviceData getDeviceDataDetailsByPacketId(final Integer packetId) {
		DeviceData deviceData = null;


		try {
			deviceData = ClinicianDashBoardDao.fetchReadingDetailsBasedOnPacketId(packetId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return deviceData;
	}



}