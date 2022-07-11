package com.hcl.adi.chf.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType;
import com.hcl.adi.chf.dao.PatientDao;
import com.hcl.adi.chf.enums.Activity;
import com.hcl.adi.chf.enums.ClinicianType;
import com.hcl.adi.chf.enums.MasterDetailsCode;
import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.ChfPatientIdPolicy.Policy;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.model.ChfPatientSearchRequest;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DashboardVitals;
import com.hcl.adi.chf.model.PatientActionComments;
import com.hcl.adi.chf.model.PatientActions;
import com.hcl.adi.chf.model.PatientClinicianMapping;
import com.hcl.adi.chf.model.PatientComments;
import com.hcl.adi.chf.model.PatientDeviceMapping;
import com.hcl.adi.chf.model.PatientMasterMapping;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.model.PatientRecord;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.util.ChfPatientIdUtil;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.JsonUtil;
import com.hcl.adi.chf.util.PatientJsonMapping;
import com.hcl.adi.chf.util.S3Util;

import net.minidev.json.JSONUtil;

/**
 * Service class for lambda functions related to patient and its dashboard
 *
 * @author DivyaAg
 */
public class PatientService {
	private static final Logger LOGGER = LogManager.getLogger(PatientService.class.getName());
	private static AuditLogService auditLogService = new AuditLogService();

