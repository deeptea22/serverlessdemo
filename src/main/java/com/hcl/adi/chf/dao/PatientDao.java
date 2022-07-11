package com.hcl.adi.chf.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.adi.chf.enums.MasterDetailsCode;
import com.hcl.adi.chf.enums.WatchlistAction;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp;
import com.hcl.adi.chf.model.ChfPatientListResponseForMobileApp.ChfPatientId;
import com.hcl.adi.chf.model.ChfPatientSearchRequest;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DashboardVitals;
import com.hcl.adi.chf.model.PatientActionComments;
import com.hcl.adi.chf.model.PatientActions;
import com.hcl.adi.chf.model.PatientClinicianMapping;
import com.hcl.adi.chf.model.PatientComments;
import com.hcl.adi.chf.model.PatientDeviceMapping;
import com.hcl.adi.chf.model.PatientMasterMapping;
import com.hcl.adi.chf.model.PatientMasterOtherMappings;
import com.hcl.adi.chf.model.PatientPhi;
import com.hcl.adi.chf.model.PatientPhi.PatientAttributes;
import com.hcl.adi.chf.model.PatientProvider;
import com.hcl.adi.chf.model.PatientProvider.Provider;
import com.hcl.adi.chf.model.PatientRecord;
import com.hcl.adi.chf.model.PatientThreshold;
import com.hcl.adi.chf.model.PatientThreshold.Threshold;
import com.hcl.adi.chf.model.Properties;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.PatientJsonMapping;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on patient_phi table
 *
 * @author AyushRa
 */
public final class PatientDao {
    private static final Logger LOGGER = LogManager.getLogger(PatientDao.class.getName());

    private PatientDao() {
        // private constructor
    }
    /**
     * This method will save the details of patient and healthcare provdier
     * in patient_provider_mapping and healthcare_provider tables
     * 
     * @param patientProvider
     * @return
     * @throws Exception
     */
    public static CustomResponse confirmPatient(final PatientProvider patientProvider) {
        CustomResponse response = new CustomResponse();
        int numberOfRecordsInserted = 0;
        PreparedStatement pstmt;
        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);
                if(patientProvider != null && !patientProvider.getProviders().isEmpty()) {
                    for(Provider provider : patientProvider.getProviders() ) {
                        // save the new provider details
                        if(provider.getProviderId() == 0){
                            pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_PROVIDER"),
                                    Statement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(1, patientProvider.getInstitutionId());
                            pstmt.setString(2, provider.getProviderName());
                            pstmt.setString(3, provider.getHospitalAffilation());
                            pstmt.setString(4, provider.getDoctorName());
                            pstmt.setString(5, provider.getDoctorContactNumber());
                            pstmt.setString(6, provider.getDoctorEmail());
                            pstmt.setString(7, provider.getOtherContactNumber());
                            pstmt.setString(8, patientProvider.getCreatedBy());
                            numberOfRecordsInserted = pstmt.executeUpdate();
                            ResultSet generatedKeys = pstmt.getGeneratedKeys();
                            if (numberOfRecordsInserted > 0 && generatedKeys.next()) {
                                Integer providerId = generatedKeys.getInt(1);
                                pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_PROVIDER_MAPPING"));
                                pstmt.setInt(1, providerId);
                                pstmt.setString(2, patientProvider.getChfPatientId());
                                pstmt.setBoolean(3, provider.isDefault());
                                pstmt.setString(4, patientProvider.getCreatedBy());
                                pstmt.setString(5, patientProvider.getCreatedBy());

                                numberOfRecordsInserted = pstmt.executeUpdate();
                                if (numberOfRecordsInserted > 0) {
                                    response.setStatusCode(Status.OK.getStatusCode());
                                    response.setDescription(Status.OK.toString());
                                    LOGGER.info(
                                            "Number of records inserted into healthcare_provider table: " + numberOfRecordsInserted);
                                } else {
                                    response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                                    response.setDescription(Status.INTERNAL_SERVER_ERROR.toString());
                                }
                            }
                        } else {
                            // update the existing provider details
                            pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_HEALTHCARE_PROVIDER_BY_PROVIDER_ID"));

                            pstmt.setString(1, provider.getProviderName());
                            pstmt.setString(2, provider.getHospitalAffilation());
                            pstmt.setString(3, provider.getDoctorName());
                            pstmt.setString(4, provider.getDoctorContactNumber());
                            pstmt.setString(5, provider.getDoctorEmail());
                            pstmt.setString(6, provider.getOtherContactNumber());
                            pstmt.setString(7, patientProvider.getUpdatedBy());
                            pstmt.setInt(8, provider.getProviderId());
                            numberOfRecordsInserted = pstmt.executeUpdate();

                            if (numberOfRecordsInserted > 0) {
                                response.setStatusCode(Status.OK.getStatusCode());
                                response.setDescription(Status.OK.toString());
                                LOGGER.info(
                                        "Number of records updated in healthcare_provider"
                                                + ": " + numberOfRecordsInserted);
                            } else {
                                response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                                response.setDescription(Status.INTERNAL_SERVER_ERROR.toString());
                            }
                        }
                    }

                }
                connection.setAutoCommit(true);
            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

                if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
                    response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
                    response.setDescription(
                            Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
                } else {
                    response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                    response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
                }

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
     * This method will fetch the patient provider details
     * based on chfPatientId
     * 
     * @param chfPatientId
     * @return
     * @throws Exception
     */
    public static PatientProvider fetchPatientProviderByChfPatientId(final String chfPatientId) throws Exception {
        PatientProvider patientProvider = new PatientProvider();
        List<Provider> providers = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();) {

            PreparedStatement pstmt = connection
                    .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_PROVIDER_MAPPING_ON_CHF_PATIENT_ID"));
            pstmt.setString(1, chfPatientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {

                    Provider providerObj = new Provider();


                    providerObj.setProviderId(rs.getInt(TableConstants.PATIENT_PROVIDER_ID));
                    providerObj.setDefault(rs.getBoolean(TableConstants.IS_PROVIDER_DEFAULT));
                    providerObj.setDoctorName(rs.getString(TableConstants.PROVIDER_DOCTOR_NAME));
                    providerObj.setDoctorEmail(rs.getString(TableConstants.PROVIDER_DOCTOR_EMAIL));
                    providerObj.setOtherContactNumber(rs.getString(TableConstants.PROVIDER_OTHER_CONTACT_NUMBER));
                    providerObj.setHospitalAffilation(rs.getString(TableConstants.HOSPITAL_AFFILATION));
                    providerObj.setProviderName(rs.getString(TableConstants.PROVIDER_NAME));
                    providerObj.setDoctorContactNumber(rs.getString(TableConstants.PROVIDER_CONTACT_NO));

                    providers.add(providerObj);
                    patientProvider.setChfPatientId(rs.getString(TableConstants.CHF_PATIENT_ID));
                    patientProvider.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
                }

                patientProvider.setProviders(providers);
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }
        return patientProvider;
    }

    /**
     * This method will return patient threshold data by patientId
     * 
     * @param patientId
     * @return
     * @throws Exception
     */
    public static PatientThreshold getPatientThresholds(final Integer patientId) throws Exception {
        PatientThreshold patientThreshold = new PatientThreshold();

        try (Connection connection = DBConnection.getConnection();) {

            PreparedStatement pstmt = connection
                    .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_THRESHOLD_ON_PATIENT_ID"));
            pstmt.setInt(1, patientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    patientThreshold.setThresholdId(rs.getInt(TableConstants.PATIENT_THRESHOLD_ID));
                    patientThreshold.setPatientId(rs.getInt(TableConstants.PATIENT_THRESHOLD_PATIENT_ID));
                    String threshold = rs.getString(TableConstants.PATIENT_THRESHOLD_VALUES);

                    // now convert the threshold values string to json
                    ObjectMapper objectMapper = new ObjectMapper();
                    TypeReference<List<Threshold>> typeReference = new TypeReference<List<Threshold>>() {
                    };
                    List<Threshold> thresholdValues = objectMapper.readValue(threshold, typeReference);

                    patientThreshold.setThreshold(thresholdValues);

                }
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }
        return patientThreshold;
    }

