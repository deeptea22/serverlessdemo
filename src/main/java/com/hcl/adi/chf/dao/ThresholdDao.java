package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.ThresholdPolicy;
import com.hcl.adi.chf.model.ThresholdPolicy.Threshold;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;
/**
 * This class is to perform DB Operations on threshold_policy table
 * for institution
 *
 * @author DivyaAg
 */
public final class ThresholdDao {

	private static final Logger LOGGER = LogManager.getLogger(ThresholdDao.class.getName());

	private ThresholdDao() {
		// private constructor
	}

	/**
	 * Fetch Threshold policy details based on institutionId
	 *
	 * @param institutionId
	 * @return
	 */
	public static ThresholdPolicy fetchThresholdPolicyDetailBasedOnInstitutionID(final int institutionId) {
		ThresholdPolicy institutionThresholdPolicy = new ThresholdPolicy();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_THRESHOLD_POLICY_BY_INSTITUTION_ID"));

			pstmt.setInt(1, institutionId);

			
			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				institutionThresholdPolicy.setThresholdPolicyId(rs.getInt(TableConstants.THRESHOLD_POLICY_ID));
				institutionThresholdPolicy.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
				String thresholdPolicy = rs.getString(TableConstants.THRESHOLD_POLICY);
				institutionThresholdPolicy.setStatus(rs.getString(TableConstants.THRESHOLD_POLICY_STATUS));
				// now convert the threshold values string to json 
				ObjectMapper objectMapper = new ObjectMapper();
				TypeReference<List<Threshold>> typeReference = new TypeReference<List<Threshold>>() {
				};
		       List<Threshold> thresholdValues = objectMapper.readValue(thresholdPolicy, typeReference);
		       institutionThresholdPolicy.setThreshold(thresholdValues);
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institutionThresholdPolicy;
	}

	
	
	/**
	 * This method will update the threshold policy of a institution 
	 * patient
	 *
	 * @param bean
	 * @return
	 */
	public static CustomResponse updateThresholdPolicyByInstitutionId(final ThresholdPolicy thresholdPolicy) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;
		PreparedStatement pstmt = null;
	

		try (Connection connection = DBConnection.getConnection();) {
			connection.setAutoCommit(false);
			
			if(thresholdPolicy.getAction().equalsIgnoreCase(Constants.TOGGLE)) {
				pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_THRESHOLD_POLICY_STATUS_BY_INSTITUTION_ID"));

				
				pstmt.setString(1, thresholdPolicy.getStatus());
				pstmt.setString(2, thresholdPolicy.getUpdatedBy());
				pstmt.setInt(3, thresholdPolicy.getInstitutionId());
				
				numberOfRecordsUpdated = pstmt.executeUpdate();
			
			} else if(thresholdPolicy.getAction().equalsIgnoreCase(Constants.POLICY)){
				
				pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_THRESHOLD_POLICY_BY_INSTITUTION_ID"));

				String threshold = new ObjectMapper().writeValueAsString(thresholdPolicy.getThreshold());
				pstmt.setString(1, threshold);
				pstmt.setString(2, thresholdPolicy.getUpdatedBy());
				pstmt.setInt(3, thresholdPolicy.getInstitutionId());
				
				numberOfRecordsUpdated = pstmt.executeUpdate();
			} 
			

			
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

	
	
}
