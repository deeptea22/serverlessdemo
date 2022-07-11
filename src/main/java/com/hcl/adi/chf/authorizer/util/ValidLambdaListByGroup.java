package com.hcl.adi.chf.authorizer.util;

/**
 * This class will return the name of valid lambdas for specified cognito group
 *
 * @author AyushRa
 */
public final class ValidLambdaListByGroup {
	private ValidLambdaListByGroup() {
		// private constructor
	}

	// list of lambda functions for SUPER ADMIN
	public static final String SUPER_ADMIN = "createInstitutionAdminBySA, createSuperAdmin, getAdminByAdminId, "
			+ "getAllActivatedInstitutionList, getDeviceList, getInstitutionDetailsByInstitutionId, "
			+ "getMaximumDetailsOfAdminByEmailId, getPwdPolicyByInstitutionId, "
			+ "listInstitutionAdmins, listInstitutions, listSuperAdmins, persistDeviceInventory, persistInstitution, "
			+ "updateAdmin, updateDataArchivalPolicy, updateInstitution, updatePwdPolicy, updateTermsAndConditions, "
			+ "insertIntoAuditLogs, getDeviceLogByDeviceId, "
			+ "getSignedURLForFirmwareUpload, getSignedURLForFirmwareDownload, updateThresholdPolicyByInstitutionId, "
			+ "getFirmwareLogListForSA, persistFirmwareLogBySA, contactUSRequest, getAuditLogsForSuperAdminByDateFilter, "
			+ "getLatestFirmwareVersion, notifyInstitutionOfFirmwareBySA, persistWearableAndBaseStationMapping, "
			+ "updateFirmwareStatusById, getContactUsSubjectList, getHelpCenterPageTextList, getAuditLogsOfInstitutionByDateFilter, "
			+ "getCompliancePolicyByInstitutionId, updateCompliancePolicy, getReadingDataByPacketId, getPreSignedUrlByDeviceId, "
			+ "getSignedURLForDeviceListUpload, getDeviceListFromBucketAndPersistInMemory"
			+ "getWearableAndBasestationLatestFwSignedURLForDownload";

	// list of lambda functions for INSTITUTION ADMIN
	public static final String INSTITUTION_ADMIN = "createInstitutionAdminByIA, getAdminByAdminId, getAdminByEmailId, "
			+ "getAdminListByInstitutionId, getDeviceMappingByInstitutionId, getInstitutionDetailsByInstitutionId, "
			+ "getPwdPolicyByInstitutionId, mapDeviceToBaseStation, updateAdmin, updateDataArchivalPolicy, "
			+ "updateDeviceMappingStatus, updatePwdPolicy, updateTermsAndConditions, createClinician, "
			+ "getAuditLogsOfInstitutionByDateFilter, getClinicianByEmailId, getClinicianListByInstitutionId, "
			+ "getPatientAccessDashboardByInstitutionId, getUnassignedCliniciansOfInstitutionForSpecifiedPatient, "
			+ "insertIntoAuditLogs, updateClinician, updatePatientClinicianMapping, "
			+ "getIADashboardDataByInstitutionId, getDeviceLogByDeviceId,updateThresholdPolicyByInstitutionId, "
			+ "updateAvailableFirmwareByDeviceId, getDeviceListWithAvailableFWListByInstitutionId, contactUSRequest, persistCHFPatientIdPolicy, "
			+ "RetrieveFHIRLogsByDateFilter, RetrieveFHIRLogsByPatientIdOrOrderIdAndStatus, RetrieveFHIRLogsByPatientIdOrOrderIdOrStatus, "
			+ "generateProtocolConfiguration, getemrconfiguration, persistEMRConfiguration, getContactUsSubjectList, getHelpCenterPageTextList, "
			+ "getCHFPatientIdPolicyByInstitutionId, getCompliancePolicyByInstitutionId, updateCompliancePolicy, getThresholdPolicyByInstitutionId, "
			+ "getReadingDataByPacketId, getPreSignedUrlByDeviceId, getSignedURLForFirmwareUpload, getSignedURLForFirmwareDownload, "
			+ "getSignedURLForDeviceListUpload, getDeviceListFromBucketAndPersistInMemory"
			+ "getWearableAndBasestationLatestFwSignedURLForDownload";

