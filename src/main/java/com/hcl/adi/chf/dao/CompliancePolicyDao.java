package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on compliance_policy table
 *
 * @author SandeepSingh
 */
public final class CompliancePolicyDao {
	private static final Logger LOGGER = LogManager.getLogger(CompliancePolicyDao.class.getName());

	private CompliancePolicyDao() {
		// private constructor
	}

	/**
	 * Fetch Compliance Policy details based on institutionId
	 *
	 * @param institutionId
	 * @return
	 */
	public static CompliancePolicy fetchCompliancePolicyBasedOnInstitutionID(final int institutionId) {
		CompliancePolicy policy = new CompliancePolicy();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_COMPLIANCE_POLICY_BY_INSTITUTION_ID"));

			pstmt.setInt(1, institutionId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				policy.setPkId(rs.getInt(TableConstants.COMPLIANCE_PK_ID));
				policy.setCompliancePeriod(rs.getInt(TableConstants.COMPLIANCE_PERIOD));
				policy.setStatus(rs.getString(TableConstants.COMPLIANCE_STATUS));
				policy.setInstitutionId(rs.getInt(TableConstants.COMPLIANCE_INSTITUTION_ID));
				policy.setCreatedBy(rs.getString(TableConstants.COMPLIANCE_CREATED_BY));
				policy.setCreatedTimestamp(rs.getTimestamp(TableConstants.COMPLIANCE_CREATED_TIMESTAMP));
				policy.setUpdatedBy(rs.getString(TableConstants.COMPLIANCE_UPDATED_BY));
				policy.setUpdatedTimestamp(rs.getTimestamp(TableConstants.COMPLIANCE_UPDATED_TIMESTAMP));

			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return policy;
	}

	/**
	 * Update record in compliance_policy table for the corresponding
	 * institution
	 *
	 * @param CompliancePolicy
	 * @return
	 */
	public static CustomResponse updateCompliancePolicy(final CompliancePolicy policy) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_COMPLIANCE_POLICY_ON_ID"));
				pstmt.setString(1, policy.getUpdatedBy());
				pstmt.setInt(2, policy.getCompliancePeriod());
				pstmt.setInt(3, policy.getPkId());

				numberOfRecordsUpdated = pstmt.executeUpdate();
				if (numberOfRecordsUpdated > 0) {
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

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	
}