	/**
	 * This method will invoke PatientDao to insert/update the patient provider
	 * details
	 * 
	 * @param patientProvider
	 * @return
	 */
	public final CustomResponse confirmPatient(final PatientProvider patientProvider) {
		CustomResponse customResponse = null;

		try {
			customResponse = PatientDao.confirmPatient(patientProvider);

			// Now insert record into audit_logs table
			if (Status.OK.getStatusCode() == customResponse.getStatusCode()) {
				auditLogService.insertRecordIntoAuditLogs(patientProvider.getInstitutionId(), ClinicianType.CL.name(),
						Activity.PATIENT_CONFIRMED_FROM_MOBILE_APP.name(), patientProvider.getCreatedBy());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return customResponse;
	}

	/**
	 * This method will invoke PatientDao to fetch the patient provider details
	 * 
	 * @param chfPatientId
	 * @return
	 */
	public final PatientProvider getPatientProviderDetailsByChfPatientId(final String chfPatientId) {
		PatientProvider patientProvider = null;

		try {
			patientProvider = PatientDao.fetchPatientProviderByChfPatientId(chfPatientId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientProvider;
	}

	/**
	 * This method will invoke PatientDao to get the details of patient Threshold
	 * value of particular patient by specified institution
	 * 
	 * @param institutionId
	 * @param data
	 * @return
	 */
	public final PatientThreshold getPatientThresholds(final Integer patientId) {
		PatientThreshold patientThreshold = null;

		try {
			patientThreshold = PatientDao.getPatientThresholds(patientId);
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientThreshold;
	}

	/**
	 * This method will invoke PatientDao to restore the default threshold of
	 * patient as per institution threshold policy
	 *
	 * @param patientId
	 * @return
	 */
	public final PatientThreshold restoreDefaultThresholdBasedOnPatientId(final int patientId) {
		PatientThreshold patientThreshold = null;

		try {
			patientThreshold = PatientDao.restoreDefaultThresholdBasedOnPatientId(patientId);
			// Now insert record into audit_logs table
			if (patientThreshold.getCreatedBy() != null) {
				auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(patientThreshold.getCreatedBy(),
						Activity.RESTORE_DEFAULT_THRESHOLD.name());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientThreshold;
	}

	/**
	 * This method will invoke PatientDao to update the Threshold of a patient by
	 * patientId and InstitutionId in DB
	 * 
	 * @param bean
	 * @return
	 */
	public final CustomResponse updatePatientThresholds(final PatientThreshold bean) {
		CustomResponse response = new CustomResponse();

		try {
			if (bean != null && bean.getPatientId() != null) {
				response = PatientDao.updatePatientThresholds(bean);

			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
			response.setDescription(Status.BAD_REQUEST.toString() + ": " + e.getMessage());
		}

		return response;
	}

	
	// ------------------------Code for Mobile App -------------------//

	/**
	 * This method will invoke PatientDao to get the list of patientId, chfPatientId
	 * and mrNumber based on institution id for mobile app
	 *
	 * @param institutionId
	 * @param pageStartIndex
	 * @param pageCount
	 * @return
	 */
	public final ChfPatientListResponseForMobileApp getChfPatientIdListBasedOnInstitutionId(final Integer institutionId,
			final Integer pageStartIndex, final Integer pageCount) {
		ChfPatientListResponseForMobileApp chfPatientListResponseForMobileApp = null;

		try {
			chfPatientListResponseForMobileApp = PatientDao.fetchChfPatientIdListBasedOnInstitutionId(institutionId,
					pageStartIndex, pageCount);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return chfPatientListResponseForMobileApp;
	}

	/**
	 * This method will invoke PatientDao to add patient info in DB
	 *
	 * @param patientPhi
	 * @return
	 */
	public final CustomResponse persistPatientData(final PatientPhi patientPhi) {
		CustomResponse response = new CustomResponse();

		try {
			if (patientPhi != null) {
				response = PatientDao.insertRecordIntoPatientPhi(patientPhi);

			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * This method will invoke PatientDao to identify if specified patient is there
	 * in specified clinician's watchlist or not
	 *
	 * @param patientId
	 * @param clinicianId
	 * @return
	 */
	public final boolean checkPatientClinicianWatchListStatus(final Integer patientId, final Integer clinicianId) {
		boolean isPatientInClinicianWatchlist = false;

		try {
			isPatientInClinicianWatchlist = PatientDao.checkPatientClinicianWatchListStatus(patientId, clinicianId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return isPatientInClinicianWatchlist;
	}

	/**
	 * This method will invoke PatientDao to get the list of all patient record of
	 * specified institution
	 *
	 * @param institutionId
	 * @return
	 */
	public final List<PatientRecord> getPatientDetailByInstitutionId(Map<String, String> input) {
		List<PatientRecord> dataList = new ArrayList<PatientRecord>();

		try {
			if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_INSTITUTION_ID))) {
				dataList = PatientDao.fetchDataByInstitutionId(input);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return dataList;
	}

	/**
	 * This method will invoke PatientDao to get the details of patient dashboard
	 * vitals of specified institution
	 * 
	 * @param input
	 * @param data
	 * @return
	 */
	public final DashboardVitals getPatientDashboardVitalByInstitutionId(Map<String, String> input,
			DashboardVitals data) {
		try {
			if (data != null) {
				PatientDao.getPatientDashboardVitalByInstitutionId(input, data);
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return data;
	}

	/**
	 * This method will invoke removePatientProvider method from PatientDao for soft
	 * delete patient provider
	 * 
	 * @param PatientProvider
	 */
	public final CustomResponse removePatientProvider(PatientProvider patientProvider) {
		CustomResponse response = new CustomResponse();
		try {

			if (patientProvider != null) {
				response = PatientDao.removePatientProvider(patientProvider);

			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * This method will invoke PatientDao to add patient and device mapping
	 *
	 * @param pdm
	 * @return
	 */
	public final CustomResponse persistPatientDeviceMapping(final PatientDeviceMapping pdm) {
		CustomResponse response = new CustomResponse();

		try {
			if (pdm != null && pdm.getChfPatientId() != null && pdm.getDeviceLabelId() != null) {
				response = PatientDao.insertPatientDeviceMapping(pdm);

				// Now insert record into audit_logs table
				if (Status.OK.getStatusCode() == response.getStatusCode()) {
					auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(pdm.getCreatedBy(),
							Activity.PATIENT_DEVICE_MAPPING_CREATED.name());
				}

			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * This method will invoke PatientDao to get the patientPhi details based on
	 * chfPatientId.
	 *
	 * @param chfPatientId
	 * @return
	 */
	public final PatientPhi getPatientPhiDetailsBasedOnChfPatientId(final String chfPatientId) {
		PatientPhi patientPhi = null;

		try {
			patientPhi = PatientDao.fetchPatientPhiDetailsBasedOnChfPatientId(chfPatientId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientPhi;
	}

	/**
	 * This method will invoke PatientDao to update 'status' as CLOSED in
	 * patient_actions and to update 'is_action_open' flag as 0 in patient_phi table
	 * 
	 * @param patientActions
	 * @return
	 */
	public final CustomResponse updatePatientAction(final PatientActions patientActions) {
		CustomResponse response = new CustomResponse();

		try {
			if (patientActions.getActionId() != null && patientActions.getPatientId() != null
					&& patientActions.getUpdatedBy() != null) {
				response = PatientDao.updatePatientAction(patientActions);

			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * This method will invoke PatientDao to add patient action in DB and to update
	 * 'is_action_open' flag as 1 in patient_phi table
	 * 
	 * @param patientActions
	 * @return
	 */
	public final PatientActions addPatientActions(final PatientActions patientActions) {
		PatientActions patientAction = new PatientActions();

		try {
			if (patientActions != null && patientActions.getPatientId() != null && patientActions.getCreatedBy() != null
					&& patientActions.getPriority() != null && patientActions.getSubject() != null) {
				patientAction = PatientDao.addPatientActions(patientActions);

			} else {
				patientAction.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				patientAction.setDescription(Status.BAD_REQUEST.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			patientAction.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			patientAction.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return patientAction;
	}

	/**
	 * This method will invoke PatientDao to persist comments corresponding to a
	 * patient action
	 * 
	 * @param patientActionComments
	 * @return
	 */
	public final PatientActionComments addActionComment(final PatientActionComments patientActionComments) {
		PatientActionComments patientActionComment = new PatientActionComments();

		try {
			if (patientActionComments != null && patientActionComments.getActionId() != null
					&& patientActionComments.getCreatedBy() != null && patientActionComments.getComments() != null) {
				patientActionComment = PatientDao.addActionComment(patientActionComments);

			} else {
				patientActionComment.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				patientActionComment.setDescription(Status.BAD_REQUEST.toString());
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			patientActionComment.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			patientActionComment.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return patientActionComment;
	}

	/**
	 * This method will invoke PatientDao to get the list of comments for the
	 * specified patient
	 * 
	 * @param patientId
	 * @return
	 */
	public final List<PatientComments> getPatientComments(final Integer patientId) {
		List<PatientComments> patientCommentsList = null;

		try {
			patientCommentsList = PatientDao.getPatientComments(patientId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientCommentsList;
	}

	/**
	 * This method will invoke PatientDao to get the list of patient actions and
	 * their comments
	 * 
	 * @param patientId
	 * @return
	 */
	public final PatientActions getPatientActions(final Integer patientId) {
		PatientActions patientActions = null;

		try {
			patientActions = PatientDao.getPatientActions(patientId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return patientActions;
	}

	/**
	 * This method will invoke getPatientMasterOtherMappingList method from
	 * PatientDao to get the medication list of patient master other mapping
	 *
	 * @param institutionId
	 * @param patientId
	 * @return
	 */
	public final PatientMasterMapping getPatientMedicationList(final Integer institutionId, final Integer patientId) {
		PatientMasterMapping data = new PatientMasterMapping();
		try {
			Map<String, Integer> masterTypeCodeMap = new HashMap<String, Integer>();
			masterTypeCodeMap.put(MasterDetailsCode.MED.toString(), 0);

			data = PatientDao.getPatientMasterOtherMappingList(institutionId, patientId, masterTypeCodeMap);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return data;
	}

	/**
	 * This method will invoke getPatientMasterOtherMappingList method from
	 * PatientDao to get the Comorbidities list of patient master other mapping
	 *
	 * @param institutionId
	 * @param patientId
	 * @return
	 */
	public final PatientMasterMapping getPatientComorbiditiesList(final Integer institutionId,
			final Integer patientId) {
		PatientMasterMapping data = new PatientMasterMapping();
		try {
			Map<String, Integer> masterTypeCodeMap = new HashMap<String, Integer>();
			masterTypeCodeMap.put(MasterDetailsCode.COM.toString(), 0);

			data = PatientDao.getPatientMasterOtherMappingList(institutionId, patientId, masterTypeCodeMap);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return data;
	}

	/**
	 * This method will invoke getPatientMasterOtherMappingList method from
	 * PatientDao to get the Patient History list of patient master other mapping
	 *
	 * @param institutionId
	 * @param patientId
	 * @return
	 */
	public final PatientMasterMapping getPatientHistoryList(final Integer institutionId, final Integer patientId) {
		PatientMasterMapping data = new PatientMasterMapping();
		try {
			Map<String, Integer> masterTypeCodeMap = new HashMap<String, Integer>();
			masterTypeCodeMap.put(MasterDetailsCode.IMP.toString(), 0);
			masterTypeCodeMap.put(MasterDetailsCode.ADM.toString(), 0);
			masterTypeCodeMap.put(MasterDetailsCode.PRO.toString(), 0);

			data = PatientDao.getPatientMasterOtherMappingList(institutionId, patientId, masterTypeCodeMap);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return data;
	}

	/**
	 * This method will invoke updatePatientMasterOtherMapping method for Insert,
	 * Delete the All Medication, Comoborties, Patient History and there respective
	 * Other.
	 * 
	 * @param PatientMasterMapping
	 */
	public final CustomResponse updatePatientMasterOtherMapping(PatientMasterMapping patientMasterMapping) {
		CustomResponse response = new CustomResponse();
		try {

			if (patientMasterMapping != null) {
				response = PatientDao.updatePatientMasterOtherMapping(patientMasterMapping);
			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * This method will invoke PatientDao to get the list of patientId, chfPatientId
	 * and mrNumber based on institution id and specific column(pattern based
	 * search) for mobile app
	 *
	 * @param chfPatientSearchRequest
	 * @return
	 */
	public final ChfPatientListResponseForMobileApp getChfPatientIdListBySearchParamWithPagination(
			final ChfPatientSearchRequest chfPatientSearchRequest) {
		ChfPatientListResponseForMobileApp chfPatientListResponseForMobileApp = null;

		try {
			chfPatientListResponseForMobileApp = PatientDao
					.getChfPatientIdListBySearchParamWithPagination(chfPatientSearchRequest);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return chfPatientListResponseForMobileApp;
	}

	/**
	 * This method will invoke PatientDao to add comment of patient By patientId and
	 * InstitutionId in DB
	 *
	 * @param bean
	 * @return
	 */
	public final PatientComments savePatientComment(final PatientComments bean) {
		PatientComments response = new PatientComments();
		try {
			if (bean != null && bean.getPatientId() != null) {
				LOGGER.info("saveThresholdOfPatientByInstitutionId 2: " + bean.getComment());
				response = PatientDao.savePatientComment(bean);
			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
			}
			// Now insert record into audit_logs table
			if (Status.OK.getStatusCode() == response.getStatusCode()) {
				auditLogService.fetchClinicianAndInsertRecordIntoAuditLogs(bean.getCreatedBy(),
						Activity.PATIENT_COMMENT_CREATED.name());
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
	 * This method will invoke PatientDao to add/delete a specified patient from
	 * specified clinician's watchlist
	 *
	 * @param pcm
	 * @return
	 */
	public final CustomResponse updatePatientClinicianMapping(final PatientClinicianMapping pcm) {
		CustomResponse response = new CustomResponse();

		try {
			if (pcm != null && pcm.getAction() != null && pcm.getPatientId() != null && pcm.getClinicianId() != null) {
				response = PatientDao.updatePatientClinicianMapping(pcm);

			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * This method will invoke PatientDao to get the details of CPM of particular
	 * patient record
	 *
	 * @param patientId
	 * @return
	 */
	public final PatientRecord getCPMTrends(final String patientId) {
		PatientRecord data = new PatientRecord();
		try {
			data = PatientDao.getCPMTrends(patientId);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return data;
	}

	/**
	 * This method will invoke PatientDao to de-enroll a patient based on chfPatientId
	 * by marking paramater delete_marker to 'Y'
	 * @param chfPatientId
	 * @param updatedBy
	 * @return
	 */
	public final CustomResponse deEnrollPatientBasedOnChfPatientId(final String chfPatientId, final String updatedBy) {
		CustomResponse response = new CustomResponse();

		try {
			if (chfPatientId != null) {
				response = PatientDao.deEnrollPatientByChfPatientId(chfPatientId, updatedBy);
				// Curently no audit logs are mentioned for this
				// as the source of this API is unidentified

			} else {
				response.setStatusCode(Status.NO_CONTENT.getStatusCode());
				response.setDescription(Status.NO_CONTENT.toString());
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
	 * This method will invoke PatientDao to add patient info in DB after required
	 * validations.
	 *
	 * @param patientPhi
	 * @return CustomResponse
	 */
	public final CustomResponse persistPatientDataFromCSV(final Map<String, String> input) {
		CustomResponse response = new CustomResponse();
		String createdBy = input.get(Constants.CREATED_BY);
		String bucketName = Constants.S3_BUCKET_NAME;
		String absolutefileName = input.get(Constants.FILE_OBJECT_KEY);
		int institutionId = Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID));

		StringBuilder absolutefileNameBuilder = new StringBuilder(absolutefileName);
		int index = absolutefileNameBuilder.lastIndexOf("/");
		List<String> mandatoryColumns = Arrays.asList("gender", "firstName", "lastName", "doB", "contactNo", "ssn");
		List<String> jsonFields = new ArrayList<String>();

		try {

			ChfPatientIdPolicyService chfPatientIdPolicyServiceObj = new ChfPatientIdPolicyService();
			ChfPatientIdPolicy chfIdPolicy = chfPatientIdPolicyServiceObj
					.getChfPatientIdPolicyByInstitutionId(institutionId);
			List<Policy> policyList = chfIdPolicy.getPolicyList();
			policyList.sort(new Comparator<Policy>() {

				@Override
				public int compare(Policy o1, Policy o2) {
					return o1.getLabelSequence() - o2.getLabelSequence();
				}
			});

			// Add all policy related fields in mandatory columms list and also in jsonField
			// list.
			for (Policy policy : chfIdPolicy.getPolicyList()) {
				switch (policy.getLabel()) {
				case "Place of birth":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("Place of birth") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("Place of birth") + "");
					break;
				case "6-digit geo code (long + lat)":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("6-digit geo code (long + lat)") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("6-digit geo code (long + lat)") + "");
					break;
				case "Enterprise Specific Number":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("Enterprise Specific Number") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("Enterprise Specific Number") + "");
					break;
				case "World Health Org T:Id":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("World Health Org T:Id") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("World Health Org T:Id") + "");
					break;
				case "Mother’s maiden name":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("Mother’s maiden name") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("Mother’s maiden name") + "");
					break;
				case "Unique Alpha Numeric Number":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("Unique Alpha Numeric Number") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("Unique Alpha Numeric Number") + "");
					break;
				case "Practice Specific":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("Practice Specific") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("Practice Specific") + "");
					break;
				case "Location Specific Number":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("Location Specific Number") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("Location Specific Number") + "");
					break;
				case "Zipcode":
					mandatoryColumns.add(PatientJsonMapping.getDbColumnName("Zipcode") + "");
					jsonFields.add(PatientJsonMapping.getDbColumnName("Zipcode") + "");
					break;
				default:
					break;
				}
			}

			// extract FileName from absolute file name
			String fileName = null;
			if (index < 0) {
				fileName = absolutefileNameBuilder.toString();
			} else {
				fileName = absolutefileNameBuilder.substring(index + 1, absolutefileNameBuilder.length());
			}
			InputStreamReader inputCSV = new InputStreamReader(
					S3Util.getFileContentsAsInputStreamFromS3(bucketName, fileName));

			CsvSchema schema = CsvSchema.builder().addColumns(mandatoryColumns, ColumnType.STRING).setUseHeader(true)
					.build();

			schema = schema.withColumnSeparator(',').withNullValue("NULL")
					.withoutEscapeChar();
			CsvMapper mapper = new CsvMapper();

			// ignore extra columns in input csv
			mapper.configure(com.fasterxml.jackson.dataformat.csv.CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE, true);
			mapper.configure(com.fasterxml.jackson.dataformat.csv.CsvParser.Feature.TRIM_SPACES, true);
			List<PatientPhi> patientList = new ArrayList<PatientPhi>();
			ObjectReader r = mapper.readerFor(PatientPhi.class).with(schema);

			// Parse CSV to patient list
			try (Reader reader = inputCSV) {
				MappingIterator<PatientPhi> mi = r.readValues(reader);
				while (mi.hasNext()) {
					patientList.add(mi.next());
				}
			}

			// Validating Patient List and Logging incorrect/Duplicate patients in input csv
			patientList = validatePatient(patientList, mandatoryColumns);

			for (PatientPhi patient : patientList) {
				patient.setInstitutionId(institutionId);
				patient.setCreatedBy(createdBy);
				patient.setUpdatedBy(createdBy);
				patient.setChfPatientId(ChfPatientIdUtil.getChfPatientId(patient, chfIdPolicy));
				if (CollectionUtils.isNotEmpty(jsonFields)) {
					populateJsoninPatientObject(patient, jsonFields);
				}else {
					patient.setPatientDetailsJson("{}");
				}
			}
			// Fetch all chfIdList for current institution
			List<String> chfIdList = PatientDao.getChfPatientIDList(institutionId);

			// check Availability of generated CHFId and generate a new one because of
			// unavailability
			for (PatientPhi patient : patientList) {
				if (chfIdList.contains(patient.getChfPatientId())) {
					patient.setChfPatientId(findNextAvailableChfId(patient.getChfPatientId(), chfIdList));
				}
			}

			if (CollectionUtils.isNotEmpty(patientList)) {
				response = PatientDao.insertBulkRecordIntoPatientPhi(patientList);
			} else {
				response.setStatusCode(Status.BAD_REQUEST.getStatusCode());
				response.setDescription(Status.BAD_REQUEST.toString());
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
	 * Validate PatientList
	 * 
	 * @param patientList
	 * @return Valid Patient List
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private List<PatientPhi> validatePatient(List<PatientPhi> patientList, List<String> mandatoryColumns)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException {
		LOGGER.info("::: Validating CSV uploaded patient list ::: ");
		List<PatientPhi> returnPatientList = new ArrayList<PatientPhi>();
		List<PatientPhi> invalidPatientList = new ArrayList<PatientPhi>();
		List<PatientPhi> duplicatePatientList = new ArrayList<PatientPhi>();
		Class cls = Class.forName("com.hcl.adi.chf.model.PatientPhi");
		Class noparams[] = {};

		for (PatientPhi patient : patientList) {
			Boolean isInValid = false;
			for (String column : mandatoryColumns) {
				Method method = cls.getDeclaredMethod(
						"get" + column.substring(0, 1).toUpperCase() + column.substring(1), noparams);
				Object obj = method.invoke(patient, null);
				if (obj == null || StringUtils.isEmpty(method.invoke(patient, null).toString())) {
					isInValid = true;
					break;
				}
			}
			if (isInValid) {
				invalidPatientList.add(patient);
			} else {
				// Check in DB to find already existing Duplicate patient entry.
				if (PatientDao.checkForDuplicatePatient(patient) == 0) {
					returnPatientList.add(patient);
				} else {
					duplicatePatientList.add(patient);
				}

			}
		}
		LOGGER.info(invalidPatientList.size()+" Invalid Patients entry in uploaded CSV are " + invalidPatientList);
		LOGGER.info(duplicatePatientList.size()+" Duplicate Patients entry in uploaded CSV are " + duplicatePatientList);
		return returnPatientList;
	}

	/**
	 * findNextAvailableChfId
	 * 
	 * @param s
	 */
	private static String findNextAvailableChfId(String patientChfId, List<String> chfIdList) {
		int i = 0;
		String str = patientChfId;
		boolean flag = true;
		while (flag) {
			if (chfIdList.contains(str)) {
				i++;
				str = patientChfId + "_" + i;
			} else {
				flag = false;
			}
		}
		return str;
	}

	/**
	 * Populate Json Map and Json String in Patient Object.
	 * 
	 * @param patientPhi
	 * @param jsonFields
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 */
	private static void populateJsoninPatientObject(PatientPhi patientPhi, List<String> jsonFields)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException {
		Class cls = Class.forName("com.hcl.adi.chf.model.PatientPhi");
		Class noparams[] = {};
		Map<String, String> jsonMap = new HashMap<String, String>();
		for (String field : jsonFields) {
			System.out.println("get" + field.substring(0, 1).toUpperCase() + field.substring(1));
			Method method = cls.getDeclaredMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1),
					noparams);
			Object obj = method.invoke(patientPhi, null);
			if (obj == null || StringUtils.isEmpty(method.invoke(patientPhi, null).toString())) {
				jsonMap.put(field, obj.toString());
			}
			patientPhi.setPatientDetailsJsonMap(jsonMap);
			patientPhi.setPatientDetailsJson(JsonUtil.mapToJsonString(jsonMap));
		}

	}
}