	// list of lambda functions for WEB CLINICIAN
	public static final String WEB_CLINICIAN = "getClinicianAndItsPwdExInfoByEmailIdAndInstitutionSpecificParams, getClinicianByEmailId, "
			+ "getPatientDashboardVitalByInstitutionId, getPatientDetailByInstitutionId, updatePatientClinicianMapping,"
			+ "insertIntoAuditLogs, insertPatientComment, insertPatientThresholds, updatePatientThresholds, getPatientThresholds, getPatientComments, getCPMTrends, "
			+ "getDeviceDataByPatientId, getDeviceDataDetailsByDeviceDataId, getPatientVitalsByPatientId, updatePatientVitals, persistPatientDeviceMapping, "
			+ "getPatientActions, addPatientAction, updatePatientAction, addActionComment, persistPatientVitals, contactUSRequest, typeAheadSearchByMRN, "
			+ "updateAlertStatusByPatientId, getContactUsSubjectList, updateDeviceReadingParametersOnPacketId, restoreDefaultThresholdByPatientId"
			+ " isBaseLineReadingExistsByPatientId, getEcgDataByPatientId, clearThresholdBreachCount, getAvailableReadingsByPatientId, updatePatientMasterOtherMapping,confirmPatient, getPatientProviderDetailsByChfPatientId,"
			+ "getDerivedDataByPatientId, getCHFPatientIdPolicyByInstitutionId, getHelpCenterPageTextList, getAuditLogsOfInstitutionByDateFilter, getCompliancePolicyByInstitutionId, updateCompliancePolicy, getThresholdPolicyByInstitutionId, updateClinician, getReadingDataByPacketId, getPreSignedUrlByDeviceId, getSignedURLForFirmwareUpload, getSignedURLForFirmwareDownload";

	// list of lambda functions for MOBILE CLINICIAN
	public static final String MOBILE_USER = "getCHFPatientIdPolicyByInstitutionId, persistPHIData, getPatientPhiDetailByChfPatientId, "
			+ "verifyDevicesBasedOnSerialNumber, mapDeviceToBaseStation, "
			+ "getClinicianAndItsPwdExInfoByEmailIdAndInstitutionSpecificParams, getClinicianByEmailId, "
			+ "insertIntoAuditLogs, insertPatientComment, insertPatientThresholds, updatePatientThresholds, getPatientThresholds, getPatientComments, getCPMTrends, "
			+ "getDeviceDataByPatientId, getDeviceDataDetailsByDeviceDataId, getPatientVitalsByPatientId, updatePatientVitals, persistPatientDeviceMapping, "
			+ "getPatientActions, addPatientAction, updatePatientAction, addActionComment, persistPatientVitals, contactUSRequest, "
			+ "typeAheadSearchByMRN, getChfPatientIdListByInstitutionIdWithPagination, restoreDefaultThresholdByPatientId "
			+ "getContactUsSubjectList, updateDeviceReadingParametersOnPacketId,clearThresholdBreachCount, isBaseLineReadingExistsByPatientId, "
			+ "getEcgDataByPatientId, getAvailableReadingsByPatientId, updatePatientMasterOtherMapping, removePatientProvider, confirmPatient, getPatientProviderDetailsByChfPatientId, getHelpCenterPageTextList, getAuditLogsOfInstitutionByDateFilter, getCompliancePolicyByInstitutionId, updateCompliancePolicy, getThresholdPolicyByInstitutionId, updateClinician, getReadingDataByPacketId, getPreSignedUrlByDeviceId, getSignedURLForFirmwareUpload, getSignedURLForFirmwareDownload"
			+ "PersistPHIDataFromCSV";
}