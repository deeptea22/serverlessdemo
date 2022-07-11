package com.hcl.adi.chf.service.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hcl.adi.chf.service.SQSReaderService;

public class SQSReaderServiceTest {
	private static final Logger LOGGER = LogManager.getLogger(SQSReaderServiceTest.class.getName());

	@BeforeClass
	public static void createInput() throws IOException {

	}

	@Test
	public void fetchMaximumPossibleDetailsOfAdminByEmailId() {
		LOGGER.info("fetchMaximumPossibleDetailsOfAdminByEmailId Method Start");
		SQSReaderService readerService = new SQSReaderService();
		readerService.readMessagesFromSQSAndSendEmail();
		LOGGER.info("fetchMaximumPossibleDetailsOfAdminByEmailId Method End");
	}

}
