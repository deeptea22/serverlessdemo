package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DataArchivalPolicy;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on data_archival_policy_mgmt table
 *
 * @author AyushRa
 */
public final class DataArchivalPolicyDao {
	private static final Logger LOGGER = LogManager.getLogger(DataArchivalPolicyDao.class.getName());

	private DataArchivalPolicyDao() {
		// private constructor
	}

	/**
	 * Fetch data archival policy details based on institutionId
	 *
	 * @param institutionId
	 * @return
	 */
	public static DataArchivalPolicy fetchDataArchivalPolicyDetailBasedOnInstitutionID(final int institutionId) {
		DataArchivalPolicy dataArchivalPolicy = new DataArchivalPolicy();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_DATA_ARCHIVAL_POICY_MGMT_BY_INSTITUTION_ID"));

			pstmt.setInt(1, institutionId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				dataArchivalPolicy.setDataArchivalPolicyId(rs.getInt(TableConstants.DATA_ARCHIVAL_POLICY_ID));
				dataArchivalPolicy.setInstitutionId(rs.getInt(TableConstants.DATA_ARCHIVAL_INSTITUTION_ID));
				dataArchivalPolicy.setArchivalPeriodInMonths(rs.getInt(TableConstants.DATA_ARCHIVAL_PERIOD));
				dataArchivalPolicy.setAutoArchivalFrequency(rs.getString(TableConstants.DATA_ARCHIVAL_FREQUENCY));
				dataArchivalPolicy
						.setAutoLogOffTimeInMinutes(rs.getInt(TableConstants.DATA_ARCHIVAL_AUTO_LOG_OFF_TIME));
				dataArchivalPolicy.setDurationToStoreAuditLogsInMonths(
						rs.getInt(TableConstants.DATA_ARCHIVAL_DURATION_TO_STORE_AUDIT_LOGS));
				dataArchivalPolicy
						.setDataArchivalPolicyStatus(rs.getString(TableConstants.DATA_ARCHIVAL_POLICY_STATUS));
				dataArchivalPolicy.setDeleteMarker(rs.getString(TableConstants.DATA_ARCHIVAL_DELETE_MARKER));
				dataArchivalPolicy.setCreatedBy(rs.getString(TableConstants.DATA_ARCHIVAL_CREATED_BY));
				dataArchivalPolicy.setCreatedTimestamp(rs.getTimestamp(TableConstants.DATA_ARCHIVAL_CREATED_TIMESTAMP));
				dataArchivalPolicy.setUpdatedBy(rs.getString(TableConstants.DATA_ARCHIVAL_UPDATED_BY));
				dataArchivalPolicy.setUpdatedTimestamp(rs.getTimestamp(TableConstants.DATA_ARCHIVAL_UPDATED_TIMESTAMP));
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return dataArchivalPolicy;
	}

	/**
	 * Update data archival policy in data_archival_policy_mgmt table
	 *
	 * @param dataArchivalPolicy
	 * @return
	 */
	public static CustomResponse updateDataArchivalPolicy(final DataArchivalPolicy dataArchivalPolicy) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_DATA_ARCHIVAL_POLICY_ON_ID"));

				pstmt.setInt(1, dataArchivalPolicy.getInstitutionId());
				pstmt.setInt(2, dataArchivalPolicy.getArchivalPeriodInMonths());
				pstmt.setString(3, dataArchivalPolicy.getAutoArchivalFrequency());
				pstmt.setInt(4, dataArchivalPolicy.getAutoLogOffTimeInMinutes());
				pstmt.setInt(5, dataArchivalPolicy.getDurationToStoreAuditLogsInMonths());
				pstmt.setString(6, dataArchivalPolicy.getDataArchivalPolicyStatus());
				pstmt.setString(7, dataArchivalPolicy.getDeleteMarker());
				pstmt.setString(8, dataArchivalPolicy.getUpdatedBy());
				pstmt.setInt(9, dataArchivalPolicy.getDataArchivalPolicyId());

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

	
}