    /**
     * This method will update the patient threshold to default threshold values
     * based on default threshold policy of institution
     *
     * @param patientId
     * @return
     */
    public static PatientThreshold restoreDefaultThresholdBasedOnPatientId(final int patientId) {
        PatientThreshold patientThreshold = new PatientThreshold();
        PreparedStatement pstmt = null;
        int numberOfRecordsUpdated = 0;

        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);
                pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("RESTORE_DEFAULT_THRESHOLD_ON_PATIENT_ID"));

                pstmt.setInt(1, patientId);
                pstmt.setInt(2, patientId);
                numberOfRecordsUpdated = pstmt.executeUpdate();

                if (numberOfRecordsUpdated > 0) {
                    patientThreshold = getPatientThresholds(patientId);
                } else {
                    patientThreshold.setStatusCode(Status.NO_CONTENT.getStatusCode());
                    patientThreshold.setDescription(Status.NO_CONTENT.toString());
                }
                connection.setAutoCommit(true);
            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
                if (connection != null && !connection.isClosed()) {
                    connection.rollback();
                }
            }
        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
            patientThreshold.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
            patientThreshold.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

        }
        return patientThreshold;
    }

    /**
     * This method will update(add/delete) the mapping of clinicians for specified
     * patient
     *
     * @param bean
     * @return
     */
    public static CustomResponse updatePatientThresholds(final PatientThreshold bean) {
        CustomResponse response = new CustomResponse();
        int numberOfRecordsUpdated = 0;
        PreparedStatement pstmt = null;

        try (Connection connection = DBConnection.getConnection();) {
            connection.setAutoCommit(false);
            

            if (bean.getThresholdId() != null && bean.getPatientId() != null && bean.getThreshold() != null) {
                pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_PATIENT_THRESHOLD"));
                String threshold = new ObjectMapper().writeValueAsString(bean.getThreshold());
                pstmt.setString(1, threshold);
                pstmt.setString(2, bean.getUpdatedBy());
                pstmt.setInt(3, bean.getPatientId());

                numberOfRecordsUpdated = pstmt.executeUpdate();
                if (numberOfRecordsUpdated > 0) {
                    response.setStatusCode(Status.OK.getStatusCode());
                    response.setDescription(Status.OK.toString());
                } else {
                    response.setStatusCode(Status.NO_CONTENT.getStatusCode());
                    response.setDescription(Status.NO_CONTENT.toString());
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

    // ----------------------code for Mobile App-------------------
    /**
     * This method will fetch the list of patientId, chfPatientId and mrNumber from
     * patient_phi table based on institution_id for mobile app to show patient list
     * 
     * @param institutionId
     * @param pageStartIndex
     * @param pageCount
     * @return
     */

    public static ChfPatientListResponseForMobileApp fetchChfPatientIdListBasedOnInstitutionId(
            final Integer institutionId, final Integer pageStartIndex, final Integer pageCount) {
        ChfPatientListResponseForMobileApp chfPatientListResponseForMobileApp = new ChfPatientListResponseForMobileApp();
        List<ChfPatientId> chfPatientIdList = new ArrayList<ChfPatientId>();
        int patientCount = 0;

        try (Connection connection = DBConnection.getConnection();) {
            PreparedStatement pstmt = connection
                    .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_PHI_BASED_ON_INSTITUTION_ID"));
            pstmt.setInt(1, institutionId);
            pstmt.setInt(2, pageStartIndex);
            pstmt.setInt(3, pageCount);

            ResultSet rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    ChfPatientId chfPatientId = new ChfPatientId();
                    chfPatientId.setPatientId(rs.getInt(TableConstants.PATIENT_ID));
                    chfPatientId.setChfPatientId(rs.getString(TableConstants.CHF_PATIENT_ID));
                    chfPatientId.setMrNumber(rs.getString(TableConstants.PATIENT_MR_NO));
                    chfPatientId.setGender(rs.getString(TableConstants.PATIENT_GENDER));
                    chfPatientId.setFirstName(rs.getString(TableConstants.PATIENT_FIRST_NAME));
                    chfPatientId.setLastName(rs.getString(TableConstants.PATIENT_LAST_NAME));
                    chfPatientId.setContactNo(rs.getString(TableConstants.PATIENT_CONTACT_NO));
                    chfPatientId.setDob(rs.getString(TableConstants.PATIENT_DOB));


                    chfPatientIdList.add(chfPatientId);
                }
            }

            // Now count total number of patients of the institution
            pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("COUNT_NO_OF_PATIENTS_BASED_ON_INSTITUTION_ID"));
            pstmt.setInt(1, institutionId);

            rs = pstmt.executeQuery();

            if (rs != null && rs.next()) {
                patientCount = rs.getInt(TableConstants.PATIENT_COUNT);
            }

            chfPatientListResponseForMobileApp.setChfPatientIdList(chfPatientIdList);
            chfPatientListResponseForMobileApp.setPatientCount(patientCount);

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return chfPatientListResponseForMobileApp;
    }
    
    /**
     * Insert record into patient_phi table
     *
     * @param patientPhi
     * @return
     */
    public static CustomResponse insertRecordIntoPatientPhi(final PatientPhi patientPhi) {
        CustomResponse response = new CustomResponse();
        int numberOfRecordsInserted = 0;
        String defaultPatientThreshold = null;

        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);
                // first fetch the threshold policy of institution to save it as patient default
                // threshold
                PreparedStatement pstmt = connection
                        .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_THRESHOLD_POLICY_BY_INSTITUTION_ID"));
                pstmt.setInt(1, patientPhi.getInstitutionId());

                ResultSet rs = pstmt.executeQuery();

                if (rs != null && rs.next()) {
                    defaultPatientThreshold = rs.getString(TableConstants.THRESHOLD_POLICY);
                }
                if (defaultPatientThreshold != null) {
                    pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_PHI"),
                            Statement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, patientPhi.getChfPatientId());
                    pstmt.setInt(2, patientPhi.getInstitutionId());
                    if (patientPhi.getMrNumber() != null) {
                        pstmt.setString(3, patientPhi.getMrNumber());
                    } else {
                        pstmt.setNull(3, Types.NULL);
                    }
                    if (patientPhi.getPatientDetailsJson() != null) {
                        
                        pstmt.setString(4, patientPhi.getPatientDetailsJson());
                    } else {
                        pstmt.setNull(4, Types.NULL);
                    }
                    

                    
                    pstmt.setString(5, patientPhi.getCreatedBy());
                    pstmt.setString(6, patientPhi.getCreatedBy());
                    if (patientPhi.getFirstName() != null) {
                        pstmt.setString(7, patientPhi.getFirstName());
                    } else {
                        pstmt.setNull(7, Types.NULL);
                    }
                    if (patientPhi.getLastName() != null) {
                        pstmt.setString(8, patientPhi.getLastName());
                    } else {
                        pstmt.setNull(8, Types.NULL);
                    }
                    if (patientPhi.getGender() != null) {
                        pstmt.setString(9, patientPhi.getGender());
                    } else {
                        pstmt.setNull(9, Types.NULL);
                    }
                    if (patientPhi.getContactNo() != null) {
                        pstmt.setString(10, patientPhi.getContactNo());
                    } else {
                        pstmt.setNull(10, Types.NULL);
                    }
                    if (patientPhi.getDoB() != null) {
                        pstmt.setString(11, patientPhi.getDoB());
                    } else {
                        pstmt.setNull(11, Types.NULL);
                    }
                    if (patientPhi.getAddress() != null) {
                        pstmt.setString(12, patientPhi.getAddress());
                    } else {
                        pstmt.setNull(12, Types.NULL);
                    }
                    if (patientPhi.getZip() != null) {
                        pstmt.setString(13, patientPhi.getZip());
                    } else {
                        pstmt.setNull(13, Types.NULL);
                    }
                    if (patientPhi.getSsn() != null) {
                        pstmt.setString(14, patientPhi.getSsn());
                    } else {
                        pstmt.setNull(14, Types.NULL);
                    }
                    
                    numberOfRecordsInserted = pstmt.executeUpdate();
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();

                    if (numberOfRecordsInserted > 0 && generatedKeys.next()) {
                        Integer patientId = generatedKeys.getInt(1);
                        pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_THRESHOLD"));
                        pstmt.setInt(1, patientId);
                        pstmt.setString(2, defaultPatientThreshold);
                        pstmt.setString(3, patientPhi.getCreatedBy());
                        pstmt.setString(4, patientPhi.getCreatedBy());

                        numberOfRecordsInserted = pstmt.executeUpdate();
                        if (numberOfRecordsInserted > 0 && generatedKeys.next()) {
                            response.setStatusCode(Status.OK.getStatusCode());
                            response.setDescription(Status.OK.toString());
                            LOGGER.info(
                                    "Number of records inserted into patient_phi table: " + numberOfRecordsInserted);
                        }
                    }
                } else {
                    response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                    response.setDescription(Status.INTERNAL_SERVER_ERROR.toString());
                }
                connection.setAutoCommit(true);

            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

                if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
                    response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
                    response.setDescription(
                            Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
                } else {
                    response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                    response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
                }

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
     * This method will check whether patient-clinician mapping is there in DB
     * or not. In other words, this method will identify if specified patient is
     * there in specified clinician's watchlist or not. If specified patient
     * would be in specified clinician's watchlist, then, this method will
     * return true else will return false
     *
     * @param patientId
     * @param clinicianId
     * @return
     */
    public static boolean checkPatientClinicianWatchListStatus(final Integer patientId, final Integer clinicianId) {
        try (Connection connection = DBConnection.getConnection();) {
            PreparedStatement pstmt = connection
                    .prepareStatement(SQLUtils.getSQLQuery("SELECT_PATIENT_CLINICIAN_WATCHLIST_STATUS"));
            pstmt.setInt(1, patientId);
            pstmt.setInt(2, clinicianId);

            ResultSet rs = pstmt.executeQuery();

            if (rs != null && rs.next()) {
                return true;

            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return false;
    }

    /**
     * This method will provide list of patient data by institutionId
     *
     * @param input
     * @return
     * @throws SQLException
     */
    public static List<PatientRecord> fetchDataByInstitutionId(final Map<String, String> input) throws Exception {
        List<PatientRecord> dataList = new ArrayList<PatientRecord>();

        try (Connection connection = DBConnection.getConnection();) {
            StringBuilder query = new StringBuilder();
            query.append(SQLUtils.getSQLQuery("SELECT_PATIENT_RECORD_BY_INSTITUTION"));
            
            if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_PATIENT_ID))) {
                query.append(Constants._BLANK);
                query.append(SQLUtils.getSQLQuery("SELECT_PATIENT_RECORD_BY_INSTITUTION_AND_PATIENTCON"))
                .append(input.get(Constants.QUERY_PARAM_PATIENT_ID)).append(Constants._SINGLE_QUOATE);
            }
            if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_PATIENT_WATCHLIST))) {
                query.append(Constants._BLANK);
                query.append(SQLUtils.getSQLQuery("SELECT_PATIENT_RECORD_BY_INSTITUTION_AND_PATIENT_WL_CON"))
                .append(input.get(Constants.QUERY_PARAM_CLINICIAN_ID)).append(") ");
            }

            if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_PATIENT_THRESHOLDS))) {
                query.append(Constants._BLANK);
                query.append(SQLUtils.getSQLQuery("SELECT_PATIENT_RECORD_BY_INSTITUTION_AND_TH_CON"));
            }

            if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_PATIENT_ACTIONS))) {
                query.append(Constants._BLANK);
                query.append(SQLUtils.getSQLQuery("SELECT_PATIENT_RECORD_BY_INSTITUTION_AND_PATIENT_ACTIONS_CON"));
            }

            if (StringUtils.isNotBlank(input.get(Constants.QUERY_PARAM_NEW_READINGS))) {
                query.append(Constants._BLANK);
                query.append(SQLUtils.getSQLQuery("SELECT_PATIENT_RECORD_BY_INSTITUTION_AND_PATIENT_LASTTHREE_CON"));
            }
            query.append(Constants._BLANK);
            query.append(SQLUtils.getSQLQuery("SELECT_PATIENT_RECORD_BY_INSTITUTION_GROUP_ORDER_BY"));
            
            PreparedStatement pstmt = connection.prepareStatement(query.toString());
            pstmt.setInt(1, Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID)));

            ResultSet rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    PatientRecord bean = new PatientRecord();
                    bean.setPatientId(rs.getString(TableConstants.PATIENT_ID_PD));
                    bean.setChfPatientId(rs.getString(TableConstants.CHF_PATIENT_ID_PD));
                    bean.setInstitutionId(rs.getString(TableConstants.INSTITUTION_ID_PD));
                    bean.setLastRead(rs.getString(TableConstants.LAST_READ));
                    bean.setHeartRateTrend(rs.getString(TableConstants.HEART_RATE_TREND));
                    bean.setHeartRateTrendValue(rs.getString(TableConstants.HEART_RATE));
                    bean.setQtTrend(rs.getString(TableConstants.QT_TREND));
                    bean.setQtWidth(rs.getString(TableConstants.QT_WIDTH));
                    bean.setQrsTrend(rs.getString(TableConstants.QRS_TREND));
                    bean.setQrsWidth(rs.getString(TableConstants.QRS_WIDTH));
                    bean.setRespirationRateTrend(rs.getString(TableConstants.RESPIRATION_RATE_TREND));
                    bean.setRespirationRateTrendValue(rs.getString(TableConstants.RESPIRATION_RATE));
                    bean.setAbnormalRhythm(rs.getString(TableConstants.ABNORMAL_RHYTHM));
                    bean.setS3Trend(rs.getString(TableConstants.S3_TREND));
                    bean.setTidalVolumeTrend(rs.getString(TableConstants.TIDAL_VOL_TREND));
                    bean.setThoracicImpedanceTrend(rs.getString(TableConstants.THORACIC_IMPEDANCE_TREND));
                    bean.setDailyCompliance(rs.getString(TableConstants.DAILY_COMPLIANCE));
                    bean.setReadingCompliance(rs.getString(TableConstants.READING_COMPLIANCE));
                    bean.setPercentage(rs.getString(TableConstants.PERCETAGE));
                    bean.setSystemId(rs.getString(TableConstants.SYSTEM_ID));
                    bean.setThresholdAlarms(rs.getString(TableConstants.THRESHOLD_ALARM));
                    bean.setMeasurementId(rs.getString(TableConstants.MEASUREMENT_ID));

                    dataList.add(bean);
                }
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return dataList;
    }
    /**
     * This method will provide details of patient dashboard vital
     *
     * @param input
     * @param data
     * @return
     * @throws SQLException
     */
    public static void getPatientDashboardVitalByInstitutionId(Map<String, String> input, DashboardVitals data)
            throws Exception {

        try (Connection connection = DBConnection.getConnection();) {

            PreparedStatement pstmtComp = connection.prepareStatement(SQLUtils.getSQLQuery("SELCT_COMPLIANCE_PERIOD_BY_INSTITUTION_ID"));
            pstmtComp.setInt(1, Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID)));

            ResultSet rsComp = pstmtComp.executeQuery();

            if (rsComp != null) {
                while (rsComp.next()) {
                    data.setCompliancePeriod(rsComp.getInt(TableConstants.COMPLIANCE_PERIOD));
                    break;
                }
            }

            StringBuilder query = new StringBuilder();
            query.append(SQLUtils.getSQLQuery("SELECT_PATINET_TOP_COUNT_WATCHLIST"));
            query.append(" union ");
            query.append(SQLUtils.getSQLQuery("SELECT_PATINET_TOP_COUNT_THRESHOLDALARM"));
            query.append(" union ");
            query.append(SQLUtils.getSQLQuery("SELECT_PATINET_TOP_COUNT_LASTTHREEDAYS"));
            query.append(" union ");
            query.append(SQLUtils.getSQLQuery("SELECT_PATINET_TOP_COUNT_PATIENTACTION"));

            PreparedStatement pstmt = connection.prepareStatement(query.toString());
            pstmt.setInt(1, Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID)));
            pstmt.setInt(2, Integer.parseInt(input.get(Constants.QUERY_PARAM_CLINICIAN_ID)));
            pstmt.setInt(3, Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID)));
            pstmt.setInt(4, Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID)));
            pstmt.setInt(5, Integer.parseInt(input.get(Constants.QUERY_PARAM_INSTITUTION_ID)));

            ResultSet rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    Properties bean = new Properties();
                    if (StringUtils.equalsIgnoreCase(rs.getString(1), "watchlist")) {
                        bean.setType("Patient WatchList");
                        bean.setValue(rs.getString(2));
                        bean.setKey("all selected");
                        data.setPatientWatchList(bean);

                    }

                    if (StringUtils.equalsIgnoreCase(rs.getString(1), "thresholdAlarm")) {
                        bean.setType("Patient With Thresholds");
                        bean.setValue(rs.getString(2));
                        bean.setKey("all selected");
                        data.setPatientThresholds(bean);
                    }

                    if (StringUtils.equalsIgnoreCase(rs.getString(1), "lastThreeday")) {
                        bean.setType("New Reading");
                        bean.setValue(rs.getString(2));
                        bean.setKey("last 3 days");
                        data.setNewReading(bean);
                    }

                    if (StringUtils.equalsIgnoreCase(rs.getString(1), "patientActions")) {
                        bean.setType("Patient Actions");
                        bean.setValue(rs.getString(2));
                        bean.setKey("all open");
                        data.setPatientActions(bean);
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }
    }

    /**
     * This method soft delete from patient_provider_mapping table based on patient id and provider id.
     *
     * @param patientProvider
     * @return
     */
    public static CustomResponse removePatientProvider(PatientProvider patientProvider) {
        CustomResponse response = new CustomResponse();     
        int updateCounts;

        if (patientProvider != null) {
            try (Connection connection = DBConnection.getConnection();) {

                String query = SQLUtils.getSQLQuery("REMOVE_PATIENT_PROVIDER_MAPPING");
                PreparedStatement ps = connection.prepareStatement(query);  
                ps.setString(1, patientProvider.getUpdatedBy());
                ps.setInt(2, patientProvider.getProviderId());
                ps.setString(3, patientProvider.getChfPatientId());
                updateCounts = ps.executeUpdate();

                if (updateCounts  > 0) {
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
        }
        return response;
    }
    /**
     * This method will insert record into patient_device_mapping table for
     * specified patient and specified device
     * 
     * @param pdm
     * @return
     */
    public static CustomResponse insertPatientDeviceMapping(final PatientDeviceMapping pdm) {
        CustomResponse response = new CustomResponse();
        int numberOfRecordsInserted = 0;
        int kitID = 0;
        String systemId = null;
        System.out.println(pdm);
        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);
                // fetch the device kit id first to save it in patient-device mapping table
                PreparedStatement pstmt = connection
                        .prepareStatement(SQLUtils.getSQLQuery("SELECT_KIT_ID_FROM_DEVICE_INVENTORY"));

                pstmt.setString(1, pdm.getDeviceLabelId());

                ResultSet rs = pstmt.executeQuery();

                if (rs != null && rs.next()) {
                    kitID = rs.getInt(TableConstants.DEVICE_INVENTORY_KIT_ID);
                }
                //If device detail is present in the system then only make an entry in device-patient mapping table
                if(kitID > 0) {

                    // first check if the device is already assigned to same patient


                    pstmt = connection
                            .prepareStatement(SQLUtils.getSQLQuery("SELECT_SYSTEM_ID_FROM_PATIENT_DEVICE_MAPPING"));
                    pstmt.setInt(1, kitID);
                    pstmt.setString(2, pdm.getChfPatientId());
                    rs = pstmt.executeQuery();
                    if (rs != null && rs.next()) {
                        systemId = rs.getString(TableConstants.PATIENT_DEVICE_MAPPING_SYSTEM_ID);
                        response.setStatusCode(Status.OK.getStatusCode());
                        response.setDescription(Status.OK.toString() + "|" + systemId);
                    } else {

                        // created the systemId(For now the id has been generated using random number in future the approach can be diiferent)
                        int currentTime = (int) (new Date().getTime()/1000);
                        systemId = RandomStringUtils.random(22, true, true) + currentTime;

                        pstmt = connection
                                .prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_DEVICE_MAPPING"));


                        pstmt.setInt(1, kitID);
                        pstmt.setString(2, pdm.getChfPatientId());
                        pstmt.setString(3, systemId);
                        pstmt.setString(4, pdm.getCreatedBy());
                        pstmt.setString(5, pdm.getCreatedBy());

                        numberOfRecordsInserted = pstmt.executeUpdate();
                        if(numberOfRecordsInserted > 0) {
                            pstmt = connection
                                    .prepareStatement(SQLUtils.getSQLQuery("UPDATE_SYSTEM_ID_IN_PATIENT_THRESHOLD"));


                            pstmt.setString(1, systemId);
                            pstmt.setString(2, pdm.getCreatedBy());
                            pstmt.setString(3, pdm.getChfPatientId());

                            numberOfRecordsInserted = pstmt.executeUpdate();
                            LOGGER.info("Number of records inserted into patient_device_mapping table: " + numberOfRecordsInserted);
                            if(numberOfRecordsInserted > 0) {
                                response.setStatusCode(Status.OK.getStatusCode());
                                response.setDescription(Status.OK.toString() + "|" + systemId);
                            } else {
                                response.setStatusCode(Status.NO_CONTENT.getStatusCode());
                                response.setDescription(Status.NO_CONTENT.toString());
                            }

                        }
                    }
                } else {
                    response.setStatusCode(Status.NO_CONTENT.getStatusCode());
                    response.setDescription(Status.NO_CONTENT.toString());
                }
                connection.setAutoCommit(true);

            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

                if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
                    response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
                    response.setDescription(
                            Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
                } else {
                    response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                    response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
                }

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
     * This method will fetch patientPhi details from patient_phi table based on
     * chfPatientId
     *
     * @param chfPatientId
     * @return
     */
    public static PatientPhi fetchPatientPhiDetailsBasedOnChfPatientId(final String chfPatientId) {
        PatientPhi patientPhi = new PatientPhi();
        String patientDetailsJson;

        try (Connection connection = DBConnection.getConnection();) {
            PreparedStatement pstmt = connection
                    .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_PHI_BASED_ON_CHF_PATIENT_ID"));
            pstmt.setString(1, chfPatientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs != null && rs.next()) {
                patientPhi.setPatientId(rs.getInt(TableConstants.PATIENT_ID));
                patientPhi.setChfPatientId(rs.getString(TableConstants.CHF_PATIENT_ID));
                patientPhi.setMrNumber(rs.getString(TableConstants.PATIENT_MR_NO));
                patientPhi.setFirstName(rs.getString(TableConstants.PATIENT_FIRST_NAME));
                patientPhi.setLastName(rs.getString(TableConstants.PATIENT_LAST_NAME));
                patientPhi.setDoB(rs.getString(TableConstants.PATIENT_DOB));
                patientPhi.setGender(rs.getString(TableConstants.PATIENT_GENDER));
                patientPhi.setInstitutionId(rs.getInt(TableConstants.PATIENT_INSTITUTION_ID));
                patientPhi.setIsActionOpen(rs.getBoolean(TableConstants.IS_ACTION_OPEN));
                patientPhi.setDeleteMarker(rs.getString(TableConstants.PATIENT_DELETE_MARKER));
                patientPhi.setCreatedBy(rs.getString(TableConstants.PATIENT_CREATED_BY));
                patientPhi.setCreatedTimestamp(rs.getTimestamp(TableConstants.PATIENT_CREATED_TIMESTAMP));
                patientPhi.setUpdatedBy(rs.getString(TableConstants.PATIENT_UPDATED_BY));
                patientPhi.setUpdatedTimestamp(rs.getTimestamp(TableConstants.PATIENT_UPDATED_TIMESTAMP));
                patientPhi.setDeviceLabelId(rs.getString(TableConstants.DEVICE_LABEL_ID));
                patientPhi.setSystemId(rs.getString(TableConstants.PATIENT_DEVICE_MAPPING_SYSTEM_ID));
                
                // now convert the patientDetailsJson string to json and store
                // in json array
                patientDetailsJson = rs.getString(TableConstants.PATIENT_DETAILS_JSON);
                ObjectMapper objectMapper = new ObjectMapper();
                List<PatientAttributes> patientAttributesList = objectMapper.readValue(patientDetailsJson,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, PatientAttributes.class));
                patientPhi.setPatientAttributesList(patientAttributesList);
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return patientPhi;
    }

    /**
     * This method will update 'status' as CLOSED in patient_actions table for
     * the specified action id. Also it will update is_action_open flag to 0 in
     * patient_phi table for the specified patient id
     *
     * @param patientActions
     * @return
     */
    public static CustomResponse updatePatientAction(final PatientActions patientActions) {
        CustomResponse response = new CustomResponse();
        int numberOfRecordsUpdated = 0;
        PreparedStatement pstmt = null;

        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);
                pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_PATIENT_ACTION_AS_CLOSED"));
                pstmt.setString(1, patientActions.getUpdatedBy());
                pstmt.setInt(2, patientActions.getActionId());

                numberOfRecordsUpdated = pstmt.executeUpdate();

                LOGGER.info(
                        "Number of actions marked as 'CLOSED' into patient_actions table: " + numberOfRecordsUpdated);

                pstmt = connection
                        .prepareStatement(SQLUtils.getSQLQuery("UPDATE_IS_ACTION_OPEN_AS_FALSE_IN_PATIENT_PHI"));
                pstmt.setString(1, patientActions.getUpdatedBy());
                pstmt.setInt(2, patientActions.getPatientId());
                pstmt.setInt(3, patientActions.getPatientId());

                numberOfRecordsUpdated = pstmt.executeUpdate();

                LOGGER.info("Number of records updated into patient_phi table for 'is_action_open' as false: "
                        + numberOfRecordsUpdated);

                response.setStatusCode(Status.OK.getStatusCode());
                response.setDescription(Status.OK.toString());

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
     * This method will insert a new patient action into patient_actions table.
     * Also it will update is_action_open flag to 1 in patient_phi table
     * 
     * @param patientActions
     * @return
     */
    public static PatientActions addPatientActions(final PatientActions patientActions) {
        PatientActions patientAction = new PatientActions();
        int numberOfRecordsInserted = 0;
        int numberOfRecordsUpdated = 0;
        PreparedStatement pstmt = null;

        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);

                pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_ACTIONS"),
                        Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, patientActions.getPatientId());
                pstmt.setString(2, patientActions.getPriority());
                pstmt.setDate(3, patientActions.getDueDate());
                pstmt.setString(4, patientActions.getSubject());
                pstmt.setString(5, patientActions.getDetails());
                pstmt.setString(6, patientActions.getCreatedBy());
                pstmt.setString(7, patientActions.getCreatedBy());

                numberOfRecordsInserted = pstmt.executeUpdate();
                ResultSet generatedKeys = pstmt.getGeneratedKeys();

                LOGGER.info("Number of records inserted into patient_actions table: " + numberOfRecordsInserted);

                if (numberOfRecordsInserted > 0 && generatedKeys.next()) {
                    Integer actionId = generatedKeys.getInt(1);

                    pstmt = connection
                            .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_ACTIONS_BY_ACTION_ID"));
                    pstmt.setInt(1, actionId);

                    ResultSet rs = pstmt.executeQuery();

                    if (rs != null) {
                        while (rs.next()) {
                            patientAction.setActionId(actionId);
                            patientAction.setPatientId(rs.getInt(TableConstants.PATIENT_ACTIONS_PATIENT_ID));
                            patientAction.setPriority(rs.getString(TableConstants.PATIENT_ACTIONS_PRIORITY));
                            patientAction.setDueDate(rs.getDate(TableConstants.PATIENT_ACTIONS_DUE_DATE));
                            patientAction.setSubject(rs.getString(TableConstants.PATIENT_ACTIONS_SUBJECT));
                            patientAction.setDetails(rs.getString(TableConstants.PATIENT_ACTIONS_DETAILS));
                            patientAction.setStatus(rs.getString(TableConstants.PATIENT_ACTIONS_STATUS));
                            patientAction.setCreatedBy(rs.getString(TableConstants.PATIENT_ACTIONS_CREATED_BY));
                            patientAction.setCreatedTimestamp(
                                    rs.getTimestamp(TableConstants.PATIENT_ACTIONS_CREATED_TIMESTAMP));
                            patientAction.setUpdatedBy(rs.getString(TableConstants.PATIENT_ACTIONS_UPDATED_BY));
                            patientAction.setUpdatedTimestamp(
                                    rs.getTimestamp(TableConstants.PATIENT_ACTIONS_UPDATED_TIMESTEMP));
                        }
                    }

                    pstmt = connection
                            .prepareStatement(SQLUtils.getSQLQuery("UPDATE_IS_ACTION_OPEN_AS_TRUE_IN_PATIENT_PHI"));
                    pstmt.setString(1, patientActions.getCreatedBy());
                    pstmt.setInt(2, patientActions.getPatientId());

                    numberOfRecordsUpdated = pstmt.executeUpdate();

                    LOGGER.info("Number of records updated into patient_phi table for 'is_action_open' as true: "
                            + numberOfRecordsUpdated);
                }

                patientAction.setStatusCode(Status.OK.getStatusCode());
                patientAction.setDescription(Status.OK.toString());

                connection.setAutoCommit(true);

            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
                patientAction.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                patientAction.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

                if (connection != null && !connection.isClosed()) {
                    connection.rollback();
                }
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
     * This method will insert new comment into patient_action_comments table
     * corresponding to action Id
     * 
     * @param patientActionComments
     * @return
     */
    public static PatientActionComments addActionComment(final PatientActionComments patientActionComments) {
        PatientActionComments patientActionComment = new PatientActionComments();
        int numberOfRecordsInserted = 0;
        PreparedStatement pstmt = null;

        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);

                pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_ACTION_COMMENTS"),
                        Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, patientActionComments.getActionId());
                pstmt.setString(2, patientActionComments.getComments());
                pstmt.setString(3, patientActionComments.getCreatedBy());

                numberOfRecordsInserted = pstmt.executeUpdate();
                ResultSet generatedKeys = pstmt.getGeneratedKeys();

                LOGGER.info("Number of records inserted into patient_action_comment table: " + numberOfRecordsInserted);

                if (numberOfRecordsInserted > 0 && generatedKeys.next()) {
                    Integer commentId = generatedKeys.getInt(1);

                    pstmt = connection.prepareStatement(
                            SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_ACTION_COMMENTS_BY_COMMENT_ID"));
                    pstmt.setInt(1, commentId);

                    ResultSet rs = pstmt.executeQuery();

                    if (rs != null) {
                        while (rs.next()) {
                            patientActionComment.setCommentId(commentId);
                            patientActionComment
                            .setActionId(rs.getInt(TableConstants.PATIENT_ACTION_COMMENTS_ACTION_ID));
                            patientActionComment
                            .setComments(rs.getString(TableConstants.PATIENT_ACTION_COMMENTS_COMMENT));
                            patientActionComment
                            .setCreatedBy(rs.getString(TableConstants.PATIENT_ACTION_COMMENTS_CREATED_BY));
                            patientActionComment.setCreatedTimestamp(
                                    rs.getTimestamp(TableConstants.PATIENT_ACTION_COMMENTS_CREATED_TIMESTAMP));
                        }
                    }
                }

                patientActionComment.setStatusCode(Status.OK.getStatusCode());
                patientActionComment.setDescription(Status.OK.toString());

                connection.setAutoCommit(true);

            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
                patientActionComment.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                patientActionComment.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

                if (connection != null && !connection.isClosed()) {
                    connection.rollback();
                }
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
     * This method will fetch patient actions and their comments from
     * patient_actions and patient_action_comments table based on patient id
     *
     * 
     * @param patientId
     * @return
     */
    public static PatientActions getPatientActions(final Integer patientId) {
        PatientActions patientActions = new PatientActions();
        List<PatientActions> patientActionsList = new ArrayList<PatientActions>();
        Map<Integer, PatientActions> patientActionsMap = new LinkedHashMap<Integer, PatientActions>();
        int recordCount=0;

        try (Connection connection = DBConnection.getConnection();) {
            PreparedStatement pstmt = connection
                    .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_ACTIONS_AND_COMMENTS"));
            pstmt.setInt(1, patientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    PatientActions patientAction = null;
                    Integer actionIdKey = rs.getInt(TableConstants.PATIENT_ACTIONS_ACTION_ID);

                    if (patientActionsMap.containsKey(actionIdKey)) {
                        patientAction = patientActionsMap.get(actionIdKey);

                    } else {
                        patientAction = new PatientActions();
                        patientAction.setActionId(actionIdKey);
                        patientAction.setPatientId(rs.getInt(TableConstants.PATIENT_ACTIONS_PATIENT_ID));
                        patientAction.setPriority(rs.getString(TableConstants.PATIENT_ACTIONS_PRIORITY));
                        patientAction.setDueDate(rs.getDate(TableConstants.PATIENT_ACTIONS_DUE_DATE));
                        patientAction.setSubject(rs.getString(TableConstants.PATIENT_ACTIONS_SUBJECT));
                        patientAction.setDetails(rs.getString(TableConstants.PATIENT_ACTIONS_DETAILS));
                        patientAction.setStatus(rs.getString(TableConstants.PATIENT_ACTIONS_STATUS));
                        patientAction.setCreatedBy(rs.getString(TableConstants.PATIENT_ACTIONS_CREATED_BY));
                        patientAction
                        .setCreatedTimestamp(rs.getTimestamp(TableConstants.PATIENT_ACTIONS_CREATED_TIMESTAMP));
                        patientAction.setUpdatedBy(rs.getString(TableConstants.PATIENT_ACTIONS_UPDATED_BY));
                        patientAction
                        .setUpdatedTimestamp(rs.getTimestamp(TableConstants.PATIENT_ACTIONS_UPDATED_TIMESTEMP));

                        if("OPEN".equalsIgnoreCase(patientAction.getStatus())) {
                            recordCount++;
                        }
                        patientActionsMap.put(actionIdKey, patientAction);
                    }

                    List<PatientActionComments> patientActionCommentsList = null;

                    if (rs.getString(TableConstants.PATIENT_ACTION_COMMENTS_COMMENT) != null) {
                        PatientActionComments patientActionComment = new PatientActionComments();
                        patientActionComment.setCommentId(rs.getInt(TableConstants.PATIENT_ACTION_COMMENTS_COMMENT_ID));
                        patientActionComment.setActionId(actionIdKey);
                        patientActionComment.setComments(rs.getString(TableConstants.PATIENT_ACTION_COMMENTS_COMMENT));
                        patientActionComment
                        .setCreatedBy(rs.getString(TableConstants.PATIENT_ACTION_COMMENTS_CREATED_BY));
                        patientActionComment.setCreatedTimestamp(
                                rs.getTimestamp(TableConstants.PATIENT_ACTION_COMMENTS_CREATED_TIMESTAMP));

                        if (patientAction.getPatientActionCommentsList() == null) {
                            patientActionCommentsList = new ArrayList<PatientActionComments>();
                        } else {
                            patientActionCommentsList = patientAction.getPatientActionCommentsList();
                        }

                        patientActionCommentsList.add(patientActionComment);
                    }

                    patientAction.setPatientActionCommentsList(patientActionCommentsList);
                }

                patientActionsList = new ArrayList<PatientActions>(patientActionsMap.values());
                patientActions.setPatientActions(patientActionsList);
                patientActions.setRecordCount(recordCount);
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return patientActions;
    }





    /**
     * Insert comment of patient into clinician_comments table and return
     * inserted object
     *
     * @param bean
     * @return
     */
    public static PatientComments savePatientComment(final PatientComments bean) {
        PatientComments patientComment = new PatientComments();

        try (Connection connection = DBConnection.getConnection();) {
            try {

                PreparedStatement pstmt = connection.prepareStatement(
                        SQLUtils.getSQLQuery("INSERT_INTO_CLINICIAN_COMMENTS"), Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, bean.getPatientId());
                pstmt.setString(2, bean.getComment());
                pstmt.setString(3, bean.getCreatedBy());

                pstmt.executeUpdate();

                ResultSet generatedKeys = pstmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    pstmt = connection
                            .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_CLINICIAN_COMMENTS_BY_COMMENT_ID"));
                    pstmt.setInt(1, generatedKeys.getInt(1));
                    ResultSet rs = pstmt.executeQuery();

                    if (rs != null) {
                        while (rs.next()) {
                            patientComment.setCommentId(rs.getInt(TableConstants.COMMENT_ID));
                            patientComment.setPatientId(rs.getInt(TableConstants.COMMENT_PATIENT_ID));
                            patientComment.setComment(rs.getString(TableConstants.COMMENT));
                            patientComment.setCreatedBy(rs.getString(TableConstants.COMMENT_CREATED_BY));
                            patientComment
                            .setCreatedTimestamp(rs.getTimestamp(TableConstants.COMMENT_CREATED_TIMESTAMP));
                            patientComment.setUpdatedBy(rs.getString(TableConstants.COMMENT_UPDATED_BY));
                            patientComment
                            .setUpdatedTimestamp(rs.getTimestamp(TableConstants.COMMENT_UPDATED_TIMESTAMP));
                        }
                    }

                    patientComment.setStatusCode(Status.OK.getStatusCode());
                    patientComment.setDescription(Status.OK.toString());
                }

            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

                if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
                    patientComment.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
                    patientComment.setDescription(
                            Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
                } else {
                    patientComment.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                    patientComment.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
            patientComment.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
            patientComment.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
        }

        return patientComment;
    }

    /**
     * This method will return the list of comments for the specified patient
     * 
     * @param patientId
     * @return
     */
    public static List<PatientComments> getPatientComments(final Integer patientId) {
        List<PatientComments> patientCommentsList = new ArrayList<PatientComments>();

        try (Connection connection = DBConnection.getConnection();) {
            PreparedStatement pstmt = connection
                    .prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_CLINICIAN_COMMENTS_BY_PATIENT_ID"));
            pstmt.setInt(1, patientId);

            ResultSet rs = pstmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    PatientComments patientComment = new PatientComments();
                    patientComment.setCommentId(rs.getInt(TableConstants.COMMENT_ID));
                    patientComment.setPatientId(rs.getInt(TableConstants.COMMENT_PATIENT_ID));
                    patientComment.setComment(rs.getString(TableConstants.COMMENT));
                    patientComment.setCreatedBy(rs.getString(TableConstants.COMMENT_CREATED_BY));
                    patientComment.setCreatedTimestamp(rs.getTimestamp(TableConstants.COMMENT_CREATED_TIMESTAMP));
                    patientComment.setUpdatedBy(rs.getString(TableConstants.COMMENT_UPDATED_BY));
                    patientComment.setUpdatedTimestamp(rs.getTimestamp(TableConstants.COMMENT_UPDATED_TIMESTAMP));

                    patientCommentsList.add(patientComment);
                }
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return patientCommentsList;
    }



    /**
     * This method has common code for return master other patient mapping list from
     * patient from patient_master_mapping, master_details,
     * patient_master_other_mapping tables based on patientId and master_type_code
     *
     *
     * @param institutionId
     * @param patientId
     * @masterTypeCodeList
     * @return
     */
    public static PatientMasterMapping getPatientMasterOtherMappingList(final Integer institutionId,
            final Integer patientId, final Map<String, Integer> masterTypeCodeList) throws Exception {
        int recordCount = 0;
        String dataType = null;
        String masterTypeCode = null;
        String mappingId = null;
        StringBuilder masterTypeCodeString = new StringBuilder();
        List<PatientMasterOtherMappings> patientMappingList = null;

        PatientMasterMapping patientMasterMapping = new PatientMasterMapping();
        Map<String, List<PatientMasterOtherMappings>> patientMappingMap = new HashMap<String, List<PatientMasterOtherMappings>>();

        if (masterTypeCodeList != null && masterTypeCodeList.size() > 0) {

            for (String key : masterTypeCodeList.keySet()) {
                masterTypeCodeString.append("'").append(key).append("'").append(",");
            }
            masterTypeCodeString.delete(masterTypeCodeString.length() - 1, masterTypeCodeString.length());

            try (Connection connection = DBConnection.getConnection();) {

                String query = SQLUtils.getSQLQuery("SELECT_PATIENT_MASTER_OTHER_MAPPING_DETAILS");
                query = query.replaceAll("REPLACE_AREA", masterTypeCodeString.toString());
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, patientId);
                pstmt.setInt(2, patientId);

                ResultSet rs = pstmt.executeQuery();
                if (rs != null) {
                    while (rs.next()) {

                        PatientMasterOtherMappings patientMasterMappings = new PatientMasterOtherMappings();
                        dataType = rs.getString(TableConstants.PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_DATA_TYPE);
                        masterTypeCode = rs
                                .getString(TableConstants.PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_MASTER_TYPE_CODE);
                        mappingId = rs.getString(TableConstants.PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_MAPPING_ID);
                        patientMasterMappings
                        .setId(rs.getInt(TableConstants.PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_ID));
                        patientMasterMappings
                        .setText(rs.getString(TableConstants.PATIENT_MASTER_OTHER_MAPPING_COLUMN_LABEL_TEXT));

                        if (patientMappingMap.containsKey(masterTypeCode)) {
                            patientMappingList = patientMappingMap.get(masterTypeCode);
                        } else {
                            patientMappingList = new ArrayList<PatientMasterOtherMappings>();
                        }

                        if ("M".equalsIgnoreCase(dataType)) {
                            patientMasterMappings.setLabel(patientMasterMappings.getText());
                            if (mappingId == null || mappingId.isEmpty()) {
                                patientMasterMappings.setIsSelected(false);
                            } else {
                                patientMasterMappings.setIsSelected(true);
                                recordCount = masterTypeCodeList.get(masterTypeCode);
                                recordCount++;
                                masterTypeCodeList.put(masterTypeCode, recordCount);
                            }
                        } else {
                            patientMasterMappings.setLabel("Other");
                            patientMasterMappings.setIsSelected(true);
                            recordCount = masterTypeCodeList.get(masterTypeCode);
                            recordCount++;
                            masterTypeCodeList.put(masterTypeCode, recordCount);
                        }
                        patientMappingList.add(patientMasterMappings);
                        patientMappingMap.put(masterTypeCode, patientMappingList);
                    }
                }

                recordCount = 0;
                for (Entry<String, Integer> entry : masterTypeCodeList.entrySet()) {
                    recordCount = recordCount + entry.getValue();
                    masterTypeCode = entry.getKey();

                    if (MasterDetailsCode.MED.toString().equalsIgnoreCase(masterTypeCode)) {
                        patientMasterMapping.setMedication(patientMappingMap.get(masterTypeCode));

                    } else if (MasterDetailsCode.ADM.toString().equalsIgnoreCase(masterTypeCode)) {
                        patientMasterMapping.setAdmissions(patientMappingMap.get(masterTypeCode));

                    } else if (MasterDetailsCode.PRO.toString().equalsIgnoreCase(masterTypeCode)) {
                        patientMasterMapping.setProcedures(patientMappingMap.get(masterTypeCode));

                    } else if (MasterDetailsCode.IMP.toString().equalsIgnoreCase(masterTypeCode)) {
                        patientMasterMapping.setImplants(patientMappingMap.get(masterTypeCode));

                    } else if (MasterDetailsCode.COM.toString().equalsIgnoreCase(masterTypeCode)) {
                        patientMasterMapping.setComorbidities(patientMappingMap.get(masterTypeCode));

                    }
                }

                patientMasterMapping.setRecordCount(recordCount);
                patientMasterMapping.setInstitutionId(institutionId);
                patientMasterMapping.setPatientId(patientId);

            } catch (Exception e) {
                LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                        + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
            }
        }
        return patientMasterMapping;
    }

    /**
     * This method has common code for Insert, Delete from patient_master_mapping,
     * patient_master_other_mapping tables based on patientId and master_type_code and id's;
     *
     * @param patientMasterMapping
     * @return
     */
    public static CustomResponse updatePatientMasterOtherMapping(PatientMasterMapping patientMasterMapping)
            throws Exception {
        CustomResponse response = new CustomResponse();

        int[] updateCounts = null;

        if (patientMasterMapping != null) {
            try (Connection connection = DBConnection.getConnection();) {
                try {
                    connection.setAutoCommit(false);

                    String query = SQLUtils.getSQLQuery("UPDATE_PATIENT_MASTER_OTHER_MAPPING");
                    CallableStatement callableStatement = connection.prepareCall(query);
                    for(PatientMasterOtherMappings mappings:patientMasterMapping.getProcedures()) {                  
                        callableStatement = updatePatientMasterOtherMapping(callableStatement, patientMasterMapping.getPatientId(),
                                MasterDetailsCode.PRO.toString(), patientMasterMapping.getCreatedBy(), patientMasterMapping.getUpdatedBy(),mappings);
                    }

                    for(PatientMasterOtherMappings mappings:patientMasterMapping.getMedication()) {                  
                        callableStatement = updatePatientMasterOtherMapping(callableStatement, patientMasterMapping.getPatientId(),
                                MasterDetailsCode.MED.toString(), patientMasterMapping.getCreatedBy(), patientMasterMapping.getUpdatedBy(),mappings);
                    }

                    for(PatientMasterOtherMappings mappings:patientMasterMapping.getComorbidities()) {                   
                        callableStatement = updatePatientMasterOtherMapping(callableStatement, patientMasterMapping.getPatientId(),
                                MasterDetailsCode.COM.toString(), patientMasterMapping.getCreatedBy(), patientMasterMapping.getUpdatedBy(),mappings);
                    }

                    for(PatientMasterOtherMappings mappings:patientMasterMapping.getImplants()) {                    
                        callableStatement = updatePatientMasterOtherMapping(callableStatement, patientMasterMapping.getPatientId(),
                                MasterDetailsCode.IMP.toString(), patientMasterMapping.getCreatedBy(), patientMasterMapping.getUpdatedBy(), mappings);
                    }

                    for(PatientMasterOtherMappings mappings:patientMasterMapping.getAdmissions()) {                  
                        callableStatement = updatePatientMasterOtherMapping(callableStatement, patientMasterMapping.getPatientId(),
                                MasterDetailsCode.ADM.toString(), patientMasterMapping.getCreatedBy(), patientMasterMapping.getUpdatedBy(),mappings);
                    }
                    updateCounts = callableStatement.executeBatch();
                    connection.setAutoCommit(true);
                    callableStatement.close();

                    if (updateCounts != null && updateCounts.length > 0) {
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

        }
        return response;
    }

    /**
     * This method has common code for set callable statement value for master and other mappings.
     *
     * @param patientMasterMapping
     * @return
     */
    private static CallableStatement updatePatientMasterOtherMapping(final CallableStatement callableStatement,  final Integer patientId,
            String masterCode, String createdBy, String updatedBy, PatientMasterOtherMappings masterOtherMapping) throws Exception {
        callableStatement.setInt(1, patientId);
        callableStatement.setInt(2, (masterOtherMapping.getId() == null ? 0 : masterOtherMapping.getId()));      
        callableStatement.setString(3, masterCode);
        if(!"Other".equalsIgnoreCase(masterOtherMapping.getLabel())) {
            callableStatement.setString(4, "M");             
        }else {
            callableStatement.setString(4, masterOtherMapping.getText());            
        }
        callableStatement.setString(5, createdBy);
        callableStatement.setString(6, updatedBy);
        if(masterOtherMapping.getIsSelected()) {
            callableStatement.setInt(7, 1);
        }else {
            callableStatement.setInt(7, 0);
        }
        callableStatement.addBatch();
        return callableStatement;
    }

    /**
     * This method will fetch the list of patientId, chfPatientId and mrNumber from
     * patient_phi table based on institution_id and specific column(pattern based search) for mobile app to show patient list
     * 
     * @param chfPatientSearchRequest
     * @return
     */

    public static ChfPatientListResponseForMobileApp getChfPatientIdListBySearchParamWithPagination(
            final ChfPatientSearchRequest chfPatientSearchRequest) {
        ChfPatientListResponseForMobileApp chfPatientListResponseForMobileApp = new ChfPatientListResponseForMobileApp();
        List<ChfPatientId> chfPatientIdList = new ArrayList<ChfPatientId>();
        int patientCount = 0;
        String columnName = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try (Connection connection = DBConnection.getConnection();) {
            String query = SQLUtils.getSQLQuery("SELECT_FROM_PATIENT_PHI_BASED_ON_INSTITUTION_ID_SEARCH");
            if (PatientJsonMapping.first_name.equals(PatientJsonMapping.getDbColumnName(chfPatientSearchRequest.getPolicyField()))
                    || PatientJsonMapping.last_name
                            .equals(PatientJsonMapping.getDbColumnName(chfPatientSearchRequest.getPolicyField()))
                    || PatientJsonMapping.dob.equals(PatientJsonMapping.getDbColumnName(chfPatientSearchRequest.getPolicyField()))
                    || PatientJsonMapping.zip
                            .equals(PatientJsonMapping.getDbColumnName(chfPatientSearchRequest.getPolicyField()))) {
                columnName = PatientJsonMapping.getDbColumnName(chfPatientSearchRequest.getPolicyField()).name();
            } else if (PatientJsonMapping.getDbColumnName(chfPatientSearchRequest.getPolicyField()) != null) {
                String jsonColumnName = PatientJsonMapping.getDbColumnName(chfPatientSearchRequest.getPolicyField()).name();
                columnName = "JSON_UNQUOTE(JSON_EXTRACT(" + TableConstants.PATIENT_DETAILS_JSON + ",'\\$.\""
                        + jsonColumnName + "\"'))";
            } else {
                columnName = "JSON_UNQUOTE(JSON_EXTRACT(" + TableConstants.PATIENT_DETAILS_JSON + ",'\\$.\"empty\"'))";
            }

            if (columnName != null && !columnName.isEmpty()) {
                columnName = columnName.concat(" LIKE '" + chfPatientSearchRequest.getPolicyValue() + "%'");
                query = query.replace("REPLACEAREA", columnName);

                pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, chfPatientSearchRequest.getInstitutionId());
                pstmt.setInt(2, chfPatientSearchRequest.getPageStartIndex());
                pstmt.setInt(3, chfPatientSearchRequest.getPageCount());

                rs = pstmt.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        ChfPatientId chfPatientId = new ChfPatientId();
                        chfPatientId.setPatientId(rs.getInt(TableConstants.PATIENT_ID));
                        chfPatientId.setChfPatientId(rs.getString(TableConstants.CHF_PATIENT_ID));
                        chfPatientId.setMrNumber(rs.getString(TableConstants.PATIENT_MR_NO));
                        chfPatientId.setGender(rs.getString(TableConstants.PATIENT_GENDER));
                        chfPatientId.setFirstName(rs.getString(TableConstants.PATIENT_FIRST_NAME));
                        chfPatientId.setLastName(rs.getString(TableConstants.PATIENT_LAST_NAME));
                        chfPatientId.setContactNo(rs.getString(TableConstants.PATIENT_CONTACT_NO));
                        chfPatientId.setDob(rs.getString(TableConstants.PATIENT_DOB));

                        chfPatientIdList.add(chfPatientId);
                    }
                }

                // Now count total number of patients of the institution
                if (chfPatientIdList != null && chfPatientIdList.size() > 0) {
                    query = SQLUtils.getSQLQuery("COUNT_NO_OF_SEARCHD_PATIENTS_BASED_ON_INSTITUTION_ID");
                    query = query.replace("REPLACEAREA", columnName);
                    pstmt = connection.prepareStatement(query);
                    pstmt.setInt(1, chfPatientSearchRequest.getInstitutionId());

                    rs = pstmt.executeQuery();

                    if (rs != null && rs.next()) {
                        patientCount = rs.getInt(TableConstants.PATIENT_COUNT);
                    }
                }
                
                chfPatientListResponseForMobileApp.setChfPatientIdList(chfPatientIdList);
                chfPatientListResponseForMobileApp.setPatientCount(patientCount);
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return chfPatientListResponseForMobileApp;
    }
    
    /**
     * This method will update(add/delete) the mapping of specified clinician
     * with specified patient, i.e. it will add the specified patient into the
     * specified clinician's watchlist or it will delete the specified patient
     * from the specified clinician's watchlist
     *
     * @param pcm
     * @return
     */
    public static CustomResponse updatePatientClinicianMapping(final PatientClinicianMapping pcm) {
        CustomResponse response = new CustomResponse();
        PreparedStatement pstmt = null;
        int numberOfRecordsUpdated = 0;

        try (Connection connection = DBConnection.getConnection();) {
            try {
                connection.setAutoCommit(false);

                if (WatchlistAction.ADD.name().equalsIgnoreCase(pcm.getAction())) {
                    pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("ADD_PATIENT_CLINICIAN_MAPPING"));
                    pstmt.setInt(1, pcm.getPatientId());
                    pstmt.setInt(2, pcm.getClinicianId());
                    pstmt.setString(3, pcm.getCreatedBy());
                    pstmt.setString(4, pcm.getCreatedBy());

                } else if (WatchlistAction.DELETE.name().equalsIgnoreCase(pcm.getAction())) {
                    pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("DELETE_PATIENT_CLINICIAN_MAPPING"));
                    pstmt.setInt(1, pcm.getPatientId());
                    pstmt.setInt(2, pcm.getClinicianId());
                }

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
     * This method will provide CPM Trend of a particular patient
     * 
     * @param patientId
     * @return
     * @throws SQLException
     */
    public static PatientRecord getCPMTrends(final String patientId) throws Exception {
        PatientRecord data = new PatientRecord();

        try (Connection connection = DBConnection.getConnection();) {
            LOGGER.info("IN Dao getCPMTrends - 1:::::::");
            PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_CPM_TRENDS"));
            pstmt.setInt(1, Integer.parseInt(patientId));
            ResultSet rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    data.setHeartRateTrend(rs.getString(TableConstants.HEART_RATE_TREND));
                    data.setHeartRateTrendValue(rs.getString(TableConstants.HEART_RATE));
                    data.setInstitutionId(rs.getString(TableConstants.INSTITUTION_ID_PD));
                    data.setLastRead(rs.getString(TableConstants.LAST_READ));
                    data.setPatientId(rs.getString(TableConstants.PATIENT_ID_PD));
                    data.setReadingCompliance(rs.getString(TableConstants.READING_COMPLIANCE));
                    data.setRespirationRateTrend(rs.getString(TableConstants.RESPIRATION_RATE_TREND));
                    data.setRespirationRateTrendValue(rs.getString(TableConstants.RESPIRATION_RATE));
                    data.setS3Trend(rs.getString(TableConstants.S3_TREND));
                    data.setThoracicImpedanceTrend(rs.getString(TableConstants.THORACIC_IMPEDANCE_TREND));
                    data.setThresholdAlarms(rs.getString(TableConstants.THRESHOLD_ALARM));
                    data.setTidalVolumeTrend(rs.getString(TableConstants.TIDAL_VOL_TREND));
                }
            }

        } catch (Exception e) {
            LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
                    + " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
        }

        return data;
    }
    
    
    /**
     * This method will de-enroll the specific patient based on chfPAtientd id by marking delete_marker to 'Y'
     *
     * @param chfPatientId
     * @param updatedBy
     * @return
     */
    public static CustomResponse deEnrollPatientByChfPatientId(final String chfPatientId, final String updatedBy) {
        CustomResponse response = new CustomResponse();
        int numberOfRecordsUpdated = 0;
        PreparedStatement pstmt = null;

        try (Connection connection = DBConnection.getConnection();) {
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("DEENROLL_PATIENT_BY_CHF_PATIENT_ID"));

                pstmt.setString(1, updatedBy);
                pstmt.setString(2, chfPatientId);
                

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

        return response;
    }
    
	/**
	 * Insert record into patient_phi table
	 *
	 * @param patientPhi
	 * @return
	 */
	public static CustomResponse insertBulkRecordIntoPatientPhi(final List<PatientPhi> patientList) {
		CustomResponse response = new CustomResponse();
		int[] numberOfRecordsInsertedL;
		String defaultPatientThreshold = null;
		Random rand = new Random();
		try (Connection connection = DBConnection.getConnection();) {
			try {
				if (CollectionUtils.isNotEmpty(patientList)) {
					PreparedStatement pstmt = connection
							.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_THRESHOLD_POLICY_BY_INSTITUTION_ID"));

					pstmt.setInt(1, patientList.get(0).getInstitutionId());
					ResultSet rs = pstmt.executeQuery();
					if (rs != null && rs.next()) {
						defaultPatientThreshold = rs.getString(TableConstants.THRESHOLD_POLICY);
					}
					if (defaultPatientThreshold != null) {
						connection.setAutoCommit(false);
						pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_PHI_BULK"),
								Statement.RETURN_GENERATED_KEYS);
						for (PatientPhi patientPhi : patientList) {
							pstmt.setString(1, patientPhi.getChfPatientId());
							pstmt.setInt(2, patientPhi.getInstitutionId());
							pstmt.setString(3, patientPhi.getCreatedBy());
							pstmt.setString(4, patientPhi.getCreatedBy());
							pstmt.setString(5, patientPhi.getFirstName());

							pstmt.setString(6, patientPhi.getLastName());
							pstmt.setString(7, patientPhi.getGender());
							pstmt.setString(8, patientPhi.getContactNo());

							pstmt.setString(9, patientPhi.getDoB());
							pstmt.setString(10, patientPhi.getSsn());
							pstmt.setString(11, patientPhi.getPatientDetailsJson());

							pstmt.addBatch();
						}

						numberOfRecordsInsertedL = pstmt.executeBatch();
						ResultSet generatedKeys = pstmt.getGeneratedKeys();

						if (numberOfRecordsInsertedL != null && numberOfRecordsInsertedL.length > 0
								&& generatedKeys.next() && numberOfRecordsInsertedL.length == patientList.size()) {
							for (PatientPhi patientPhi : patientList) {

								Integer patientId = generatedKeys.getInt(1);
								pstmt = connection
										.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PATIENT_THRESHOLD"));
								pstmt.setInt(1, patientId);
								pstmt.setString(2, defaultPatientThreshold);
								pstmt.setString(3, patientPhi.getCreatedBy());
								pstmt.setString(4, patientPhi.getCreatedBy());
								pstmt.addBatch();
							}
//							numberOfRecordsInsertedL = pstmt.executeBatch();
							if (numberOfRecordsInsertedL != null && numberOfRecordsInsertedL.length > 0
									&& generatedKeys.next()) {
								response.setStatusCode(Status.OK.getStatusCode());
								response.setDescription(Status.OK.toString());
								LOGGER.info("Number of records inserted into patient_phi table: "
										+ numberOfRecordsInsertedL.length);
							}
						}
					}
				} else {
					response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
					response.setDescription(Status.INTERNAL_SERVER_ERROR.toString());
				}
				connection.setAutoCommit(true);

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());

				if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
					response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
					response.setDescription(
							Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
				} else {
					response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
					response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
				}

				if (connection != null && !connection.isClosed()) {
					connection.rollback();
				}
			}

		} catch (

		Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * Get ChfId list for all patients based on institution id
	 * 
	 * @param institutionId
	 * @return chfIdList
	 */
	public static List<String> getChfPatientIDList(final int institutionId) {

		List<String> chfIdList = new ArrayList<String>();
		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_PATINET_CHF_ID_LIST"));
			pstmt.setInt(1, institutionId);
			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					chfIdList.add(rs.getString(TableConstants.CHF_PATIENT_ID));
				}
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
		return chfIdList;
	}

	public static int checkForDuplicatePatient(PatientPhi patientPhi) {
		int patientCount = 0;
		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_DUPLICATE_PATINET_COUNT"));
			pstmt.setString(1, patientPhi.getGender());
			pstmt.setString(2, patientPhi.getFirstName());
			pstmt.setString(3, patientPhi.getLastName());
			pstmt.setString(4, patientPhi.getDoB());
			pstmt.setString(5, patientPhi.getContactNo());
			pstmt.setString(6, patientPhi.getSsn());

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				patientCount = rs.getInt(TableConstants.PATIENT_COUNT);
				LOGGER.info(patientPhi + "patientCount  :::::: " + patientCount);
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}
		return patientCount;
	}

}
