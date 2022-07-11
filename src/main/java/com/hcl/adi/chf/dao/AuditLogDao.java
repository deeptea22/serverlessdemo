package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on audit_logs table
 *
 * @author AyushRa
 */
public class AuditLogDao {
	private static final Logger LOGGER = LogManager.getLogger(AuditLogDao.class.getName());

	/**
	 * Fetch audit logs for super admin from audit_logs table based on date
	 * filter
	 *
	 * @param startDate
	 * @param endDate
	 * @param logType
	 * @return
	 */
	public static List<AuditLog> fetchAuditLogsForSuperAdminByDateFilter(final Date startDate, final Date endDate,
			final String logType) {
		List<AuditLog> auditLogList = new ArrayList<AuditLog>();
		String sqlQuery = null;

		try (Connection connection = DBConnection.getConnection();) {
			if (logType != null && StringUtils.isNotBlank(logType) && Constants.SUPER_ADMIN_ONLY_LOGS.equalsIgnoreCase(logType)) {
				sqlQuery = SQLUtils.getSQLQuery("SELECT_FROM_AUDIT_LOGS_FOR_SUPER_ADMIN_ONLY_BY_DATE_FILTER");
			} else {
				sqlQuery = SQLUtils.getSQLQuery("SELECT_FROM_AUDIT_LOGS_BY_DATE_FILTER");
			}

			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
			pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					AuditLog auditLog = new AuditLog();
					auditLog.setLogId(rs.getInt(TableConstants.AUDIT_LOG_ID));
					auditLog.setInstitutionId(rs.getInt(TableConstants.AUDIT_LOG_INSTITUTION_ID));
					auditLog.setUserType(rs.getString(TableConstants.AUDIT_LOG_USER_TYPE));
					auditLog.setActivity(rs.getString(TableConstants.AUDIT_LOG_ACTIVITY));
					auditLog.setCreatedBy(rs.getString(TableConstants.AUDIT_LOG_CREATED_BY));
					auditLog.setCreatedTimestamp(rs.getTimestamp(TableConstants.AUDIT_LOG_CREATED_TIMESTAMP));

					auditLogList.add(auditLog);
				}
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return auditLogList;
	}

	/**
	 * Insert record into audit_logs table
	 *
	 * @param auditLog
	 * @return
	 */
	public static CustomResponse insertRecordIntoAuditLogs(final AuditLog auditLog) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsInserted = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_AUDIT_LOGS"));

				pstmt.setInt(1, auditLog.getInstitutionId());
				pstmt.setString(2, auditLog.getUserType());
				pstmt.setString(3, auditLog.getActivity());
				pstmt.setString(4, auditLog.getCreatedBy());

				numberOfRecordsInserted = pstmt.executeUpdate();

				LOGGER.info("Number of records inserted into audit_logs table: " + numberOfRecordsInserted);

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
	 * Fetch audit logs of specified institution from audit_logs table based on
	 * date filter
	 *
	 * @param institutionId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<AuditLog> fetchAuditLogsOfSpecifiedInstitutionByDateFilter(final int institutionId,
			final Date startDate, final Date endDate) {
		List<AuditLog> auditLogList = new ArrayList<AuditLog>();

		try (Connection connection = DBConnection.getConnection();) {
			String sqlQuery = SQLUtils.getSQLQuery("SELECT_FROM_AUDIT_LOGS_BASED_ON_INSTITUTION_ID_AND_DATE_FILTER");
			PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
			pstmt.setInt(1, institutionId);
			pstmt.setDate(2, new java.sql.Date(startDate.getTime()));
			pstmt.setDate(3, new java.sql.Date(endDate.getTime()));

			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					AuditLog auditLog = new AuditLog();
					auditLog.setLogId(rs.getInt(TableConstants.AUDIT_LOG_ID));
					auditLog.setInstitutionId(rs.getInt(TableConstants.AUDIT_LOG_INSTITUTION_ID));
					auditLog.setUserType(rs.getString(TableConstants.AUDIT_LOG_USER_TYPE));
					auditLog.setActivity(rs.getString(TableConstants.AUDIT_LOG_ACTIVITY));
					auditLog.setCreatedBy(rs.getString(TableConstants.AUDIT_LOG_CREATED_BY));
					auditLog.setCreatedTimestamp(rs.getTimestamp(TableConstants.AUDIT_LOG_CREATED_TIMESTAMP));

					auditLogList.add(auditLog);
				}
			}
		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return auditLogList;
	}

}