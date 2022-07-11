package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.ChfPatientIdPolicy;
import com.hcl.adi.chf.model.ChfPatientIdPolicy.Policy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on chf_patient_id_policy table
 *
 * @author AyushRa
 */
public final class ChfPatientIdPolicyDao {
	private static final Logger LOGGER = LogManager.getLogger(ChfPatientIdPolicyDao.class.getName());

	private ChfPatientIdPolicyDao() {
		// private constructor
	}

	/**
	 * Insert record into chf_patient_id_policy table
	 *
	 * @param chfPatientIdPolicy
	 * @return
	 */
	public static CustomResponse persistChfPatientIdPolicy(final ChfPatientIdPolicy chfPatientIdPolicy) {
		CustomResponse response = new CustomResponse();
		final int batchSize = 5;
		int count = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_CHF_PATIENT_ID_POLICY"));

				for (ChfPatientIdPolicy.Policy policy : chfPatientIdPolicy.getPolicyList()) {
					pstmt.setInt(1, chfPatientIdPolicy.getInstitutionId());
					pstmt.setString(2, chfPatientIdPolicy.getIsEMREHREnabled());
					pstmt.setString(3, policy.getLabel());
					pstmt.setString(4, policy.getLabelRule());
					pstmt.setString(5, policy.getLabelSeparator());
					pstmt.setShort(6, policy.getLabelSequence());
					pstmt.setString(7, chfPatientIdPolicy.getCreatedBy());

					pstmt.addBatch(); // add query into batch

					if (++count % batchSize == 0) {
						pstmt.executeBatch(); // execute batch
						pstmt.clearBatch(); // clear the batch after execution
						count = 0; // reset the count
					}
				}

				if (count > 0) {
					pstmt.executeBatch(); // insert remaining records
				}

				LOGGER.info("Number of records inserted into chf_patient_id_policy table: "
						+ chfPatientIdPolicy.getPolicyList().size());

				response.setStatusCode(Status.OK.getStatusCode());
				response.setDescription(Status.OK.toString());

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
	 * Fetch chf patient id policies of specified institution from
	 * chf_patient_id_policy table
	 *
	 * @param institutionId
	 * @return
	 */
	public static ChfPatientIdPolicy getChfPatientIdPolicyByInstitutionId(final Integer institutionId) {
		ChfPatientIdPolicy chfPatientIdPolicy = new ChfPatientIdPolicy();
		List<Policy> policyList = new LinkedList<Policy>();

		try (Connection con = DBConnection.getConnection()) {
			PreparedStatement ps = con.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_CHF_PATIENT_ID_POLICY_BY_INSTITUTION_ID"));
			ps.setInt(1, institutionId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				chfPatientIdPolicy.setInstitutionId(rs.getInt(TableConstants.CHF_PATIENT_ID_POLICY_INSTITUTION_ID));
				chfPatientIdPolicy.setIsEMREHREnabled(rs.getString(TableConstants.CHF_PATIENT_ID_POLICY_IS_EMR_EHR_ENABLED));

				Policy policy = new Policy();

				policy.setLabel(rs.getString(TableConstants.CHF_PATIENT_ID_POLICY_LABEL));
				policy.setLabelRule(rs.getString(TableConstants.CHF_PATIENT_ID_POLICY_LABEL_RULE));
				policy.setLabelSeparator(rs.getString(TableConstants.CHF_PATIENT_ID_POLICY_LABEL_SEPARATOR));
				policy.setLabelSequence(rs.getShort(TableConstants.CHF_PATIENT_ID_POLICY_LABEL_SEQUENCE));

				policyList.add(policy);
			}

			chfPatientIdPolicy.setPolicyList(policyList);

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return chfPatientIdPolicy;
	}
}