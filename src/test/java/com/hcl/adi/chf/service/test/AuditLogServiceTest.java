package com.hcl.adi.chf.service.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.enums.AdminType;
import com.hcl.adi.chf.model.Admins;
import com.hcl.adi.chf.model.AuditLog;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.service.AuditLogService;
import com.hcl.adi.chf.util.test.SetupInMemoryDBUtil;

public class AuditLogServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(AuditLogServiceTest.class.getName());
	private static AuditLog createAuditLog = null;
	private static String email = null;
	private static int institutionId;
	private static Admins admins = null;
	private static Date startDate =null;
	private static Date endDate =null;

	@BeforeClass
	public static void createInput() throws IOException {
		// It is use to initialize database and create table and insert data
		SetupInMemoryDBUtil.setupInMemoryDB();

		createAuditLog = new AuditLog();
		createAuditLog.setInstitutionId(1);
		email = "admin10@gmail.com";
		institutionId = 1;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-04-22 09:45:22");
			endDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-04-24 07:41:39");
			System.out.println("startDate::"+startDate+"  endDate:::"+endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		admins = new Admins();
		admins.setType(AdminType.SA.toString());
		admins.setAdminId(1);
		admins.setEmailId(email);
	}

	@Test
	public void fetchAdminAndInsertRecordIntoAuditLogs() {
		AuditLogService auditLogService = new AuditLogService();
		CustomResponse customResponse = auditLogService.fetchAdminAndInsertRecordIntoAuditLogs(email,
				Actions.LIST.toString());
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void getAuditLogsForSuperAdminByDateFilter() {
		AuditLogService auditLogService = new AuditLogService();
		List<AuditLog> auditLogList = auditLogService.getAuditLogsForSuperAdminByDateFilter(startDate,endDate,"vv","ADI");
		LOGGER.info(auditLogList);
		Assert.assertNotNull(auditLogList);
	}
	
	@Test
	public void getAuditLogsOfSpecifiedInstitutionByDateFilter() {
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-04-21 09:45:22");
			endDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-04-24 07:41:39");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		AuditLogService auditLogService = new AuditLogService();
		List<AuditLog> auditLogList = auditLogService.getAuditLogsOfSpecifiedInstitutionByDateFilter(institutionId,startDate,endDate,"vv");
		LOGGER.info(auditLogList);
		Assert.assertNotNull(auditLogList);
	}
	
	@Test
	public void sendMessageToAuditLogQueue() {
		AuditLogService auditLogService = new AuditLogService();
		CustomResponse customResponse = auditLogService.sendMessageToAuditLogQueue(institutionId, "SA",
				Actions.LIST.toString(), email);
		LOGGER.info(customResponse);
		Assert.assertNotNull(customResponse);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueActivate() {
		admins.setType(AdminType.SA.toString());
		admins.setAction(Actions.ACTIVATE.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueDeactivate() {
		admins.setType(AdminType.SA.toString());
		admins.setAction(Actions.DEACTIVATE.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueDelete() {
		admins.setType(AdminType.SA.toString());
		admins.setAction(Actions.DELETE.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueEdit() {
		admins.setType(AdminType.SA.toString());
		admins.setAction(Actions.EDIT.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueIAActivate() {
		admins.setType(AdminType.IA.toString());
		admins.setAction(Actions.ACTIVATE.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueIADeactivate() {
		admins.setType(AdminType.IA.toString());
		admins.setAction(Actions.DEACTIVATE.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueIADelete() {
		admins.setType(AdminType.IA.toString());
		admins.setAction(Actions.DELETE.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}

	@Test
	public void sendUpdateAdminMessageToAuditLogQueueIAEdit() {
		admins.setType(AdminType.IA.toString());
		admins.setAction(Actions.EDIT.toString());
		AuditLogService auditLogService = new AuditLogService();
		auditLogService.sendUpdateAdminMessageToAuditLogQueue(admins);
	}
}